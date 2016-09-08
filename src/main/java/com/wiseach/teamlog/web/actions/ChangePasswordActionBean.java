package com.wiseach.teamlog.web.actions;

import com.wiseach.teamlog.db.UserAuthDBHelper;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;

/**
 * User: Arlen Tan
 * 12-8-9 下午5:15
 */
@UrlBinding("/change-password/{userId}")
public class ChangePasswordActionBean extends BaseActionBean {

    @DontValidate
    @DefaultHandler
    public Resolution view() {
        return new ForwardResolution(ViewHelper.CHANGE_PASSWORD_PAGE);
    }

    public Resolution change() {
        if (UserAuthDBHelper.changePassword(userId,newPassword)) {
            return ViewHelper.getHomePageResolution();
        } else {
            addLocalizableError("newPassword", "change.password.error");
            return view();
        }
    }

    // todo: replace with username or email
    private String userId="admin";
    @Validate(required = true)
    private String  currentPassword,newPassword,retypePassword;

    @ValidationMethod
    public void validatePassword(ValidationErrors errors) {
        // todo: replace the real password change logic here.
        if (!UserAuthDBHelper.userPasswordMatched(userId,currentPassword)) {
            errors.add("currentPassword",new LocalizableError("change.password.current.error"));
            return; // ignore next items.
        }

        if (!newPassword.equals(retypePassword)) {
            errors.add("retypePassword", new LocalizableError("password.not.equal"));
        }

    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRetypePassword() {
        return retypePassword;
    }

    public void setRetypePassword(String retypePassword) {
        this.retypePassword = retypePassword;
    }
}
