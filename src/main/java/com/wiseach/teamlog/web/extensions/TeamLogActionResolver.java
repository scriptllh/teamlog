package com.wiseach.teamlog.web.extensions;

import com.wiseach.teamlog.Constants;
import net.sourceforge.stripes.controller.NameBasedActionResolver;

/**
 * User: Arlen Tan
 * 12-8-9 上午9:39
 */
public class TeamLogActionResolver extends NameBasedActionResolver {
    @Override
    protected String getBindingSuffix() {
        return Constants.EMPTY_STRING;
    }
    @Override
    protected String getUrlBinding(String actionBeanName) {
        String result = super.getUrlBinding(actionBeanName);
        return convertToLowerCaseWithUnderscores(result);
    }
    private String convertToLowerCaseWithUnderscores(String string) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0, t = string.length(); i < t; i++) {
            char ch = string.charAt(i);
            if (Character.isUpperCase(ch)) {
                ch = Character.toLowerCase(ch);
                if (i > 1) {
                    builder.append(Constants.MINUS_STRING);
                }
            }
            builder.append(ch);
        }
        return builder.toString();
    }
}
