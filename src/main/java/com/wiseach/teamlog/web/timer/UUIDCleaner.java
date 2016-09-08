package com.wiseach.teamlog.web.timer;

import com.wiseach.teamlog.db.UserAuthDBHelper;

/**
 * User: Arlen Tan
 * 12-8-15 上午9:28
 */
public class UUIDCleaner implements Runnable{

    public void run() {
        UserAuthDBHelper.removeExpiredUUID();
    }
}
