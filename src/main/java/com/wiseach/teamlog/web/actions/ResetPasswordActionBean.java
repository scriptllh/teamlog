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

@UrlBinding("/reset-password/{uuid}")
public class ResetPasswordActionBean extends BaseActionBean {

    @DontValidate
    @DefaultHandler
    public Resolution view() {
        if (uuid !=null && !UserAuthDBHelper.needResetPassword(uuid)) {
//            getContext().getRequest().setAttribute(ERROR_TTILE_KEY,"");
            setRequestAttribute(ERROR_DESCRIPTION_KEY, "reset.password.error.description");
            return ViewHelper.getStandardErrorBoxResolution();
        }

        if (uuid == null) {
            getContext().getValidationErrors().add("uuid",new LocalizableError("uuid.valueNotPresent"));
        }
        return new ForwardResolution(ViewHelper.RESET_PASSWORD_PAGE);
    }

    public Resolution activate() {
        UserAuthDBHelper.resetPassword(uuid, resetNewPassword);

        setRequestAttribute(SUCCESSFUL_TTILE_KEY,"reset.password.successful.title");
        setRequestAttribute(SUCCESSFUL_DESCRIPTION_KEY,"reset.password.successful.description");
        setRequestAttribute(SUCCESSFUL_INFO_KEY,getMessage("reset.password.successful.info",getMessage("successful.box.go.login")));

        return ViewHelper.getStandardSuccessfulBoxResolution();
    }

    @Validate(required = true)
    private String uuid,resetNewPassword, resetRetypePassword;

    @ValidationMethod
    public void validatePassword(ValidationErrors errors) {
        if (!resetNewPassword.equals(resetRetypePassword)) {
            errors.add("resetRetypePassword", new LocalizableError("password.not.equal"));
        }
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getResetNewPassword() {
        return resetNewPassword;
    }

    public void setResetNewPassword(String resetNewPassword) {
        this.resetNewPassword = resetNewPassword;
    }

    public String getResetRetypePassword() {
        return resetRetypePassword;
    }

    public void setResetRetypePassword(String resetRetypePassword) {
        this.resetRetypePassword = resetRetypePassword;
    }
}
