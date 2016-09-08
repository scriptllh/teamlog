package com.wiseach.teamlog.web.actions;

import com.wiseach.teamlog.db.UserAuthDBHelper;
import com.wiseach.teamlog.model.User;
import com.wiseach.teamlog.utils.EmailSender;
import com.wiseach.teamlog.web.WebUtils;
import com.wiseach.teamlog.web.security.UserAuthProcessor;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.util.StringUtil;
import net.sourceforge.stripes.validation.Validate;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Arlen Tan
 * 12-8-10 上午8:56
 */
@UrlBinding("/invite-users")
public class InviteUserActionBean extends BaseActionBean {

    private static final String INVITE_USER_ERROR_DESCRIPTION = "invite.user.error.description";

    @DontValidate
    @DefaultHandler
    public Resolution view() {
        if (UserAuthProcessor.isAdmin(getContext())) {
            return new ForwardResolution(ViewHelper.INVITE_USER_PAGE);
        }

        return returnNoAdminResolution(INVITE_USER_ERROR_DESCRIPTION);
    }


    public Resolution invite(){
        if (UserAuthProcessor.isAdmin(getContext())) {
            String[] emails = StringUtil.standardSplit(inviteEmail);
            ArrayList<String> registeredEmails = new ArrayList<String>();

            String email,uuid,activateUrl;
            for (int i = 0; i < emails.length; i++) {
                email = emails[i];
                if (UserAuthDBHelper.emailHasRegistered(email)) {
                    registeredEmails.add(email);
                    continue;
                }
                uuid = UserAuthDBHelper.createUser(null,email);
                activateUrl = WebUtils.ACTIVATE_URL+uuid;
                EmailSender.send(email,email,getMessage("email.activate.invite.user.subject"),getMessage("email.activate.invite.user",email,activateUrl,activateUrl));
            }

            if (registeredEmails.size()>0) {
                addError("inviteEmail", getMessage("error.message.email.duplicate", StringUtil.combineParts(registeredEmails)));
            }

            return view();
        }

        return returnNoAdminResolution(INVITE_USER_ERROR_DESCRIPTION);
    }

    @Validate(required = true)
    private String inviteEmail;

    public String getInviteEmail() {
        return inviteEmail;
    }

    public void setInviteEmail(String inviteEmail) {
        this.inviteEmail = inviteEmail;
    }

    public List<User> getUserList() {
        if (UserAuthProcessor.isAdmin(getContext())) {
            return UserAuthDBHelper.getUserList();
        } else {
            return null;
        }
    }
}
