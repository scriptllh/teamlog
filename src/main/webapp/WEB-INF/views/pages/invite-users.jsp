<%@include file="/WEB-INF/views/public/tagLib.jsp"%>
<fmt:message key="invite.user.title" var="titleStr"/>
<s:layout-render name="/WEB-INF/views/layout/login-layout.jsp" title="${titleStr}">
    <s:layout-component name="mainContent">
        <div class="page-header">
            <h2>${titleStr}</h2>
        </div>
        <h3 class="sub-content"><fmt:message key="invite.user.description"/></h3>
        <div class="form-box">
            <s:form beanclass="com.wiseach.teamlog.web.actions.InviteUserActionBean" class="form-inline">
                <div class="input-prepend">
                    <fmt:message key="invite.user.info" var="inputWorkType"/>
                    <span class="add-on"><i class="icon-envelope"></i></span><input id="inviteEmail" style="width: 740px;" name="inviteEmail" value="${actionBean.inviteEmail}" type="text" placeholder="${inputWorkType}" title="${inputWorkType}">
                </div>
                <s:submit name="invite" class="btn btn-primary"><fmt:message key="button.ok.label"/></s:submit>
            </s:form>
            <s:errors field="inviteEmail"/>
        </div>

        <fmt:message key="invite.user.allow.inviteNewUser" var="iniviteNewUser"/>
        <fmt:message key="invite.user.disable" var="disableUser"/>
        <div class="form-box" style="padding-top: 0px;">
            <div class="user-list">
                <c:forEach items="${actionBean.userList}" var="user">
                    <div class="user-info">
                        <img src="${user.avatar==null?'/res/imgs/default-avatar.png':user.avatar}" alt="${user.username}" class="huge-avatar">
                        <p><a href="mailto:${user.email}">${user.username}</a></p>
                        <%--<label class="checkbox">--%>
                            <%--<input type="checkbox" id="allowInvitation" name="allowInvitation" value="yes" checked="checked">--%>
                            <%--${iniviteNewUser}--%>
                        <%--</label>--%>
                        <label class="checkbox">
                            <input type="checkbox" id="${user.id}" name="disabledUser" value="true" ${user.disabled==null or user.disabled == false?'':'checked'} ${user.admin?'disabled':''}>
                            ${disableUser}
                        </label>
                    </div>
                </c:forEach>
            </div>
            <p class="help-block">
                <fmt:message key="invite.user.help">
                    <fmt:param value="${iniviteNewUser}"/>
                    <fmt:param value="${disableUser}"/>
                </fmt:message>
            </p>
            <a href="${rootUri}/invite-users/finish" class="btn btn-primary" style="margin-top: 20px;"><fmt:message key="invite.user.button.finish"/></a>
        </div>

        <script type="text/javascript">
            $(function () {
                        $('#inviteEmail').focus();
                        $(".user-list :checkbox").on('click', function (e) {
                            var isChecked = ($(e.target).attr('checked') == 'checked');
                            $.post('${rootUri}/user-management/userState/' + $(e.target).attr('id') + '/' + isChecked);
                        });
                    }
            );
        </script>

    </s:layout-component>
</s:layout-render>