<%@include file="/WEB-INF/views/public/tagLib.jsp"%>
<!DOCTYPE HTML>
<fmt:message key="profile.title" var="titleStr"/>
<s:layout-render name="/WEB-INF/views/layout/login-layout.jsp" title="${titleStr}">
    <s:layout-component name="mainContent">
        <div class="page-header">
            <h2>${titleStr}</h2>
        </div>

        <div class="form-box">
            <s:form beanclass="com.wiseach.teamlog.web.actions.ProfileActionBean" class="form-horizontal">
                <div class="control-group">
                    <label class="control-label"><fmt:message key="profile.base.email"/></label>
                    <div class="controls">
                        <label>${actionBean.userInfo.email}</label>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="userName"><fmt:message key="profile.base.userName"/></label>
                    <div class="controls">
                        <fmt:message key="profile.base.userName.help" var="userNameHelp"/>
                        <input type="text" id="userName" class="span7" name="userInfo.username" value="${actionBean.userInfo.username}" placeholder="${userNameHelp}" title="${userNameHelp}">
                        <s:errors field="userInfo.username"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="description"><fmt:message key="profile.base.description"/></label>
                    <div class="controls">
                        <fmt:message key="profile.base.description.help" var="userDescriptionHelp"/>
                        <textarea id="description" name="userInfo.description" class="span7" placeholder="${userDescriptionHelp}" title="${userDescriptionHelp}" rows="4">${actionBean.userInfo.description}</textarea>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="telephone"><fmt:message key="profile.base.tel"/></label>
                    <div class="controls">
                        <input type="tel" id="telephone" name="userInfo.telephone" class="span7" value="${actionBean.userInfo.telephone}">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="mobile"><fmt:message key="profile.base.mobile"/></label>
                    <div class="controls">
                        <input type="tel" id="mobile" name="userInfo.mobile" class="span7" value="${actionBean.userInfo.mobile}">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="qqNumber"><fmt:message key="profile.base.qq"/></label>
                    <div class="controls">
                        <input type="text" id="qqNumber" name="userInfo.qq" class="span7" value="${actionBean.userInfo.qq}">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="note"><fmt:message key="profile.base.note"/></label>
                    <div class="controls">
                        <fmt:message key="profile.base.note.help" var="noteHelp"/>
                        <textarea id="note" name="userInfo.note" class="span7" placeholder="${noteHelp}" title="${noteHelp}" rows="4">${actionBean.userInfo.note}</textarea>
                    </div>
                </div>
                <div class="control-group" style="padding-left: 160px;">
                    <s:submit name="save" class="btn btn-primary"><fmt:message key="button.finish.label"/></s:submit>
                </div>
            </s:form>
        </div>
        <script type="text/javascript">$('#username').focus();</script>
    </s:layout-component>
</s:layout-render>