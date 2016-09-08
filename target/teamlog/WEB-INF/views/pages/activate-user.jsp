<%@include file="/WEB-INF/views/public/tagLib.jsp"%>

<fmt:message key="activate.title" var="titleStr"/>
<s:layout-render name="/WEB-INF/views/layout/common-layout.jsp" title="${titleStr}">
    <s:layout-component name="mainContent">
        <div class="page-header">
            <h2>${titleStr}</h2>
        </div>
        <h3 class="sub-content"><fmt:message key="activate.description"/></h3>
        <div class="activate-box">
            <s:form beanclass="com.wiseach.teamlog.web.actions.ActivateUserActionBean">
                <s:hidden name="uuid" value="${actionBean.uuid}"/>
                <s:errors field="uuid"/>
                <div class="control-group">
                    <div class="controls">
                        <div class="input-prepend">
                            <fmt:message key="activate.password" var="yourPassword"/>
                            <span class="add-on"><i class="icon-lock"></i></span><input id="activatePw" class="input-width-std" name="activateNewPassword" value="${actionBean.activateNewPassword}" type="password" placeholder="${yourPassword}" title="${yourPassword}">
                        </div>
                    </div>
                    <s:errors field="activateNewPassword"/>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <div class="input-prepend">
                            <fmt:message key="activate.password.retype" var="retypePassword"/>
                            <span class="add-on"><i class="icon-repeat"></i></span><input class="input-width-std" name="activateRetypePassword" value="${actionBean.activateRetypePassword}" type="password" placeholder="${retypePassword}" title="${retypePassword}">
                        </div>
                    </div>
                    <s:errors field="activateRetypePassword"/>
                </div>
                <div class="control-group">
                    <s:submit name="activate" class="btn btn-primary"><fmt:message key="activate.button"/></s:submit>
                </div>
            </s:form>

            <%@include file="/WEB-INF/views/include/password-indicator.jsp"%>
        </div>
        <script type="text/javascript">$('#activatePw').focus();$('#activatePw').keyup(function(){checkStrength($(this).val())});</script>
    </s:layout-component>
</s:layout-render>