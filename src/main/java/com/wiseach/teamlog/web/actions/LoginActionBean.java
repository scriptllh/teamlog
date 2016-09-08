package com.wiseach.teamlog.web.actions;

import com.wiseach.teamlog.Constants;
import com.wiseach.teamlog.db.UserAuthDBHelper;
import com.wiseach.teamlog.web.security.UserAuthProcessor;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.util.StringUtil;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;

/**
 * User: Arlen Tan
 */

@UrlBinding("/login/{gotoUrl}")
public class LoginActionBean extends BaseActionBean {


    @DefaultHandler
    @DontValidate
    public Resolution view() {
        adminEmail = UserAuthProcessor.getAdminEmail();
        return new ForwardResolution(ViewHelper.LOGIN_PAGE);
    }

    public Resolution login() {

        UserAuthProcessor.doLogin(getContext(),username,keepInCookie);

        if (gotoUrl!=null) {
            return new RedirectResolution(Constants.ROOT_STRING + StringUtil.urlDecode(gotoUrl));
        } else {
            return new RedirectResolution(WorkLogActionBean.class);
        }
    }

    @DontValidate
    public Resolution reset() {
        return  new RedirectResolution(SendRestPasswordEmailActionBean.class);
    }


    private String gotoUrl,adminEmail;

    @Validate(required = true)
    private String username,password;
    private Boolean keepInCookie;

    public String getGotoUrl() {
        return gotoUrl;
    }

    public void setGotoUrl(String gotoUrl) {
        this.gotoUrl = gotoUrl;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getKeepInCookie() {
        return keepInCookie;
    }

    public void setKeepInCookie(Boolean keepInCookie) {
        this.keepInCookie = keepInCookie;
    }

    @ValidationMethod
    public void validateUser(ValidationErrors errors) {
        if (UserAuthDBHelper.userDisabled(username)) {
            errors.add("username",new LocalizableError("login.user.disabled"));
            return;
        }
        if (!UserAuthDBHelper.userPasswordMatched(username,password)) {
            errors.add("username",new LocalizableError("login.no.user"));
        }
    }
}
