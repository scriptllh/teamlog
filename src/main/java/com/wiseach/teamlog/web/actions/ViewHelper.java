package com.wiseach.teamlog.web.actions;

import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;

/**
 * Created with IntelliJ IDEA.
 * User: Arlen Tan
 */
public class ViewHelper {
    public static final String VIEW_PATH="/WEB-INF/views/";
    public static final String LOGIN_PAGE=VIEW_PATH+"pages/login.jsp";
    public static final String WORK_LOG_PAGE=VIEW_PATH+"pages/worklog.jsp";
    public static final String RESET_PASSWORD_PAGE = VIEW_PATH+"pages/reset-password.jsp";
    public static final String SEND_RESET_PASSWORD_EMAIL_PAGE = VIEW_PATH+"pages/send-reset-password-email.jsp";
    public static final String CREATE_FIRST_USER_PAGE = VIEW_PATH+"pages/create-first-user.jsp";
    public static final String CHANGE_PASSWORD_PAGE = VIEW_PATH+"pages/change-password.jsp";
    public static final String ACTIVATE_USER_PAGE = VIEW_PATH+"pages/activate-user.jsp";
    public static final String CHANGE_AVATAR_PAGE = VIEW_PATH+"pages/change-avatar.jsp";
    public static final String PROFILE_PAGE = VIEW_PATH+"pages/profile.jsp";
    public static final String CONFIG_PAGE = VIEW_PATH+"pages/config.jsp";
    public static final String INVITE_USER_PAGE = VIEW_PATH+"pages/invite-users.jsp";
    public static final String SETTING_PAGE = VIEW_PATH+"pages/setting.jsp";
    public static final String TAG_SELECTION_PAGE = VIEW_PATH+"pages/tag-selection.jsp";


    public static final String STANDARD_ERROR_BOX_PAGE = VIEW_PATH+"public/results/errorBox.jsp";
    public static final String STANDARD_SUCCESSFUL_BOX_PAGE = VIEW_PATH+"public/results/successfulBox.jsp";

    public static Resolution getHomePageResolution() {
        return new RedirectResolution(WorkLogActionBean.class);
    }
    public static Resolution getLoginResolution() {
        return new RedirectResolution(LoginActionBean.class);
    }

    public static Resolution getStandardErrorBoxResolution() {
        return new ForwardResolution(STANDARD_ERROR_BOX_PAGE);
    }

    public static Resolution getStandardSuccessfulBoxResolution() {
        return new ForwardResolution(STANDARD_SUCCESSFUL_BOX_PAGE);
    }
}
