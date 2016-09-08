<%@include file="/WEB-INF/views/public/tagLib.jsp"%>
<fmt:message key="login.title" var="titleStr"/>
<s:layout-render name="/WEB-INF/views/layout/common-layout.jsp" title="${titleStr}">
    <s:layout-component name="headerContent">
        <c:if test="${actionBean['adminEmail'] != null}">
            <div style="padding: 16px 0;">
            <a href="mailto:${actionBean['adminEmail']}"><fmt:message key="login.application"/></a></div>
        </c:if>
    </s:layout-component>
    <s:layout-component name="mainContent">
        <div class="page-header">
            <h2>${titleStr}</h2>
        </div>
        <div class="row" style="position: relative;">
            <div class="span7" style="min-width: 540px;">
                <div class="sub-content">
                    <h3><fmt:message key="login.description"/></h3>
                    <p style="margin-top: 10px;"><fmt:message key="login.info"/></p>
                    <p></p>
                    <ul class="login-ul">
                        <li><img src="${rootUri}/res/imgs/log_edit.png" alt="edit-log"><fmt:message key="login.function.editLog"/></li>
                        <li><img src="${rootUri}/res/imgs/analysis.png" alt="analysis"><fmt:message key="login.function.analysis"/></li>
                        <li><img src="${rootUri}/res/imgs/comment.png" alt="comment"><fmt:message key="login.function.comment"/></li>
                    </ul>
                </div>
            </div>
            <div class="login-box inner-box" style="position: absolute;right: 20px;top:10px;width: 300px;">

                    <s:form beanclass="com.wiseach.teamlog.web.actions.LoginActionBean">
                        <%--<s:errors/>--%>
                        <s:hidden name="gotoUrl"/>
                        <div class="control-group">
                            <div class="controls">
                                <div class="input-prepend">
                                    <fmt:message key="login.userOrEmail" var="userOrEmail"/>
                                    <span class="add-on"><i class="icon-user"></i></span><input id="userName" class="input-width-std" name="username" value="${actionBean.username}" type="text" title="${userOrEmail}" placeholder="${userOrEmail}">
                                </div>
                                <s:errors field="username"/>
                            </div>
                        </div>
                        <div class="control-group">
                            <div class="controls">
                                <div class="input-prepend">
                                    <fmt:message key="login.password" var="userLoginPassword"/>
                                    <span class="add-on"><i class="icon-lock"></i></span><input class="input-width-std" name="password" type="password" title="${userLoginPassword}" placeholder="${userLoginPassword}">
                                </div>
                                <s:errors field="password"/>
                            </div>
                        </div>
                        <div class="control-group">
                            <s:submit name="login"  class="btn btn-primary"><fmt:message key="login.label"/></s:submit>
                            <label class="checkbox login-box-rememberme">
                                <input type="checkbox" name="keepInCookie" value="yes">
                                <fmt:message key="login.rememberMe"/>
                            </label>
                        </div>
                    </s:form>
                    <a href="${rootUri}/send-reset-password-email"><fmt:message key="login.forgot"/></a>
            </div>
        </div>
        <script type="text/javascript">$('#userName').focus();</script>
    </s:layout-component>
</s:layout-render>