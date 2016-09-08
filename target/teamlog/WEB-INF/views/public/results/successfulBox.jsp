<%@include file="/WEB-INF/views/public/tagLib.jsp"%>

<s:layout-render name="/WEB-INF/views/layout/common-layout.jsp">
    <s:layout-component name="mainContent">
        <div class="page-header">
            <h2><fmt:message key="${successfulTitle == null ? 'successful.box.title': successfulTitle }"/></h2>
        </div>
        <h3 class="sub-content"><fmt:message key="${successfulDescription == null ? 'successful.box.description':successfulDescription}"/></h3>

        <div class="sub-content">
            <p style="margin: 20px 0;">
                ${successfulInfo}
            </p>
            <a href="${rootUri}/login" class="btn btn-primary"><fmt:message key="${successfulButtonText == null ? 'successful.box.go.login': successfulButtonText}"/></a>
        </div>
    </s:layout-component>
</s:layout-render>