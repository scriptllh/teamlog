<%--<%@include file="/WEB-INF/views/public/tagLib.jsp" %>--%>
<div style="padding: 8px 0;">
    <div class="login-info"><span id="username">${userName}</span> <img src="${avatar}" class="normal-avatar"><span id="userId" class="hide">${userId}</span> </div>
    <div id="settingMenu" class="row inner-box form-box">
        <div class="span2">
            <img src="${avatar}">
        </div>
        <div class="span2">
            <ul class="nav nav-list">
                <li class="nav-header">
                    <fmt:message key="setting.menu.title"/>
                </li>
                <li>
                    <a href="${rootUri}/change-password"><fmt:message key="setting.menu.change.password"/></a>
                </li>
                <li>
                    <a href="${rootUri}/change-avatar"><fmt:message key="setting.menu.change.avatar"/></a>
                </li>
                <c:if test="${userName=='admin'}">
                    <li>
                        <a href="${rootUri}/invite-users"><fmt:message key="setting.menu.invite.users"/></a>
                    </li>
                    <li>
                        <a href="${rootUri}/setting"><fmt:message key="setting.menu.define.work-type"/></a>
                    </li>
                    <li>
                        <a href="${rootUri}/config"><fmt:message key="config.title"/></a>
                    </li>
                </c:if>
                <c:if test="${userName!='admin'}">
                    <li>
                        <a href="${rootUri}/profile"><fmt:message key="setting.menu.change.profile"/></a>
                    </li>
                    <li>
                        <a href="${rootUri}/work-type-selection"><fmt:message key="setting.menu.select.work-type"/></a>
                    </li>
                </c:if>
                <li class="divider"></li>
                <li>
                    <a href="${rootUri}/logout"><fmt:message key="setting.menu.logout"/></a>
                </li>
            </ul>
        </div>
    </div>
</div>

<script type="text/javascript">

    $("#settingMenu").appendTo($("body"));
    $('.login-info').click(function(e){
        $('body').animate({scrollTop:0},function(){
            $("#settingMenu").slideToggle();
        });

        e.stopPropagation();
    });
    $('body').click(function() {
        $("#settingMenu").fadeOut();
    })
    $(".login-info img,#settingMenu img").each(function(i,d){
        var newSrc = $(d).attr('src')+'?t='+new Date().getMilliseconds();
        $(d).attr('src',newSrc);
    });
</script>
