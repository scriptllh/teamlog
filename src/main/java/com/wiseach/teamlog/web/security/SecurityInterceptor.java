package com.wiseach.teamlog.web.security;

import com.mysql.jdbc.StringUtils;
import com.wiseach.teamlog.Constants;
import com.wiseach.teamlog.web.actions.*;
import com.wiseach.teamlog.web.resolutions.JsonResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.ExecutionContext;
import net.sourceforge.stripes.controller.Interceptor;
import net.sourceforge.stripes.controller.Intercepts;
import net.sourceforge.stripes.controller.LifecycleStage;

import java.util.Arrays;
import java.util.List;

/**
 * User: Arlen Tan
 * 12-8-14 下午3:56
 */
@Intercepts(LifecycleStage.ActionBeanResolution)
public class SecurityInterceptor implements Interceptor{
    private static final List<Class<? extends BaseActionBean>> ALLOW = Arrays.asList(WorkLogActionBean.class, ProfileActionBean.class,
            ChangePasswordActionBean.class,ChangeAvatarActionBean.class,TagSelectionActionBean.class,
            InviteUserActionBean.class,WorkLogDataServiceActionBean.class,ExportExcelActionBean.class);
    private static final String GOTO_URL = "gotoUrl";
    private static final String NO_LOGIN = "noLogin";


    public Resolution intercept(ExecutionContext context) throws Exception {

        Resolution resolution = context.proceed();
        BaseActionBean actionBean = (BaseActionBean)context.getActionBean();

        if (UserAuthProcessor.firstUserCreated()) {
            if (ALLOW.contains(actionBean.getClass())) {
                if (UserAuthProcessor.userLogined(context.getActionBeanContext())) {
                    return resolution;
                } else if (actionBean.getClass().equals(WorkLogDataServiceActionBean.class)) {
                    return new JsonResolution<String>(NO_LOGIN);
                } else {
                    RedirectResolution redirectResolution = new RedirectResolution(LoginActionBean.class);
                    String lastUrl = actionBean.getLastUrl().replace(UserAuthProcessor.ROOT_URI, Constants.EMPTY_STRING);
//                    return StringUtils.isNullOrEmpty(lastUrl)?redirectResolution:redirectResolution.addParameter(GOTO_URL, lastUrl);
                    return StringUtils.isNullOrEmpty(lastUrl)?redirectResolution:redirectResolution.addParameter(GOTO_URL, lastUrl);
                }
            }
        } else {
            if (!actionBean.getClass().equals(ConfigActionBean.class) && !actionBean.getClass().equals(CreateFirstUserActionBean.class)) return new RedirectResolution(ConfigActionBean.class);
        }

        return resolution;
    }
}
