<%@include file="/WEB-INF/views/public/tagLib.jsp"%>
<!DOCTYPE HTML>
<fmt:message key="tag.selection.title" var="titleStr"/>
<fmt:message key="tag.selection.add.error" var="addErrorMsg"/>
<fmt:message key="tag.selection.remove.error" var="removeErrorMsg"/>
<fmt:message key="tag.selection.all.empty" var="allEmptyErrorMsg"/>
<fmt:message key="tag.selection.user.empty" var="userEmptyErrorMsg"/>
<s:layout-render name="/WEB-INF/views/layout/login-layout.jsp" title="${titleStr}">
    <s:layout-component name="mainContent">
        <div class="page-header">
            <h2>${titleStr}</h2>
        </div>

        <div class="container-fluid">
            <div class="row-fluid">
                <div class="span6">
                    <h4><fmt:message key="tag.selection.all.title"/></h4>
                    <p class="help-block"><fmt:message key="tag.selection.all.title.tips"/></p>
                    <ul class="tag-selection-ul" id="all-work-type">
                        <c:choose>
                            <c:when test="${empty actionBean.tags}">
                                <p class="help-block" style="margin: 10px;">${allEmptyErrorMsg}</p>
                            </c:when>
                            <c:otherwise>
                                <c:forEach items="${actionBean.tags}" var="tag">
                                    <li data-id="${tag.id}">${tag.name}<span title="<fmt:message key="tag.selection.add"/>"><i class="icon-plus"></i></span></li>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>

                    </ul>
                </div>
                <div class="span6">
                    <h4><fmt:message key="tag.selection.user.title"/></h4>
                    <p class="help-block"><fmt:message key="tag.selection.user.title.tips"/></p>
                    <ul class="tag-selection-ul" id="user-work-type">
                        <c:choose>
                            <c:when test="${empty actionBean.userTags}">
                                <p class="help-block" style="margin: 10px;">${userEmptyErrorMsg}</p>
                            </c:when>
                            <c:otherwise>
                                <c:forEach items="${actionBean.userTags}" var="tag">
                                    <li data-id="${tag.id}" data-user-tag-id="${tag.userTagId}">${tag.name}<span title="<fmt:message key="tag.selection.remove"/>""><i class="icon-minus"></i></span></li>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </div>
                <a href="${rootUri}/work-type-selection/finish" class="btn btn-primary clearfix pull-right" style="margin-top: 20px;"><fmt:message key="button.finish.label"/></a>
            </div>
        </div>

        <script type="text/javascript">
            function moveItem($item,$originalItem,$parent) {
                if ($parent.find('li').length==0) $parent.empty();
                $item.appendTo($parent).fadeIn();
                $originalItem.fadeOut().detach();
                var $all = $('#all-work-type'),$user = $('#user-work-type');
                if ($all.children().length==0) $all.html('<p class="help-block" style="margin: 10px;">${allEmptyErrorMsg}</p>');
                if ($user.children().length==0) $user.html('<p class="help-block" style="margin: 10px;">${userEmptyErrorMsg}</p>');
            }

            $('#all-work-type').on('click','li>span',function(e){
                var $parent = $(this).parent();
                $.post("${rootUri}/work-type-selection/select",{id:$parent.attr("data-id")},function(userTagId) {
                    if ($.isNumeric(userTagId)) {
                        moveItem($("<li>"+$parent.html().replace('plus','minus')+"</li>").attr('data-id',$parent.attr('data-id')).attr('data-user-tag-id',userTagId),$parent,$('#user-work-type'))
                    } else {
                        alert("${addErrorMsg}");
                    }
                }).error(function(){
                    alert("${addErrorMsg}");
                });
            });
            $('#user-work-type').on('click','li>span',function(e){
                var $parent = $(this).parent();
                $.post("${rootUri}/work-type-selection/remove",{id:$parent.attr("data-user-tag-id")},function(result) {
                    if (result) {
                        moveItem($("<li>"+$parent.html().replace('minus','plus')+"</li>").attr('data-id',$parent.attr('data-id')),$parent,$('#all-work-type'));
                    } else {
                        alert("${removeErrorMsg}");
                    }
                }).error(function(){
                    alert("${removeErrorMsg}");
                });
            });
        </script>
    </s:layout-component>
</s:layout-render>