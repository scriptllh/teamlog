package com.wiseach.teamlog.web.actions;

import com.mysql.jdbc.StringUtils;
import com.wiseach.teamlog.Constants;
import com.wiseach.teamlog.db.CommonDBHelper;
import com.wiseach.teamlog.db.WorkLogDBHelper;
import com.wiseach.teamlog.utils.DateUtils;
import com.wiseach.teamlog.utils.TeamlogUtils;
import com.wiseach.teamlog.web.resolutions.JsonResolution;
import com.wiseach.teamlog.web.security.UserAuthProcessor;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.util.StringUtil;
import org.joda.time.DateTime;

import java.util.*;

/**
 * User: Arlen Tan
 * 12-8-24 上午9:19
 */
@UrlBinding("/worklog-data/{$event}")
public class WorkLogDataServiceActionBean extends BaseActionBean {

    public static final String ERROR_MESSAGE_COMMENT_REFERID_VALUE_NOT_PRESENT = "error.message.comment.referid.valueNotPresent";
    public static final String ERROR_MESSAGE_COMMENT_REFERID_AND_COMMENT_VALUE_NOT_PRESENT = "error.message.comment.referid.and.comment.valueNotPresent";
    public static final String ERROR_MESSAGE_ID_VALUE_NOT_PRESENT = "error.message.id.valueNotPresent";

    public Resolution shareToMe() {
        return new JsonResolution<List<Map<String, Object>>>(WorkLogDBHelper.getShareToMe(UserAuthProcessor.getUserId(getContext())));
    }

    public Resolution showWorkLogData() {
        String message = TeamlogUtils.checkWorklogConditions(period,people);

        if (StringUtils.isNullOrEmpty(message)) {
            Map<String, DateTime> datePeriod = TeamlogUtils.getSplitDate(period);
            List<Long> userIdList = TeamlogUtils.getSplitId(people);

            return new JsonResolution<List<Map<String, Object>>>(WorkLogDBHelper.getWorkLogData(datePeriod.get("start").toDate(),datePeriod.get("end").toDate(),userIdList.toArray()));
        } else {
            return new JsonResolution<String>(getMessage(message));
        }
    }

    public Resolution showComments() {
        if (referId!=null && referId>0) {
            return new JsonResolution<List<Map<String, Object>>>(WorkLogDBHelper.getCommentData(referId));
        } else {
            return getErrorJsonResolution(ERROR_MESSAGE_COMMENT_REFERID_VALUE_NOT_PRESENT);
        }
    }

    public Resolution newComment(){

        if (StringUtils.isNullOrEmpty(description) || (referId == null || referId == 0)) {
            return getErrorJsonResolution(ERROR_MESSAGE_COMMENT_REFERID_AND_COMMENT_VALUE_NOT_PRESENT);
        } else {
            Long cid = CommonDBHelper.newComment(description, referId, UserAuthProcessor.getUserId(getContext()));
            WorkLogDBHelper.updateCommentCount(referId);
            return new JsonResolution<Long>(cid);
        }
    }

    public Resolution nice() {
        if (referId == null || referId == 0) {
            return getErrorJsonResolution(ERROR_MESSAGE_COMMENT_REFERID_VALUE_NOT_PRESENT);
        } else {
            if (WorkLogDBHelper.updateNiceCount(referId,UserAuthProcessor.getUserId(getContext()))) {
                return getSuccessfulJsonResolution();
            } else {
                return getErrorJsonResolution("error.message.rice.duplicate");
            }

        }
    }

    public Resolution updateTime() {
        if (id == null || id ==0) {
            return getErrorJsonResolution(ERROR_MESSAGE_ID_VALUE_NOT_PRESENT);
        }
        if (start == null || end == null ) {
            return getErrorJsonResolution("error.message.time.valueNotPresent");
        }
        if (WorkLogDBHelper.updateWorklogTime(id, DateUtils.parseDate(start),DateUtils.parseDate(end))) {
            return new JsonResolution<Integer>(0);
        } else {
            return getErrorJsonResolution("error.message.worklog.update");
        }
    }

