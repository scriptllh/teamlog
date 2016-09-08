package com.wiseach.teamlog.db;

import org.apache.commons.dbutils.handlers.MapListHandler;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * User: Arlen Tan
 * 12-8-24 上午9:21
 */
public class WorkLogDBHelper {
    public static List<Map<String, Object>> getShareToMe(Long userId) {
        return PublicDBHelper.query("select u.id,u.username,(select ui.avatar from userInfo ui where ui.id = u.id) as avatar " +
                "from user u where u.id in (select userId from userRelation where shareToUserId=?) or u.id=?",
                new MapListHandler(),userId,userId);
    }

    public static List<Map<String, Object>> getWorkLogData(Date start, Date end, Object[] people) {
        return PublicDBHelper.queryWithInParam("select w.id,w.userId,(select avatar from userInfo where id=w.userId) as avatar,u.username,w.description,w.createTime,w.startTime,w.endTime,w.nice,w.comments,w.tags,w.tagId from worklog w inner join user u on (w.userId = u.id) where w.startTime>=? and w.endTime<=? and w.userId in (%in0) order by w.startTime"
                , new MapListHandler(), start, end, people);
    }

    public static List<Map<String, Object>> getWorkLogExportData(Date start, Date end, Object[] people) {
        return PublicDBHelper.queryWithInParam("select DATE(startTime) as workDate,TIME(startTime) as stTime,TIME(endTime) as edTime,round(TIMESTAMPDIFF(MINUTE,startTime,endTime)/60,2) as hours,description,(select username from `user` where id=userId) as staff,(select `name` from tag where id=tagId) as tag,nice,comments from worklog where startTime>=? and endTime<=? and userId in (%in0) order by startTime"
                , new MapListHandler(), start, end, people);
    }

    public static List<Map<String, Object>> getCommentData(Long referId) {
        return PublicDBHelper.query("select id,userid,description,createTime,(select username from user where id = userid) as userName,(select avatar from userInfo where id = userId) as avatar from comment where referId = ?",new MapListHandler(),referId);
    }

    public static void updateCommentCount(Long referId) {
        PublicDBHelper.exec("update worklog set comments=comments+1 where id=?",referId);
    }

    public static boolean updateNiceCount(Long referId,Long userId) {
        if (PublicDBHelper.exist("select count(*) from rank where referId=? and userId=?",referId,userId)) return false;
        PublicDBHelper.insert("insert into rank(referId,userId,createTime) values(?,?,current_timestamp())",referId,userId);
        PublicDBHelper.exec("update worklog set nice=(select count(referId) from rank where referId=?) where id=?",referId,referId);
        return true;
    }

    public static boolean updateWorklogTime(Long id, Date start, Date end) {
        return PublicDBHelper.exec("update worklog set startTime=?,endTime=? where id = ?",start,end,id)>0;
    }

    public static boolean deleteWorklog(Long id) {
        // todo: implement a timer to delete the children data.
        return PublicDBHelper.exec("delete from worklog where id=?", id)>0;
    }

    public static Long newWorklog(String desc,String tags,Long tagId,Date start,Date end, Long userId) {
        return PublicDBHelper.insert("insert into worklog(description,startTime,endTime,tags,tagId,userId,nice,comments,createTime) " +
                "values(?,?,?,?,?,?,0,0,current_timestamp())",
                desc,start,end,tags,tagId,userId);
    }

    public static Long updateContent(String desc,String tags,Long tagId,Long id) {
        if (PublicDBHelper.exec("update worklog set description=?,tags=?,tagId=? where id=?",desc,tags,tagId,id)>0) {
            return id;
        } else {
            return 0l;
        }
    }

    public static List<Long> getSharedWithMe(Long userId) {
        return PublicDBHelper.query("select userId from userRelation where shareToUserId=? union select ?",new LongArrayResultSetHandler(),userId,userId);
    }

    public static List<Map<String, Object>> getSharedPeople(Long userId) {
        return PublicDBHelper.query("select u.id,u.username,(select ui.avatar from userInfo ui where ui.id = u.id) as avatar, 1 as shared " +
                "from user u where u.id in (select shareToUserId from userRelation where userId=?) " +
                "union all " +
                "select u.id,u.username,(select ui.avatar from userInfo ui where ui.id = u.id) as avatar, 0 " +
                "from user u where u.id not in (select shareToUserId from userRelation where userId=?) and u.activateUUID is null",new MapListHandler(),userId,userId);
    }

    public static Boolean deleteSharedPerson(Long id,Long userId) {
        return PublicDBHelper.exec("delete from userRelation where shareToUserId=? and userId=?",id,userId)>0;
    }

    public static Long newSharedPerson(Long id, Long userId) {
        return PublicDBHelper.insert("insert into userRelation(shareToUserId,userId) values(?,?)",id,userId);
    }
}

