package com.wiseach.teamlog.web.security;

import com.mysql.jdbc.StringUtils;
import com.wiseach.teamlog.Constants;
import com.wiseach.teamlog.db.UserAuthDBHelper;
import com.wiseach.teamlog.model.User;
import com.wiseach.teamlog.utils.FileUtils;
import net.sourceforge.stripes.action.ActionBeanContext;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

/**
 * User: Arlen Tan
 * 12-8-14 下午2:45
 */
public class UserAuthProcessor {
    public static final int NORMAL_EXPIRY = 60 * 30;
    public static final int TWO_WEEKS_EXPIRY = 60 * 60 * 24 * 14;
    public static final String TEAM_LOG = "TeamLog";
    public static final String USER_ID_KEY = "userId";
    public static final String USER_NAME_KEY = "userName";
    public static final String USER_AVATAR_KEY = "avatar";
    public static String ROOT_URI = "";
    private static boolean firstUserCreated;
    private static String adminEmail;

    public static void doLogin(ActionBeanContext context, String username, Boolean keepInCookie) {
        Integer expired = NORMAL_EXPIRY;
        if (keepInCookie!=null && keepInCookie) {
            expired = TWO_WEEKS_EXPIRY;
        }

        User user = UserAuthDBHelper.getAuthUserByName(username);
        String ip = context.getRequest().getLocalAddr();
        String cookieKey = DigestUtils.md5Hex(user.getPassword() + user.getId() + ip);
        UserAuthDBHelper.saveLoginKey(cookieKey,user.getId(),user.getUsername(), ip,expired);
        Cookie userInfo = new Cookie(TEAM_LOG,cookieKey);
        userInfo.setMaxAge(expired);
        context.getResponse().addCookie(userInfo);

        saveUserToSession(context, user);

    }

    public static void saveUserToSession(ActionBeanContext context, User user) {
        HttpSession session = context.getRequest().getSession();

        session.setAttribute(USER_ID_KEY,user.getId());
        session.setAttribute(USER_NAME_KEY,user.getUsername());
        session.setAttribute(USER_AVATAR_KEY, FileUtils.getUserAvatarURL(user.getId()));
    }

    public static Long getUserId(ActionBeanContext context) {
        return (Long) context.getRequest().getSession().getAttribute(USER_ID_KEY);
    }

    public static boolean userLogined(ActionBeanContext context) {

        Long userId = getUserId(context);
        if (userId !=null && userId >0) {
            // user exist in session.
            return true;
        }

        String cookieKey = getCookieKey(context);
        if (cookieKey == null) {
            // no cookie, need to login;
            return false;
        } else {
            User user = UserAuthDBHelper.getUserByCookieKey(cookieKey);
            if (user != null) {
                // save user info into session
                saveUserToSession(context,user);
                return true;
            } else {
                return false;
            }
        }
    }

    private static String getCookieKey(ActionBeanContext context) {
        Cookie[] cookies = context.getRequest().getCookies();
        Cookie cookie;
        String cookieKey = null;

        if (cookies!=null) {
            for (int i = 0; i < cookies.length; i++) {
                cookie = cookies[i];
                if (cookie.getName().equals(TEAM_LOG)) {
                    cookieKey = cookie.getValue();
                    break;
                }
            }
        }

        return cookieKey;
    }

    public static void doLogout(ActionBeanContext context) {
        UserAuthDBHelper.removeOnlineUser(getCookieKey(context));
        //remove the login cookie.
        Cookie cookie = new Cookie(TEAM_LOG, Constants.EMPTY_STRING);
        cookie.setMaxAge(0);
        context.getResponse().addCookie(cookie);
        HttpSession session = context.getRequest().getSession();
        if (session!=null) session.invalidate();
    }

    public static boolean firstUserCreated() {
        if (firstUserCreated) {
            return true;
        } else {
            updateFirstUserStatus();
            return firstUserCreated;
        }
    }

    public static void updateFirstUserStatus() {
        firstUserCreated = UserAuthDBHelper.firstUserCreated();
    }

    public static String getAdminEmail() {
        if (adminEmail==null) {
            adminEmail = UserAuthDBHelper.getAdminEmail(); //avoid many access db to get the email.
        }
        return adminEmail;
    }


    public static boolean isAdmin(ActionBeanContext context) {
        Object userName = context.getRequest().getSession().getAttribute(USER_NAME_KEY);
        return userName !=null && userName.equals(UserAuthDBHelper.ADMIN_USER);
    }


    public static boolean isLogin(ActionBeanContext context) {

        Long userId = getUserId(context);
        return userId !=null && userId>0;
    }

    public static void updateUserAvatar(ActionBeanContext context) {
        context.getRequest().getSession().setAttribute(USER_AVATAR_KEY, FileUtils.getUserAvatarURL(getUserId(context)));
    }

    public static void updateRootUri(String rootUri) {
        if (!StringUtils.isNullOrEmpty(rootUri)) ROOT_URI = rootUri;
    }
}
