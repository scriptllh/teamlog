package com.wiseach.teamlog.web.actions;

import com.wiseach.teamlog.db.UserAuthDBHelper;
import com.wiseach.teamlog.model.User;
import com.wiseach.teamlog.model.UserInfo;
import com.wiseach.teamlog.web.security.UserAuthProcessor;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.validation.*;

/**
 * User: Arlen Tan
 * 12-8-9 下午6:22
 */
@UrlBinding("/profile")
public class ProfileActionBean extends BaseActionBean {

    @DontValidate
    @DefaultHandler
    public Resolution view() {
        if (UserAuthProcessor.isAdmin(getContext())) {
            setRequestAttribute(ERROR_DESCRIPTION_KEY,"profile.base.error.description");
            return ViewHelper.getStandardErrorBoxResolution();
        }
        userInfo = UserAuthDBHelper.getProfile(UserAuthProcessor.getUserId(getContext()));
        return new ForwardResolution(ViewHelper.PROFILE_PAGE);
    }

    public Resolution save() {
        userInfo.setId(UserAuthProcessor.getUserId(getContext()));
        UserAuthDBHelper.updateProfile(userInfo);
        //update username into session.
        User user = new User();
        user.setId(userInfo.getId());
        user.setUsername(userInfo.getUsername());
        UserAuthProcessor.saveUserToSession(getContext(),user);
        return ViewHelper.getHomePageResolution();
    }

    @ValidationMethod
    public void validateUsername(ValidationErrors errors) {
        String username = userInfo.getUsername();
        if (username.equals(UserAuthDBHelper.ADMIN_USER)) {
            errors.add("userInfo.username",new LocalizableError("userInfo.username.admin.conflict"));
            return;
        }

        if (!username.equals(userInfo.getUsername()) && UserAuthDBHelper.usernameUsed(username)) {
            userInfo = UserAuthDBHelper.getProfile(UserAuthProcessor.getUserId(getContext()));
            errors.add("userInfo.username", new LocalizableError("userInfo.username.duplicate", username));
        }
    }

    private UserInfo userInfo;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