    public Resolution updateContent() {
        Long tId = Long.parseLong(tagId);
        if (id==null || id == 0) {
            //new a worklog
            id = WorkLogDBHelper.newWorklog(description, tagStr,tId,DateUtils.parseDate(start),DateUtils.parseDate(end),UserAuthProcessor.getUserId(getContext()));
        } else {
            //update a worklog
            id = WorkLogDBHelper.updateContent(description, tagStr,tId,id);
        }
        if (id > 0) {
            return new JsonResolution<Long>(id);
        } else {
            return new JsonResolution<String>(getMessage("error.message.worklog.update.content"));
        }
    }

    private void updateTagData() {
        String[] tagArray = StringUtil.standardSplit(tagStr);
        String[] tagIdArray = StringUtil.standardSplit(tagId);
        String tag,tagId;
        Long tId;
        List<Long> currentTagIds = new ArrayList<Long>();
        boolean needCreateReferTag;
        for (int i = 0; i < tagArray.length; i++) {
            tag = tagArray[i];
            tagId = tagIdArray[i];
            try {
                tId = Long.parseLong(tagId);
                needCreateReferTag = CommonDBHelper.needCreateReferTag(tId, id, Constants.REFER_TYPE_WORKLOG);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                tId = CommonDBHelper.newTag(tag);
                needCreateReferTag = true;
            }

            if (needCreateReferTag) CommonDBHelper.newReferTag(tId,id,Constants.REFER_TYPE_WORKLOG);
            currentTagIds.add(tId);
        }

        CommonDBHelper.clearTags(id,Constants.REFER_TYPE_WORKLOG, currentTagIds.toArray(new Long[0]));
    }

    public Resolution delete() {
        if (id == null || id ==0) {
            return getErrorJsonResolution(ERROR_MESSAGE_ID_VALUE_NOT_PRESENT);
        }

        if (WorkLogDBHelper.deleteWorklog(id)) {
            return getSuccessfulJsonResolution();
        } else {
            return getErrorJsonResolution("error.message.worklog.delete");
        }
    }

    public Resolution getTags() {
        List<Map<String, Object>> tags = CommonDBHelper.getUserTags(getUserId());
        if (tags!=null && tags.size()>0) {
            return new JsonResolution<List<Map<String, Object>>>(tags);
        } else {
            return getErrorJsonResolution("error.message.worklog.tag.get");
        }

    }

    public Resolution showSharedPeople() {
        return new JsonResolution<List<Map<String, Object>>>(WorkLogDBHelper.getSharedPeople(UserAuthProcessor.getUserId(getContext())));
    }

    public Resolution deleteSharedPerson() {
        if (id==null || id==0) {
            return getErrorJsonResolution(ERROR_MESSAGE_ID_VALUE_NOT_PRESENT);
        } else {
            if (WorkLogDBHelper.deleteSharedPerson(id,UserAuthProcessor.getUserId(getContext()))) {
                return new JsonResolution<Integer>(0);
            } else {
                return getErrorJsonResolution("error.message.share.user.delete");
            }
        }
    }

    public Resolution addSharePerson() {
        if (id==null || id==0) {
            return getErrorJsonResolution(ERROR_MESSAGE_ID_VALUE_NOT_PRESENT);
        } else {
            Long newId = WorkLogDBHelper.newSharedPerson(id,UserAuthProcessor.getUserId(getContext()));
            if (newId>0) {
                return new JsonResolution<Long>(newId);
            } else {
                return getErrorJsonResolution("error.message.share.user.new");
            }
        }
    }

//    @Validate(required = true)
    public String period,people;
    public Long referId,id; // for comments

    public String description;
    public String start,end;
    public String tagStr, tagId;
}
