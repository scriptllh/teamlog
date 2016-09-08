<%@include file="/WEB-INF/views/public/tagLib.jsp"%>
<fmt:message key="send.title" var="titleStr"/>
<s:layout-render name="/WEB-INF/views/layout/common-layout.jsp" title="${titleStr}">
    <s:layout-component name="mainContent">
        <div class="page-header">
            <h2>${titleStr}</h2>
        </div>
        <h3 class="sub-content"><fmt:message key="send.description"/></h3>

        <div class="sub-content">
            <p style="margin: 20px 0;">
                <fmt:message key="send.info">
                    <fmt:param value="${actionBean.resetEmail}"/>
                </fmt:message>
            </p>
            <a href="${rootUri}/login" class="btn btn-primary"><fmt:message key="send.login.label"/></a>
        </div>
    </s:layout-component>
</s:layout-render>