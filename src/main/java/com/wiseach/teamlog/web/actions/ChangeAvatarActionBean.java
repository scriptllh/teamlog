package com.wiseach.teamlog.web.actions;

import com.wiseach.teamlog.Constants;
import com.wiseach.teamlog.db.UserAuthDBHelper;
import com.wiseach.teamlog.utils.FileUtils;
import com.wiseach.teamlog.web.resolutions.JsonResolution;
import com.wiseach.teamlog.web.security.UserAuthProcessor;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.util.StringUtil;
import net.sourceforge.stripes.validation.Validate;

/**
 * User: Arlen Tan
 * 12-8-9 下午6:19
 */
@UrlBinding("/change-avatar")
public class ChangeAvatarActionBean extends BaseActionBean {

    @DontValidate
    @DefaultHandler
    public Resolution view() {
        return new ForwardResolution(ViewHelper.CHANGE_AVATAR_PAGE);
    }

    @DontValidate
    public Resolution uploadAvatar() {
        String filename;
        if (avatar!=null) {
            Long userId = UserAuthProcessor.getUserId(getContext());
                    filename = userId +".jpg";
                    UserAuthDBHelper.updateUserAvatar(userId, filename);
                    FileUtils.saveBigAvatar(avatar,getRealPath(),filename);
        } else {
            filename=Constants.EMPTY_STRING;
        }

        return new JsonResolution<String>(filename);
    }

    public Resolution resizeAvatar() {
        String[] dim = StringUtil.standardSplit(dimension);

        try {
            Integer x=Double.valueOf(dim[0]).intValue(),y=Double.valueOf(dim[1]).intValue(),width = Double.valueOf(dim[2]).intValue();
            FileUtils.saveAvatar(getRealPath(),x,y,width,UserAuthDBHelper.getUserAvatar(UserAuthProcessor.getUserId(getContext())));
        } catch (Exception e) {
            addLocalizableError("dimension", "dimension.params.error");
            return view();
        }

        UserAuthProcessor.updateUserAvatar(getContext());

        return ViewHelper.getHomePageResolution();
    }

    @Validate(required = true)
    private String dimension;
    private String userAvatar;
    private FileBean avatar;

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getBigAvatar() {
        if (userAvatar == null) {
            userAvatar = FileUtils.getUserBigAvatarURL(UserAuthProcessor.getUserId(getContext()));
        }
        return userAvatar;
    }

    public FileBean getAvatar() {
        return avatar;
    }

    public void setAvatar(FileBean avatar) {
        this.avatar = avatar;
    }
}
