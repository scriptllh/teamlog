package com.wiseach.teamlog.web.actions;

import com.wiseach.teamlog.web.security.UserAuthProcessor;
import net.sourceforge.stripes.action.*;

/**
 * User: Arlen Tan
 * 12-8-14 下午6:28
 */
@UrlBinding("/logout")
public class LogoutActionBean extends BaseActionBean{

    @DefaultHandler
    @DontValidate
    public Resolution view() {
        UserAuthProcessor.doLogout(getContext());

        return new RedirectResolution(LoginActionBean.class);
    }
}
