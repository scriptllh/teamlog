<%@include file="/WEB-INF/views/public/tagLib.jsp"%>

<div class="f-sub-header">
    <h4><i class="icon-user" style="margin:0 10px"></i><fmt:message key="browse.log.people.title"/></h4>
</div>
<div id="userId" style="display: none;">${userId}</div>
<div>
    <div class="controls">
        <div class="input-append">
            <input style="width: 156px;" id="filterInput" size="16" type="text"><span class="add-on"><i class="icon-filter"></i></span>
        </div>
    </div>
</div>

<div>
    <ul class="f-ul">
        <li><a href="#" id="a0"><img src="${rootUri}/res/imgs/all-user.png" alt="photo" class="normal-avatar"><fmt:message key="browse.log.people.item.all"/></a><input id="c0" type="checkbox"></li>
        <<_.each(people,function(p){ >>
        <li><a href="#" id="a<<=p.id>>"><img src="<<=p.avatar>>" alt="photo" class="normal-avatar" title="<<=p.username>>"> <<=p.ellipsisName>></a> <input id="c<<=p.id>>" type="checkbox"></li>
        << }); >>
    </ul>
</div>