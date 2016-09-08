<%@include file="/WEB-INF/views/public/tagLib.jsp"%>
<fmt:message key="change.password.title" var="titleStr"/>
<s:layout-render name="/WEB-INF/views/layout/login-layout.jsp" title="${titleStr}">
    <s:layout-component name="mainContent">
        <div class="page-header">
            <h2>${titleStr}</h2>
        </div>
        <h3 class="sub-content"><fmt:message key="change.password.description"/></h3>
        <div class="change-password-box">
            <s:form beanclass="com.wiseach.teamlog.web.actions.ChangePasswordActionBean">
                <div class="control-group">
                    <div class="controls">
                        <div class="input-prepend">
                            <fmt:message key="change.password.password.current" var="currentPassword"/>
                            <span class="add-on"><i class="icon-lock"></i></span><input id="currentPassword" class="input-width-std" name="currentPassword" type="password" placeholder="${currentPassword}" title="${currentPassword}">
                        </div>
                    </div>
                    <s:errors field="currentPassword"/>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <div class="input-prepend">
                            <fmt:message key="change.password.password.new" var="newPassword"/>
                            <span class="add-on"><i class="icon-lock"></i></span><input id="newPassword" class="input-width-std" name="newPassword" type="password" placeholder="${newPassword}" title="${newPassword}">
                        </div>
                    </div>
                    <s:errors field="newPassword"/>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <div class="input-prepend">
                            <fmt:message key="change.password.password.retype" var="retypePassword"/>
                            <span class="add-on"><i class="icon-repeat"></i></span><input class="input-width-std" name="retypePassword" type="password" placeholder="${retypePassword}" title="${retypePassword}">
                        </div>
                    </div>
                    <s:errors field="retypePassword"/>
                </div>
                <div class="control-group">
                    <s:submit name="change" class="btn btn-primary"><fmt:message key="button.finish.label"/></s:submit>
                </div>
            </s:form>

            <%@include file="/WEB-INF/views/include/password-indicator.jsp"%>
        </div>
        <script type="text/javascript">$('#currentPassword').focus();$('#newPassword').keyup(function(){checkStrength($(this).val())});</script>
    </s:layout-component>
</s:layout-render>