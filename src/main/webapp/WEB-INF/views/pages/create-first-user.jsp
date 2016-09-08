<%@include file="/WEB-INF/views/public/tagLib.jsp"%>
<fmt:message key="create.first.user.title" var="titleStr"/>
<s:layout-render name="/WEB-INF/views/layout/common-layout.jsp" title="${titleStr}">
    <s:layout-component name="mainContent">
        <div class="page-header">
            <h2>${titleStr}</h2>
        </div>
        <h3 class="sub-content"><fmt:message key="create.first.user.description"/></h3>

        <div class="form-box">
            <s:form beanclass="com.wiseach.teamlog.web.actions.CreateFirstUserActionBean" class="form-inline">
                <div class="input-prepend">
                    <fmt:message key="create.first.user.info" var="firstUserEmail"/>
                    <span class="add-on"><i class="icon-envelope"></i></span><input id="firstUser" style="width: 350px;" name="firstUserEmail" value="${actionBean.firstUserEmail}" type="text" placeholder="${firstUserEmail}" title="${firstUserEmail}">
                </div>
                <s:submit name="create" class="btn btn-primary"><fmt:message key="button.ok.label"/></s:submit>
            </s:form>
            <s:errors field="firstUserEmail"/>
        </div>
        <%@include file="/WEB-INF/views/include/email-failed-reasons.jsp"%>
    </s:layout-component>
</s:layout-render>
<script type="text/javascript">$('#firstUser').focus()</script>