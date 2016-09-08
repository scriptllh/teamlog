package com.wiseach.teamlog.web.actions;

import com.wiseach.teamlog.Constants;
import com.wiseach.teamlog.db.UserAuthDBHelper;
import com.wiseach.teamlog.web.resolutions.JsonResolution;
import com.wiseach.teamlog.web.security.UserAuthProcessor;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

/**
 * User: Arlen Tan
 * 12-8-19 下午3:44
 */
@UrlBinding("/user-management/{$event}/{userId}/{opt}")
public class UserManagementActionBean extends BaseActionBean {

    public Resolution userState() {
        if (UserAuthProcessor.isLogin(getContext()) && UserAuthProcessor.isAdmin(getContext())) {
            if (userId.equals(UserAuthProcessor.getUserId(getContext()))) {
                //ignore the admin click action.
            } else {
                UserAuthDBHelper.updateUserState(userId,opt);
            }
            // update the user state
            return new JsonResolution<String>(Constants.EMPTY_STRING);
        }
        return new JsonResolution<String>(getMessage("error.message.no.privilege.updateUserState"));
    }

    // todo: implement later
//    public Resolution allowInvitation() {
//        if (UserAuthProcessor.isAdmin(getContext())) {
//            // grant the privileges.
//            return new JsonResolution<String>(Constants.EMPTY_STRING);
//        }
//        return new JsonResolution<String>(getMessage(""));
//    }

    @Validate(required = true)
    private Long userId;
    @Validate(required = true)
    private Boolean opt;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getOpt() {
        return opt;
    }

    public void setOpt(Boolean opt) {
        this.opt = opt;
    }
}
