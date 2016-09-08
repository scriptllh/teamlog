package com.wiseach.teamlog.web.actions;

import com.wiseach.teamlog.db.CommonDBHelper;
import com.wiseach.teamlog.db.UserAuthDBHelper;
import com.wiseach.teamlog.model.User;
import com.wiseach.teamlog.model.UserInfo;
import com.wiseach.teamlog.web.resolutions.JsonResolution;
import com.wiseach.teamlog.web.security.UserAuthProcessor;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: Arlen Tan
 * 12-8-9 下午6:22
 */
@UrlBinding("/work-type-selection")
public class TagSelectionActionBean extends BaseActionBean {

    @DontValidate
    @DefaultHandler
    public Resolution view() {

        List<Map<String,Object>> allTags = CommonDBHelper.getTags();
        List<Map<String, Object>> userTagsId = CommonDBHelper.getUserTagsId(getUserId());
        if (userTagsId!=null&&userTagsId.size()>0) {
            boolean found;
            for (Map<String, Object> tag : allTags) {
                found = false;
                for (Map<String, Object> tagId : userTagsId) {
                    if (tag.get("id").equals(tagId.get("tagId"))) {
                        tag.put("userTagId",tagId.get("id"));
                        found=true;
                        break;
                    }
                }
                if (found) {
                    userTags.add(tag);
                } else {
                    tags.add(tag);
                }
            }
        } else {
            tags = allTags;
        }

        return new ForwardResolution(ViewHelper.TAG_SELECTION_PAGE);
    }

    public Resolution select() {
        if (isPost()) {
            String id = getContext().getRequest().getParameter("id");
            if (id==null) {
                return badRequest();
            } else {
                return new JsonResolution<Long>(CommonDBHelper.addUserTag(getUserId(), Long.valueOf(id)));
            }
        } else {
            return notFound();
        }
    }

    public Resolution remove() {
        if (isPost()) {
            String id = getContext().getRequest().getParameter("id");
            if (id==null) {
                return badRequest();
            } else {
                return new JsonResolution<Boolean>(CommonDBHelper.removeUserTag(Long.valueOf(id)));
            }
        } else {
            return notFound();
        }
    }

    private List<Map<String,Object>> tags=new ArrayList<Map<String, Object>>(),userTags=new ArrayList<Map<String, Object>>();

    public List<Map<String, Object>> getTags() {
        return tags;
    }

    public List<Map<String, Object>> getUserTags() {
        return userTags;
    }
}
