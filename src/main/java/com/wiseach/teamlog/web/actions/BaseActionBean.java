package com.wiseach.teamlog.web.actions;

import com.wiseach.teamlog.Constants;
import com.wiseach.teamlog.utils.FileUtils;
import com.wiseach.teamlog.utils.TeamlogLocalizationUtils;
import com.wiseach.teamlog.web.resolutions.JsonResolution;
import com.wiseach.teamlog.web.security.UserAuthProcessor;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.SimpleError;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Arlen Tan
 */
public class BaseActionBean implements ActionBean {
    private ActionBeanContext context;
    protected static final String ERROR_TTILE_KEY="errorTitle";
    protected static final String ERROR_DESCRIPTION_KEY="errorDescription";
    protected static final String SUCCESSFUL_TTILE_KEY="successfulTitle";
    protected static final String SUCCESSFUL_DESCRIPTION_KEY="successfulDescription";
    protected static final String SUCCESSFUL_INFO_KEY ="successfulInfo";
    protected static final String SUCCESSFUL_BUTTON_KEY="successfulButtonText";


    public void setContext(ActionBeanContext context) {
        this.context = context;
    }


    public ActionBeanContext getContext() {
        return this.context;
    }

    @DontValidate
    public Resolution finish() {
        return ViewHelper.getHomePageResolution();
    }

    @DontValidate
    public Resolution notFound() {
        return new ErrorResolution(404);
    }

    @DontValidate
    public Resolution Error() {
        return new ErrorResolution(500);
    }

    @DontValidate
    public Resolution badRequest() {
        return new ErrorResolution(400);
    }

    protected String getMessage(String key,Object... params) {
        return TeamlogLocalizationUtils.getResourceMessage(key, context.getLocale(), params);
    }

    protected void addLocalizableError(String fieldName, String errorKey) {
        context.getValidationErrors().add(fieldName,new LocalizableError(errorKey));
    }

    protected void addError(String fieldName, String error) {
        context.getValidationErrors().add(fieldName,new SimpleError(error));
    }

    protected void setRequestAttribute(String key,Object value) {
        context.getRequest().setAttribute(key,value);
    }

    protected String getRealPath(String uri) {
        return getContext().getServletContext().getRealPath(uri);
    }

    protected String getRealPath() {
        return FileUtils.getFileServicePath();
//        return getRealPath(Constants.ROOT_STRING);
    }

    protected Long getUserId() {
        return UserAuthProcessor.getUserId(getContext());
    }

    protected boolean isPost() {
        return getContext().getRequest().getMethod().equals("POST");
    }

    public String getLastUrl() {
        HttpServletRequest req = getContext().getRequest();
        StringBuilder sb = new StringBuilder();

        // Start with the URI and the path
        String uri = (String)
                req.getAttribute("javax.servlet.forward.request_uri");
        String path = (String)
                req.getAttribute("javax.servlet.forward.path_info");
        if (uri == null) {
            uri = req.getRequestURI();
            path = req.getPathInfo();
        }
        sb.append(uri);
        if (path != null) { sb.append(path); }

        // Now the request parameters
        sb.append('?');
        Map<String,String[]> map =
                new HashMap<String,String[]>(req.getParameterMap());

        // Remove previous locale parameter, if present.
//        map.remove(MyLocalePicker.LOCALE);

        // Append the parameters to the URL
        for (String key : map.keySet()) {
            String[] values = map.get(key);
            for (String value : values) {
                sb.append(key).append('=').append(value).append('&');
            }
        }
        // Remove the last '&'
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(0);

        return Constants.ROOT_STRING+sb.toString();
    }

    protected Resolution getErrorJsonResolution(String msgKey) {
        return new JsonResolution<String>(getMessage(msgKey));
    }
    protected Resolution getSuccessfulJsonResolution() {
        return new JsonResolution<Integer>(0);
    }

    protected Resolution getEmailSentFailureResolution() {
        setRequestAttribute(ERROR_TTILE_KEY,"error.message.email.description");
        setRequestAttribute(ERROR_DESCRIPTION_KEY,"error.message.email.description");
        return ViewHelper.getStandardErrorBoxResolution();
    }

    protected Resolution returnNoAdminResolution(String errorInfoKey) {
        setRequestAttribute(ERROR_DESCRIPTION_KEY,errorInfoKey);
        return ViewHelper.getStandardErrorBoxResolution();
    }
}
