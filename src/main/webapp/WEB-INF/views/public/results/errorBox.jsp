<%@include file="/WEB-INF/views/public/tagLib.jsp"%>

<s:layout-render name="/WEB-INF/views/layout/common-layout.jsp">
    <s:layout-component name="mainContent">
        <div class="page-header">
            <h2><fmt:message key="${errorTitle == null ? 'error.box.title': errorTile}"/></h2>
        </div>
        <h3 class="sub-content"><fmt:message key="${errorDescription}"/></h3>
        <div class="form-box">
            <a href="${rootUri}/" class="btn btn-primary"><fmt:message key="error.box.go.home" /> </a>
        </div>
    </s:layout-component>
</s:layout-render>