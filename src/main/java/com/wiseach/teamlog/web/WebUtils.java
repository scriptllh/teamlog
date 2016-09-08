package com.wiseach.teamlog.web;

import com.mysql.jdbc.StringUtils;
import com.wiseach.teamlog.Constants;
import com.wiseach.teamlog.utils.TeamlogLocalizationUtils;
import com.wiseach.teamlog.web.security.UserAuthProcessor;

/**
 * User: Arlen Tan
 * 12-8-12 下午2:21
 */
public class WebUtils {

    public static String SITE_URL= TeamlogLocalizationUtils.getSystemParams("site.url")
            + (UserAuthProcessor.ROOT_URI.equals(Constants.EMPTY_STRING)?Constants.EMPTY_STRING:UserAuthProcessor.ROOT_URI.substring(1)+ Constants.ROOT_STRING);
    public static String ACTIVATE_URL=SITE_URL+"activate-user/";

    public static void updateSiteUrl(String url) {
        if (!StringUtils.isNullOrEmpty(url)) {
            SITE_URL=url+ (UserAuthProcessor.ROOT_URI.equals(Constants.EMPTY_STRING)?Constants.EMPTY_STRING:UserAuthProcessor.ROOT_URI.substring(1)+ Constants.ROOT_STRING);
            ACTIVATE_URL=SITE_URL+"activate-user/";
        }
    }
}
