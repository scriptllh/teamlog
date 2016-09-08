package com.wiseach.teamlog.utils;

import com.wiseach.teamlog.Constants;
import net.sourceforge.stripes.util.Log;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 * User: Arlen Tan
 * 12-8-10 下午4:21
 */
public class EmailSender {
    public static String HOST_NAME = TeamlogLocalizationUtils.getSystemParams("email.smtp.host");
    public static Integer SMTP_PORT = Integer.parseInt(TeamlogLocalizationUtils.getSystemParams("email.smtp.port"));
    public static String EMAIL_ACCOUNT = TeamlogLocalizationUtils.getSystemParams("email.account");
    public static String EMAIL_PASSWORD = TeamlogLocalizationUtils.getSystemParams("email.password");
    public static String EMAIL_SSL_ENABLED = TeamlogLocalizationUtils.getSystemParams("email.ssl.enabled");
    public static String EMAIL_TLS_ENABLED = TeamlogLocalizationUtils.getSystemParams("email.tls.enabled");

    public static boolean send(final String to, final String toName, final String subject, final String content){
        if (HOST_NAME==null || HOST_NAME.equals("")) return false;
        new Thread(new Runnable() {
            @Override
            public void run() {
                doEmailSend(to, toName, subject, content);

            }
        }).run();
        return true;
    }

    public static boolean test(String to, String name,String subject,String content) {
        return doEmailSend(to,name,subject,content);
    }

    public static void updateParameters(String userName,String password,String host,String port,String ssl,String tls) {
        EmailSender.EMAIL_ACCOUNT = userName;
        EmailSender.EMAIL_PASSWORD = password;
        EmailSender.HOST_NAME=host;
        EmailSender.SMTP_PORT = Integer.valueOf(port);
        EmailSender.EMAIL_SSL_ENABLED = ssl;
        EmailSender.EMAIL_TLS_ENABLED= tls;

    }

    private static boolean doEmailSend(String to, String toName, String subject, String content) {
        HtmlEmail htmlEmail = new HtmlEmail();
        htmlEmail.setHostName(HOST_NAME);
        htmlEmail.setSmtpPort(SMTP_PORT);
        htmlEmail.setAuthentication(EMAIL_ACCOUNT,EMAIL_PASSWORD);
        if (Boolean.valueOf(EMAIL_SSL_ENABLED)) htmlEmail.setSSL(true);
        if (Boolean.valueOf(EMAIL_TLS_ENABLED)) htmlEmail.setTLS(true);
        htmlEmail.setCharset(Constants.ENCODING_UTF8);

        try {
            htmlEmail.setFrom(EMAIL_ACCOUNT);
            htmlEmail.addTo(to, toName, Constants.ENCODING_UTF8);
            htmlEmail.setSubject(subject);
            htmlEmail.setHtmlMsg(content);
            htmlEmail.send();
            return true;
        } catch (EmailException e) {
            Log.getInstance(EmailSender.class).error(e);
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return false;
        }
    }
}
