<%@include file="/WEB-INF/views/public/tagLib.jsp"%>

<div class="f-sub-header">
    <h4><i class="icon-share" style="margin:0 10px"></i><fmt:message key="edit.log.people.title"/></h4>
</div>
<div>
    <div class="controls">
        <div class="input-append">
            <input style="width: 156px;" id="filterInput" size="16" type="text"><span class="add-on"><i class="icon-filter"></i></span>
        </div>
    </div>
</div>

<div>
    <ul class="f-ul">
        <<_.each(people,function(p){ >>
        <li><img src="<<=p.avatar>>" alt="photo" class="normal-avatar" title="<<=p.username>>"> <<=p.ellipsisName>><span class="pull-right label <<print(p.shared ? 'label-info' : 'hide');>>" style="cursor: pointer;" id="p<<=p.id>>" title="<<print(p.shared ? '<fmt:message key="edit.log.people.shared"/>' : '<fmt:message key="edit.log.people.not.shared"/>');>>"><i class="icon-share icon-white"></i></span></li>
        << }); >>
    </ul>
</div>