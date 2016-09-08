package com.wiseach.teamlog.model;

import com.wiseach.teamlog.Constants;
import com.wiseach.teamlog.db.UserAuthDBHelper;
import com.wiseach.teamlog.utils.FileUtils;
import com.wiseach.teamlog.web.security.UserAuthProcessor;

/**
 * User: Arlen Tan
 * 12-8-9 下午6:29
 */
public class User {
    private Long id;
    private String email,username,password,avatar;
    private Boolean disabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return FileUtils.getUserAvatarURL(avatar);
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public boolean getAdmin() {
        return username.equals(UserAuthDBHelper.ADMIN_USER);
    }

}
