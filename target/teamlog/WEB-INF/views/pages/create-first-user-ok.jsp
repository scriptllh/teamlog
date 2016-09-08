<%@include file="/WEB-INF/views/public/tagLib.jsp"%>
<fmt:message key="create.first.user.ok.title" var="titleStr"/>
<s:layout-render name="/WEB-INF/views/layout/common-layout.jsp" title="${titleStr}">
    <s:layout-component name="mainContent">
        <div class="page-header">
            <h2>${titleStr}</h2>
        </div>
        <h3 class="sub-content"><fmt:message key="create.first.user.ok.description"/></h3>

        <div class="sub-content">
            <p style="margin: 20px 0;">
                <fmt:message key="create.first.user.ok.info">
                    <fmt:param value="${actionBean.firstUserEmail}"/>
                </fmt:message>
            </p>
            <a class="btn btn-primary" href="${rootUri}/login"><fmt:message key="create.first.user.ok.login"/></a>
        </div>
    </s:layout-component>
</s:layout-render>