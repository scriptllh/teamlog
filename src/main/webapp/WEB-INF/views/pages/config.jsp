<%@include file="/WEB-INF/views/public/tagLib.jsp" %>
<fmt:message key="config.title" var="titleStr"/>
<s:layout-render name="/WEB-INF/views/layout/common-layout.jsp" title="${titleStr}">
    <s:layout-component name="mainContent">
        <div class="page-header">
            <h2>${titleStr}</h2>
        </div>

        <div class="form-box">
            <s:form beanclass="com.wiseach.teamlog.web.actions.ConfigActionBean" class="form-horizontal">
        <div class="control-group">
            <label class="control-label" for="siteUrl"><fmt:message key="config.site.url"/></label>
            <div class="controls">
                <input type="text" id="siteUrl" class="span7" name="siteUrl" value="${actionBean.siteUrl}">
                <p class="help-block"><fmt:message key="config.site.url.help"/></p>
                <s:errors field="siteUrl"/>
                <c:if test="${not empty actionBean.urls}">
                    <p class="help-block"><fmt:message key="config.site.url.help.chosen"/></p>
                    <c:forEach var="url" items="${actionBean.urls}" >
                        <label class="radio">
                            <input type="radio" name="ipUrl" value="${url}" ${url eq actionBean.siteUrl?"checked":""}>
                                ${url}
                        </label>
                    </c:forEach>
                </c:if>
            </div>
        </div>


        <div class="control-group">
            <label class="control-label" for="userName"><fmt:message key="config.email.username"/></label>
            <div class="controls">
                <fmt:message key="config.email.username.help" var="userNameHelp"/>
                <input type="text" id="userName" class="span7" name="smtpUsername" value="${actionBean.smtpUsername}" placeholder="${userNameHelp}" title="${userNameHelp}">
                <s:errors field="smtpUsername"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="password"><fmt:message key="config.email.password"/></label>
            <div class="controls">
                <input type="password" id="password" class="span7" name="smtpPassword" value="${actionBean.smtpPassword}">
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="smtpHost"><fmt:message key="config.email.smtp.host"/></label>
            <div class="controls">
                <fmt:message key="config.email.smtp.host.help" var="smtpHostHelp"/>
                <input type="text" id="smtpHost" class="span7" name="smtpHost" value="${actionBean.smtpHost}" placeholder="${smtpHostHelp}" title="${smtpHostHelp}">
                <s:errors field="smtpHost"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="smtpPort"><fmt:message key="config.email.smtp.port"/></label>
            <div class="controls">
                <fmt:message key="config.email.smtp.port.help" var="smtpPortHelp"/>
                <input type="text" id="smtpPort" class="span7" name="smtpPort" value="${actionBean.smtpPort}" placeholder="${smtpPortHelp}" title="${smtpPortHelp}">
                <s:errors field="smtpPort"/>
            </div>
        </div>
        <div class="control-group">
            <div class="controls">
                <label for="smtpSsl">
                    <input type="checkbox" id="smtpSsl" name="ssl" ${actionBean.ssl=="true"?"checked":""}><fmt:message key="config.email.smtp.ssl"/>
                </label>
            </div>
        </div>
        <div class="control-group">
            <div class="controls">
                <label for="smtpTls">
                    <input type="checkbox" id="smtpTls" name="tls" ${actionBean.tls=="true"?"checked":""}><fmt:message key="config.email.smtp.tls"/>
                </label>
            </div>
        </div>
        <div class="control-group" style="padding-left: 160px;">
            <s:submit name="save" class="btn btn-primary"><fmt:message key="button.finish.label"/></s:submit>
        </div>
    </s:form>
        </div>
        <%--<%@include file="/WEB-INF/views/include/email-failed-reasons.jsp"%>--%>
    </s:layout-component>
</s:layout-render>
<script type="text/javascript">
    $('#siteUrl').focus()
    $('input:radio').change(function(){
        $('#siteUrl').val($(this).val());
    });
</script>

