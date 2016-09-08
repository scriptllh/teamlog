<%@include file="/WEB-INF/views/public/tagLib.jsp"%>

<s:layout-definition>
    <!DOCTYPE HTML>
    <html>
    <head>
        <title>Teamlog|${title}</title>
        <META http-equiv="Content-Type" content="text/html;charset=UTF-8">
        <link rel="stylesheet" href="${rootUri}/res/bootstrap/css/bootstrap.css" media="all" type="text/css">
        <link rel="stylesheet" href="${rootUri}/res/css/normal.css" media="all" type="text/css">
        <link rel="stylesheet" href="${rootUri}/res/reject/jquery.reject.css" media="all" type="text/css">
        <script type="text/javascript" src="${rootUri}/res/js/jquery-1.7.2.js"></script>
        <script type="text/javascript" src="${rootUri}/res/reject/jquery.reject.min.js"></script>
    </head>
    <body onload="$.reject({display: ['firefox','chrome','safari'],imagePath:'${rootUri}/res/reject/imgs/',header: '<fmt:message key="reject.head"/>',paragraph1: '<fmt:message key="reject.p1"/>',  paragraph2: '<fmt:message key="reject.p2"/>',closeMessage: '<fmt:message key="reject.close.tips"/>',closeLink: '<fmt:message key="reject.close"/>'});">
    <div class="wrapper">
        <div class="header content clearfix">
            <a href="${rootUri}/"><img src="${rootUri}/res/imgs/teamlog-logo.png" class="logo-img" alt="teamlog logo image"></a>
            <div class="header-content"><s:layout-component name="headerContent"/></div>
        </div>
        <div class="main content clearfix">
            <s:layout-component name="mainContent"/>
        </div>
        <div class="footer clearfix">

        </div>
    </div>
    </body>
    </html>
</s:layout-definition>
