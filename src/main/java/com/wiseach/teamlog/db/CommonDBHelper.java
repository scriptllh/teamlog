package com.wiseach.teamlog.db;

import org.apache.commons.dbutils.handlers.MapListHandler;

import java.util.List;
import java.util.Map;

/**
 * User: Arlen Tan
 * 12-8-28 上午11:07
 */
public class CommonDBHelper {
    public static Long newComment(String description,Long referId, Long userId) {
        return PublicDBHelper.insert("insert into comment(description,referId,userId,createTime) values(?,?,?,current_timestamp())",description,referId,userId);
    }


    public static List<Map<String, Object>> getTags() {
        return PublicDBHelper.query("select name as label,id as value,color from tag order by name", new MapListHandler());
    }

    public static List<Map<String, Object>> getUserTags(Long userId) {
        List<Map<String, Object>> tags = PublicDBHelper.query("select name as label,id as value,color from tag where id in (select tagId from userTag where userId=?) order by name", new MapListHandler(), userId);
        if (tags==null || tags.size()==0) {
            tags = getTags();
        }
        return tags;
    }

    public static List<Map<String, Object>> getUserTagsId(Long userId) {
        return PublicDBHelper.query("select id,tagId from userTag where userId=?",new MapListHandler(),userId);
    }

    public static Long addUserTag(Long userId,Long tagId) {
        return PublicDBHelper.insert("insert into userTag(`userId`,`tagId`) values(?,?)", userId, tagId);
    }

    public static boolean removeUserTag(Long userTagId) {
        return PublicDBHelper.exec("delete from userTag where id=?",userTagId)>0;
    }

    public static void newReferTag(long tagId, Long id, Integer referType) {
        PublicDBHelper.insert("insert into referTags(tagId,referId,referType) values(?,?,?)",tagId,id,referType);
    }

    public static boolean needCreateReferTag(Long tId, Long id, Integer referType) {
        return !PublicDBHelper.exist("select count(*) from referTags where tagId=? and referId=? and referType=?",tId,id,referType);
    }

    public static Long newTag(String tag) {
        return PublicDBHelper.insert("insert into tag(name) values(?)",tag);
    }

    public static boolean updateTagColor(Long tagId, String color) {
        return PublicDBHelper.exec("update tag set color=? where tagId=?",color,tagId)>0;
    }

    public static void clearTags(Long id, Integer referType, Long[] currentTagIds) {
        PublicDBHelper.executeWithInParam("delete from referTags where referId=? and referType=? and tagId not in (%in0)",id,referType,currentTagIds);
    }

    public static boolean hasTag(String type) {
        return PublicDBHelper.exist("select count(*) from tag where name = ?",type);
    }

    public static boolean delTag(Long typeId) {
        return PublicDBHelper.exec("delete from tag where id=?",typeId)>0;
    }
}
