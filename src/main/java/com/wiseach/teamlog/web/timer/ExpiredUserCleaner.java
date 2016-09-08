package com.wiseach.teamlog.web.timer;

import com.wiseach.teamlog.db.UserAuthDBHelper;

/**
 * User: Arlen Tan
 * 12-8-14 下午6:00
 */
public class ExpiredUserCleaner implements Runnable {


    public void run() {
        UserAuthDBHelper.clearExpiredUser();
    }
}
