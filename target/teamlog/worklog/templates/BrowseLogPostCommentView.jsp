<%@include file="/WEB-INF/views/public/tagLib.jsp"%>

<div class="log-main-post"><fmt:message key="browse.log.add.comment"/></div>
<div class="log-main-post-form hide">
    <img src="<<=avatar>>" alt="<<=username>>" class="normal-avatar">
    <div>
        <div><textarea rows="3" style="width: 90%"></textarea></div>
        <div class="actionBtns">
            <button type="button" class="btn btn-primary"><fmt:message key="button.ok.label"/></button>
            <a class="btn"><fmt:message key="button.cancel.label"/></a>
        </div>
        <div class="doAction hide">
            <div class="progress progress-striped active" style="width: 200px;">
                <div class="bar" style="width: 200px;" title='<fmt:message key="ajax.save.data"/>'></div>
            </div>
        </div>
    </div>
</div>
