<%@include file="/WEB-INF/views/public/tagLib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>TeamLog|<fmt:message key="worklog.title"/> </title>
    <META http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <link rel="stylesheet" href="${rootUri}/res/bootstrap/css/bootstrap.css" media="all" type="text/css">
    <link rel="stylesheet" href="${rootUri}/res/reject/jquery.reject.css" media="all" type="text/css">
    <script type="text/javascript" src="${rootUri}/res/jqueryui/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="${rootUri}/res/js/underscore-1.3.3.js"></script>
    <script type="text/javascript" src="${rootUri}/res/js/backbone-0.9.2.js"></script>
    <script type="text/javascript" src="${rootUri}/res/reject/jquery.reject.min.js"></script>
</head>
<body onload="$.reject({display: ['firefox','chrome','safari'],imagePath:'${rootUri}/res/reject/imgs/',header: '<fmt:message key="reject.head"/>',paragraph1: '<fmt:message key="reject.p1"/>',  paragraph2: '<fmt:message key="reject.p2"/>',closeMessage: '<fmt:message key="reject.close.tips"/>',closeLink: '<fmt:message key="reject.close"/>'});">
<div class="wrapper">
    <div class="header content clearfix">
        <a href="${rootUri}/"><img src="${rootUri}/res/imgs/teamlog-logo.png" class="logo-img" alt="teamlog logo image"></a>
        <div class="msg-box"><span></span><a href="#" class="close"><fmt:message key="button.close.label"/></a></div>
        <div class="header-content"><%@include file="/WEB-INF/views/include/setting.jsp"%></div>
    </div>
    <div class="f-header-padding"></div>
    <div class="f-main clearfix">
        <div class="f-box"></div>
        <div class="function-list">
            <div id="edit-log" class="f-item">
                <a href="#editLog" class="f-link">
                    <div class="f-link-wrapper">
                        <div class="edit-log-active"></div>
                        <div class="f-link-text active"><fmt:message key="worklog.fill.label"/></div>
                    </div>
                </a>
                <div class="f-item-active-line"></div>
                <div class="f-item-active-color"></div>
            </div>
            <div id="view-log" class="f-item">
                <a href="#viewLog" class="f-link">
                    <div class="f-link-wrapper">
                        <div class="view-log"></div>
                        <div class="f-link-text"><fmt:message key="worklog.view.label"/></div>
                    </div>
                </a>
            </div>
        </div>
        <div class="f-content">
            <div class="f-content-inner">
                <div class="f-scroll-indicator"></div>
                <div class="f-top-line">
                    <div class="f-left-corner">
                        <div class="f-left-corner-1"></div>
                        <div class="f-left-corner-2"></div>
                    </div>
                    <div class="f-right-corner">
                        <div class="f-right-corner-1"></div>
                        <div class="f-right-corner-2"></div>
                    </div>
                </div>
                <div id="data-container" style="padding: 20px;min-height: 540px;">
                    <%--<%@include file="/WEB-INF/views/mockJsp/view.jsp"%>--%>
                </div>
                <%--<%@include file="/test.html"%>--%>
            </div>
        </div>
    </div>
    <div class="function-context-bar">
        <%--<%@include file="/WEB-INF/views/mockJsp/browse.html"%>--%>
        <%--<%@include file="/mockJsp/share.html"%>--%>
    </div>
    <div class="footer clearfix">

    </div>
    <script type="text/javascript">
        window.i18n = {
            addComment:'<fmt:message key="browse.log.add.comment"/>',
            okButton:'<fmt:message key="button.ok.label"/>',
            cancelButton:'<fmt:message key="button.cancel.label"/>',
            timeFormat:'<fmt:message key="time.format"/>',
            hourStr:'<fmt:message key="text.hour.string"/>',
            deleteComfirm:'<fmt:message key="edit.log.confirm.msg"/>',
            worklogDataEmpty:'<fmt:message key="data.worklog.empty"/>',
            backToTop:'<fmt:message key="button.go.top.label"/>',
            noDescription:'<fmt:message key="edit.log.form.description.none"/>',
            noTag:'<fmt:message key="edit.log.form.tags.none"/>',
            sharePerson:'<fmt:message key="edit.log.people.shared"/>',
            notSharePerson:'<fmt:message key="edit.log.people.not.shared"/>'
        };
        window.rootUri = '${rootUri}';
        $(".close").click(function(e){
            $(this).parent().fadeOut();
            e.preventDefault();
        }).parent().hide();

    </script>
    <script type="text/javascript" src="${rootUri}/res/js/jquery.prettydate.js"></script>
    <script type="text/javascript" src="${rootUri}/res/bDate/date.js"></script>
    <link rel="stylesheet" href="${rootUri}/res/bDate/daterangepicker.css" media="all" type="text/css">
    <script type="text/javascript" src="${rootUri}/res/bDate/daterangepicker.js"></script>

    <link rel="stylesheet" href="${rootUri}/res/jqueryui/css/ui-lightness/jquery-ui-1.8.23.custom.css" media="all" type="text/css">
    <script type='text/javascript' src='${rootUri}/res/jqueryui/js/jquery-ui-1.8.23.custom.min.js'></script>
    <script type="text/javascript" src="${rootUri}/res/fullCalendar/fullcalendar.js"></script>
    <link rel='stylesheet' type='text/css' href='${rootUri}/res/fullCalendar/fullcalendar.css' />
    <link rel='stylesheet' type='text/css' href='${rootUri}/res/fullCalendar/fullcalendar.print.css' media='print' />

    <script type="text/javascript" src="${rootUri}/res/js/jquery.form.3.14.js"></script>

    <link rel="stylesheet" href="${rootUri}/res/css/func.css" media="all" type="text/css">

    <link rel="stylesheet" href="${rootUri}/res/jqPlot/jquery.jqplot.css" media="all" type="text/css">
    <script type="text/javascript" src="${rootUri}/res/jqPlot/jquery.jqplot.js"></script>
    <script type="text/javascript" src="${rootUri}/res/jqPlot/plugins/jqplot.barRenderer.js"></script>
    <script type="text/javascript" src="${rootUri}/res/jqPlot/plugins/jqplot.highlighter.js"></script>
    <script type="text/javascript" src="${rootUri}/res/jqPlot/plugins/jqplot.categoryAxisRenderer.js"></script>
    <script type="text/javascript" src="${rootUri}/res/jqPlot/plugins/jqplot.pointLabels.js"></script>

    <script type="text/javascript" src="${rootUri}/res/bootstrap/js/bootstrap.js"></script>
    <script type="text/javascript" src="${rootUri}/worklog/utils.js"></script>
    <script type="text/javascript" src="${rootUri}/worklog/models/worklogModels.js"></script>
    <script type="text/javascript" src="${rootUri}/worklog/views/browseViews.js"></script>
    <script type="text/javascript" src="${rootUri}/worklog/main.js"></script>
    <script type="text/javascript" src="${rootUri}/res/js/scrolltotop.js"></script>
</div>
</body>
</html>