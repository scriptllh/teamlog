package com.wiseach.teamlog.web.actions;

import com.wiseach.teamlog.db.UserAuthDBHelper;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;

/**
 * User: Arlen Tan
 * 12-8-9 下午5:52
 */

@UrlBinding("/activate-user/{uuid}")
public class ActivateUserActionBean extends BaseActionBean {

    @DontValidate
    @DefaultHandler
    public Resolution view() {
        if (uuid!=null&&!UserAuthDBHelper.needActivate(uuid)) {
//            getContext().getRequest().setAttribute(ERROR_TTILE_KEY,"");
            setRequestAttribute(ERROR_DESCRIPTION_KEY, "activate.error.description");
            return ViewHelper.getStandardErrorBoxResolution();
        }

        if (uuid == null) {
            getContext().getValidationErrors().add("uuid",new LocalizableError("uuid.valueNotPresent"));
        }
        return new ForwardResolution(ViewHelper.ACTIVATE_USER_PAGE);
    }

    public Resolution activate() {
        UserAuthDBHelper.activateUser(uuid,activateNewPassword);

        setRequestAttribute(SUCCESSFUL_TTILE_KEY,"activate.successful.title");
        setRequestAttribute(SUCCESSFUL_DESCRIPTION_KEY,"activate.successful.description");
        setRequestAttribute(SUCCESSFUL_INFO_KEY,getMessage("activate.successful.info",getMessage("successful.box.go.login")));

        return ViewHelper.getStandardSuccessfulBoxResolution();
    }

    @Validate(required = true)
    private String uuid,activateNewPassword,activateRetypePassword;

    @ValidationMethod
    public void validatePassword(ValidationErrors errors) {
        if (!activateNewPassword.equals(activateRetypePassword)) {
            errors.add("activateRetypePassword", new LocalizableError("password.not.equal"));
        }
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getActivateNewPassword() {
        return activateNewPassword;
    }

    public void setActivateNewPassword(String activateNewPassword) {
        this.activateNewPassword = activateNewPassword;
    }

    public String getActivateRetypePassword() {
        return activateRetypePassword;
    }

    public void setActivateRetypePassword(String activateRetypePassword) {
        this.activateRetypePassword = activateRetypePassword;
    }
}
