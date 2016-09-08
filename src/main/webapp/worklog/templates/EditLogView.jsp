<%@include file="/WEB-INF/views/public/tagLib.jsp"%>

<div id="worklogCalendar">

</div>
<div id="editor" class="popover left" style="width: 400px;">
    <div class="arrow"></div>
    <h3 class="popover-title"><fmt:message key="edit.log.form.title"/></h3>
    <div class="popover-content">
        <div>
            <form id="worklogForm" action="${rootUri}/worklog-data/updateContent/" method="post">
                <input id="id" name="id" type="hidden">
                <label for="description"><fmt:message key="edit.log.form.description"/></label>
                <textarea id="description" style="width: 354px;" rows="5" name="description" class="focused"></textarea>
                <label for="tags"><fmt:message key="edit.log.form.tags"/></label>
                <select id="tags" name="tag"></select>
                <div style="margin-top: 10px;">
                    <button type="submit" class="btn btn-primary"><fmt:message key="button.ok.label"/></button>
                    <a id="cancelBtn" href="#" style="margin-left:10px;"><fmt:message key="button.cancel.label"/></a>
                    <a id="deleteBtn" href="#" class="btn btn-danger pull-right hide" title='<fmt:message key="button.delete.label"/>'><i class="icon-trash icon-white"></i></a>
                </div>
            </form>
        </div>
    </div>
</div>
