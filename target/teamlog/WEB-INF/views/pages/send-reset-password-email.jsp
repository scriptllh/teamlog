<%@include file="/WEB-INF/views/public/tagLib.jsp"%>
<fmt:message key="reset.title" var="titleStr"/>
<s:layout-render name="/WEB-INF/views/layout/common-layout.jsp" title="${titleStr}">
    <s:layout-component name="mainContent">
        <div class="page-header">
            <h2>${titleStr}</h2>
        </div>
        <h3 class="sub-content"><fmt:message key="reset.description"/></h3>

        <div class="form-box">
            <s:form beanclass="com.wiseach.teamlog.web.actions.SendRestPasswordEmailActionBean" class="form-inline">
                <div class="input-prepend">
                    <span class="add-on"><i class="icon-envelope"></i></span><s:text id="resetEmail" name="resetEmail" value="${actionBean.resetEmail}" style="width: 350px;"/>
                </div>
                <s:submit name="send" class="btn btn-primary"><fmt:message key="reset.send.label"/></s:submit>
            </s:form>
            <s:errors field="resetEmail"/>
        </div>
        <script type="text/javascript">$('#resetEmail').focus()</script>
        <%@include file="/WEB-INF/views/include/email-failed-reasons.jsp"%>
    </s:layout-component>
</s:layout-render>