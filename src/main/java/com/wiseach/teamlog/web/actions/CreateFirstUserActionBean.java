package com.wiseach.teamlog.web.actions;

import com.wiseach.teamlog.db.UserAuthDBHelper;
import com.wiseach.teamlog.utils.EmailSender;
import com.wiseach.teamlog.web.WebUtils;
import com.wiseach.teamlog.web.security.UserAuthProcessor;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.validation.Validate;

/**
 * User: Arlen Tan
 * 12-8-9 下午4:35
 */
@UrlBinding("/create-first-user")
public class CreateFirstUserActionBean extends BaseActionBean {

    @DontValidate
    @DefaultHandler
    public Resolution view() {
        if (UserAuthProcessor.firstUserCreated()) {
            setRequestAttribute(ERROR_DESCRIPTION_KEY,"create.first.user.error.description");
            return ViewHelper.getStandardErrorBoxResolution();
        }
        return new ForwardResolution(ViewHelper.CREATE_FIRST_USER_PAGE);
    }

    public Resolution create() {
//
//        try {
//            EmailSender.send(firstUserEmail,firstUserEmail,getMessage("email.activate.first.user.subject"),content);
//        } catch (EmailException e) {
//            e.printStackTrace();
//            getContext().getValidationErrors().add("firstUserEmail",new LocalizableError("email.activate.error"));
//            return view();
//        }
        //todo: add email send call.
        String uuidStr = UserAuthDBHelper.createFirstUser(firstUserEmail);
        String content = getMessage("email.activate.first.user", firstUserEmail, WebUtils.ACTIVATE_URL + uuidStr, WebUtils.ACTIVATE_URL + uuidStr);
        boolean sentOK = EmailSender.send(firstUserEmail, firstUserEmail, getMessage("email.activate.first.user.subject"), content);

        if (sentOK) {
            setRequestAttribute(SUCCESSFUL_TTILE_KEY,"create.first.user.ok.title");
            setRequestAttribute(SUCCESSFUL_DESCRIPTION_KEY,"create.first.user.ok.description");
            setRequestAttribute(SUCCESSFUL_INFO_KEY,getMessage("create.first.user.ok.info",firstUserEmail));

            return ViewHelper.getStandardSuccessfulBoxResolution();
        } else {
            //email send failure...
            return getEmailSentFailureResolution();
        }
    }

    @Validate(required = true)
    String firstUserEmail;

    public String getFirstUserEmail() {
        return firstUserEmail;
    }

    public void setFirstUserEmail(String firstUserEmail) {
        this.firstUserEmail = firstUserEmail;
    }
}
