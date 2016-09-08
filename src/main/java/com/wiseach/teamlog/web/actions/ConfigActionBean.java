package com.wiseach.teamlog.web.actions;

import com.wiseach.teamlog.Constants;
import com.wiseach.teamlog.utils.*;
import com.wiseach.teamlog.web.WebUtils;
import com.wiseach.teamlog.web.security.UserAuthProcessor;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.*;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * User: Arlen Tan
 * 12-8-9 下午6:22
 */
@UrlBinding("/config")
public class ConfigActionBean extends BaseActionBean {

    @DontValidate
    @DefaultHandler
    public Resolution view() {
        if (UserAuthProcessor.isAdmin(getContext()) || !UserAuthProcessor.firstUserCreated()) {
            loadConfiguration();
            return new ForwardResolution(ViewHelper.CONFIG_PAGE);
        }
        setRequestAttribute(ERROR_DESCRIPTION_KEY,"config.error.description");
        return ViewHelper.getStandardErrorBoxResolution();
    }

    private void loadConfiguration() {
        siteUrl= TeamlogLocalizationUtils.getSystemParams("site.url");
        updateSiteUrl();
        smtpUsername = EmailSender.EMAIL_ACCOUNT;
        smtpPassword = EmailSender.EMAIL_PASSWORD;
        smtpHost = EmailSender.HOST_NAME;
        smtpPort = EmailSender.SMTP_PORT.toString();
        ssl = EmailSender.EMAIL_SSL_ENABLED;
        tls = EmailSender.EMAIL_TLS_ENABLED;
    }

    private void updateSiteUrl() {
        if (siteUrl==null || siteUrl.equals(Constants.EMPTY_STRING)) {
            urls = NetUtils.getAllHostUrl(getContext().getRequest().getLocalPort());
            if (urls.size()>0) siteUrl = urls.get(0);
            for (String url : urls) {
                if (url.contains("//192.") || url.contains("//10.")||url.contains("//172.")) {
                    siteUrl = url;
                    break;
                }
            }
        }
    }

    public Resolution save() {
        ssl = ssl==null?"false":"true";
        tls = tls==null?"false":"true";
        EmailSender.updateParameters(smtpUsername,smtpPassword,smtpHost,smtpPort,ssl,tls);
        if (!EmailSender.test(smtpUsername,getMessage("config.email.test.name"),getMessage("config.email.test.subject"),getMessage("config.email.test.content"))) {
            setRequestAttribute(ERROR_DESCRIPTION_KEY,"config.error.save.description.email");
            return ViewHelper.getStandardErrorBoxResolution();
        }
        Properties properties = new Properties();
        try {
            String paramsFile = FileUtils.getParamFileName();
//            String paramsFile = getContext().getServletContext().getRealPath("/")+"WEB-INF"+ File.separator+"classes"
//                    +File.separator+ TeamlogLocalizationUtils.PARAMS_NAME+".properties";
            FileInputStream fileInputStream = new FileInputStream(paramsFile);
            properties.load(fileInputStream);
            fileInputStream.close();
            properties.setProperty("site.url", siteUrl);
            properties.setProperty("email.account", smtpUsername);
            properties.setProperty("email.password", smtpPassword);
            properties.setProperty("email.smtp.host", smtpHost);
            properties.setProperty("email.smtp.port", smtpPort);
            properties.setProperty("email.ssl.enabled", ssl);
            properties.setProperty("email.tls.enabled",tls);
            FileOutputStream fileOutputStream = new FileOutputStream(paramsFile);
            properties.store(fileOutputStream,"update at "+ DateUtils.formatDateTime(new Date()));
            fileOutputStream.close();
            TeamlogLocalizationUtils.refreshParamBundle();
            //refresh current configuration.
            EmailSender.updateParameters(smtpUsername,smtpPassword,smtpHost,smtpPort,ssl,tls);
            WebUtils.updateSiteUrl(siteUrl);
        } catch (Exception e) {
            setRequestAttribute(ERROR_DESCRIPTION_KEY,"config.error.save.description");
            return ViewHelper.getStandardErrorBoxResolution();
        }
        if (UserAuthProcessor.firstUserCreated()) {
            return view();
        } else {
            return new RedirectResolution(CreateFirstUserActionBean.class);
        }
    }

    @Validate(required = true)
    public String siteUrl, smtpUsername, smtpPassword,smtpHost,smtpPort;
    public String ssl,tls;
    public List<String> urls;

    @ValidationMethod
    public void checkData(ValidationErrors errors) {
        try {
            Integer.valueOf(smtpPort);
        } catch (NumberFormatException e) {
            errors.add("smtpPort",new LocalizableError("smtpPort.notANumber"));
        }
        try {
            new URL(siteUrl);
        } catch (Exception e) {
            errors.add("siteUrl",new LocalizableError("siteUrl.invalid"));
        }
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getSmtpUsername() {
        return smtpUsername;
    }

    public void setSmtpUsername(String smtpUsername) {
        this.smtpUsername = smtpUsername;
    }

    public String getSmtpPassword() {
        return smtpPassword;
    }

    public void setSmtpPassword(String smtpPassword) {
        this.smtpPassword = smtpPassword;
    }

    public String getSmtpHost() {
        return smtpHost;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public String getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(String smtpPort) {
        this.smtpPort = smtpPort;
    }

    public String getSsl() {
        return ssl;
    }

    public void setSsl(String ssl) {
        this.ssl = ssl;
    }

    public String getTls() {
        return tls;
    }

    public void setTls(String tls) {
        this.tls = tls;
    }

    public List<String> getUrls() {
        return urls;
    }

}
