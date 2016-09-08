<%@include file="/WEB-INF/views/public/tagLib.jsp"%>

<s:layout-definition>
    <s:layout-render name="/WEB-INF/views/layout/common-layout.jsp" title="${title}">
        <s:layout-component name="headerContent"><%@include file="/WEB-INF/views/include/setting.jsp"%></s:layout-component>
        <s:layout-component name="mainContent">${mainContent}</s:layout-component>
    </s:layout-render>
</s:layout-definition>
