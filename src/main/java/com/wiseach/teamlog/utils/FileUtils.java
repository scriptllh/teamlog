package com.wiseach.teamlog.utils;

import com.wiseach.teamlog.Constants;
import com.wiseach.teamlog.db.UserAuthDBHelper;
import com.wiseach.teamlog.web.security.UserAuthProcessor;
import net.coobird.thumbnailator.Thumbnails;
import net.sourceforge.stripes.action.FileBean;

import java.io.*;
import java.text.MessageFormat;

/**
 * User: Arlen Tan
 * 12-8-17 上午10:30
 */
public class FileUtils {


    public static final String TEMP_AVATAR_FOLDER = "avatarTemp";
    public static final String AVATAR_FOLDER = "avatar";
    public static final String DEFAULT_AVATAR = UserAuthProcessor.ROOT_URI+"/res/imgs/default-avatar.png";
//    public static final String FILE_SERVICE_NUMBER = System.getenv("FILE_SERVICE_NUMBER");
//
//    public static final String FILE_SERVICE_PATH = System.getenv("MOPAAS_FILESYSTEM"+FILE_SERVICE_NUMBER+"_LOCAL_PATH") + File.separator + System.getenv("MOPAAS_FILESYSTEM"+FILE_SERVICE_NUMBER+"_NAME");
    public static final String FILE_SERVICE_PATH = System.getProperty("filePath");

    public static void saveBigAvatar(FileBean avatar, String realPath, String filename) {
        try {
            Thumbnails.of(avatar.getInputStream()).size(540,540).toFile(realPath+File.separator+ TEMP_AVATAR_FOLDER +File.separator+filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(Constants.DOT_STRING));
    }

    public static void saveAvatar(String realPath, Integer x, Integer y, Integer width, String userAvatar) {
        if (userAvatar == null) return;
        try {

            String pathTemplate = realPath+File.separator+"{0}"+File.separator+userAvatar;
            Thumbnails.of(MessageFormat.format(pathTemplate, TEMP_AVATAR_FOLDER)).sourceRegion(x, y, width, width).size(120,120).toFile(MessageFormat.format(pathTemplate, AVATAR_FOLDER));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getUserAvatarURL(Long userId) {
        String userAvatar = UserAuthDBHelper.getUserAvatar(userId);
        return getUserAvatarURL(userAvatar);
    }

    public static String getUserAvatarURL(String avatar) {
        return (avatar !=null?UserAuthProcessor.ROOT_URI +Constants.ROOT_STRING+"avatar/"+FileUtils.AVATAR_FOLDER +Constants.ROOT_STRING+ avatar:DEFAULT_AVATAR);
    }

    public static String getUserBigAvatarURL(Long userId) {
        String userAvatar = UserAuthDBHelper.getUserAvatar(userId);
        return getUserBigAvatarURL(userAvatar);
    }

    public static String getUserBigAvatarURL(String avatar) {
        return (avatar !=null?UserAuthProcessor.ROOT_URI +Constants.ROOT_STRING+"avatar/"+FileUtils.TEMP_AVATAR_FOLDER +Constants.ROOT_STRING+ avatar: DEFAULT_AVATAR);
    }

    public static String getFileServicePath() {
        return FILE_SERVICE_PATH;
    }

    public static String getParamFileName() {
        return getFileServicePath()+File.separator+TeamlogLocalizationUtils.PARAMS_NAME+"_en_US.properties";
    }

    public static boolean isParamFileExists() {
        return new File(getParamFileName()).exists();
    }

    public static void initAvatarPath() {
        File bigAvatarPath = new File(getFileServicePath()+File.separator+ TEMP_AVATAR_FOLDER);
        if (!bigAvatarPath.exists()) {
            bigAvatarPath.mkdirs();
        }
        File thumbnailAvatarPath = new File(getFileServicePath()+File.separator+ AVATAR_FOLDER);
        if (!thumbnailAvatarPath.exists()) {
            thumbnailAvatarPath.mkdirs();
        }
    }

    public static void copyFile(String source,String dist){
        try {
            InputStream is = new FileInputStream(new File(source));
            OutputStream os = new FileOutputStream(new File(dist));
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            is.close();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
