package com.wiseach.teamlog.web.actions;

import net.sourceforge.stripes.action.*;

/**
 * User: Arlen Tan
 * 12-8-9 下午1:45
 */
@UrlBinding("/worklog/{userId}")
public class WorkLogActionBean extends BaseActionBean {

    @DefaultHandler
    public Resolution view() {
        return new ForwardResolution(ViewHelper.WORK_LOG_PAGE);
    }
}
