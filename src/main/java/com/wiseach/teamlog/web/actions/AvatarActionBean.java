package com.wiseach.teamlog.web.actions;

import com.wiseach.teamlog.utils.FileUtils;
import net.sourceforge.stripes.action.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * User: Sean Tan
 * Date: 13-12-10
 * Time: 下午10:40
 */
@UrlBinding("/avatar/{picName}")
public class AvatarActionBean extends BaseActionBean{

    @DontValidate
    @DefaultHandler
    public Resolution view() {
        File file = new File(getRealPath()+File.separator+picName);
        if (file.exists()) {
            try {
                String extension = FileUtils.getExtension(picName);
                return new StreamingResolution("images/"+ extension=="jpg"?"jpeg":extension,new FileInputStream(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return new ErrorResolution(404);
            }
        } else {
            return new ErrorResolution(404);
        }
//        return getErrorJsonResolution(getRealPath()+File.separator+picName);
    }

//    public Resolution fileList() {
//        File file = new File(getRealPath()+File.separator+FileUtils.TEMP_AVATAR_FOLDER);
//        List<String> files = new ArrayList<String>();
//        for (String f : file.list()) {
//            files.add(f);
//        }
//        files.add("-------------------");
//
//        file = new File(getRealPath() + File.separator + FileUtils.AVATAR_FOLDER);
//        for (String f : file.list()) {
//            files.add(f);
//        }
//        fileList = files.toArray(new String[files.size()]);
//
//        return new ForwardResolution("/WEB-INF/views/pages/fileList.jsp");
//    }

    private String picName;
    private String[] fileList;

    public String[] getFileList() {
        return fileList;
    }

    public void setFileList(String[] fileList) {
        this.fileList = fileList;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }
}
