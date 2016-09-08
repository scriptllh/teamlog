<%@include file="/WEB-INF/views/public/tagLib.jsp"%>
<fmt:message key="reset.password.title" var="titleStr"/>
<s:layout-render name="/WEB-INF/views/layout/common-layout.jsp" title="${titleStr}">
    <s:layout-component name="mainContent">
        <div class="page-header">
            <h2>${titleStr}</h2>
        </div>
        <h3 class="sub-content"><fmt:message key="reset.password.description"/></h3>
        <div class="activate-box">
            <s:form beanclass="com.wiseach.teamlog.web.actions.ResetPasswordActionBean">
                <s:hidden name="uuid" value="${actionBean.uuid}"/>
                <div class="control-group">
                    <div class="controls">
                        <div class="input-prepend">
                            <fmt:message key="reset.password.password" var="yourPassword"/>
                            <span class="add-on"><i class="icon-lock"></i></span><input id="resetPw" class="input-width-std" name="resetNewPassword" type="password" placeholder="${yourPassword}" title="${yourPassword}">
                        </div>
                    </div>
                    <s:errors field="uuid"/>
                    <s:errors field="resetNewPassword"/>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <div class="input-prepend">
                            <fmt:message key="reset.password.password.retype" var="retypePassword"/>
                            <span class="add-on"><i class="icon-repeat"></i></span><input class="input-width-std" name="resetRetypePassword" type="password" placeholder="${retypePassword}" title="${retypePassword}">
                        </div>
                    </div>
                    <s:errors field="resetRetypePassword"/>
                </div>
                <div class="control-group">
                    <s:submit name="activate" class="btn btn-primary"><fmt:message key="reset.password.button"/></s:submit>
                </div>
            </s:form>

            <%@include file="/WEB-INF/views/include/password-indicator.jsp"%>
        </div>
        <script type="text/javascript">$('#resetPw').keyup(function(){checkStrength($(this).val())}).focus();</script>
    </s:layout-component>
</s:layout-render>