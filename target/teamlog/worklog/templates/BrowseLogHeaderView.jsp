<%@include file="/WEB-INF/views/public/tagLib.jsp"%>

<div class="page-header clearfix" style="margin: 0px;padding: 0;">
    <button id="showReportBtn" type="button" class="btn" data-toggle="button"><fmt:message key="browse.log.show.reports"/> </button>
    <a id="exportReportBtn" type="button" class="btn" href="#"><fmt:message key="browse.log.show.reports.export"/> </a>
    <div id="reportrange" class="pull-right well" style="padding: 9px;cursor: pointer;">
        <i class="icon-calendar icon-large"></i>
        <jsp:useBean id="now" class="java.util.Date" scope="page" />
        <span></span>
    </div>
</div>
<div id="work-log-reports" class="row-fluid" style="display:none;">
    <%--<div id="peopleChart" class="span4"></div>--%>
    <div class="span6">
        <p><fmt:message key="browse.log.report.title.people"/></p>
        <div id="peopleChart"></div>
        <p><fmt:message key="browse.log.report.title.category"/></p>
        <div id="categoryChart"></div>
    </div>
    <div class="span6">
        <p><fmt:message key="browse.log.report.title.comment"/></p>
        <div id="commentChart"></div>
        <p><fmt:message key="browse.log.report.title.like"/></p>
        <div id="likeChart"></div>
    </div>
</div>
<div id="work-log-container"></div>
<%--<fmt:message key="worklog.this.default.date">--%>
    <%--<fmt:param value="${now}"/>--%>
    <%--<fmt:param value="${now}"/>--%>
<%--</fmt:message>--%>