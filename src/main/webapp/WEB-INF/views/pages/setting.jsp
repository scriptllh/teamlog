<%@include file="/WEB-INF/views/public/tagLib.jsp"%>
<fmt:message key="setting.log.type.title" var="titleStr"/>
<s:layout-render name="/WEB-INF/views/layout/login-layout.jsp" title="${titleStr}">
    <s:layout-component name="mainContent">
        <div class="page-header">
            <h2>${titleStr}</h2>
        </div>
        <h3 class="sub-content"><fmt:message key="setting.log.type.description"/></h3>
        <div class="form-box">
            <s:form beanclass="com.wiseach.teamlog.web.actions.SettingActionBean" class="form-inline">
                <div class="input-prepend">
                    <fmt:message key="setting.log.type.info" var="inputWorkType"/>
                    <span class="add-on"><i class="icon-th-large"></i></span><input id="workType" style="width: 740px;" name="workType" value="${actionBean.workType}" type="text" placeholder="${inputWorkType}" title="${inputWorkType}">
                </div>
                <s:submit name="create" class="btn btn-primary"><fmt:message key="button.ok.label"/></s:submit>
            </s:form>
            <s:errors field="workType"/>
        </div>

        <div class="form-box" style="padding-top: 0px;">
            <div class="clearfix" style="border: 1px solid #EEE;padding: 10px;">
                <c:if test="${actionBean.allWorkType!=null}">
                    <c:forEach items="${actionBean.allWorkType}" var="workType">
                        <div class="work-type">
                            ${workType.name}
                            <a class="btn btn-mini btn-danger del-work-type" title='<fmt:message key="setting.log.type.delete"/>' id="${workType.id}"><i class="icon-trash icon-white"></i></a>
                        </div>
                    </c:forEach>
                </c:if>
                <c:if test="${actionBean.allWorkType==null}">
                    <div class="warning"><fmt:message key="setting.log.type.warning.description" /></div>
                </c:if>
            </div>
            <a href="${rootUri}/invite-users/finish" class="btn btn-primary clearfix" style="margin-top: 20px;"><fmt:message key="setting.log.type.button.finish"/></a>
        </div>

        <script type="text/javascript">
            $(function () {
                        $('#inviteEmail').focus();
                        $('.del-work-type').hide().on('click',function(e) {
                            if (confirm('<fmt:message key="setting.log.type.delete.confirm"/>')) {
                                var $this = $(this);
                                $.post('${rootUri}/setting/deleteWorkType/',{typeId:$(this).attr('id')},function(d){
                                    if (d) {
                                        $this.parent().detach();
                                    }
                                });
                            }

                            e.preventDefault();
                        });
                        $('.work-type').hover(function(e){
                            $(this).css('background-color','#eff').find('a').show();
                        },function(){
                            $(this).css('background-color','transparent').find('a').hide();
                        });
                    }
            );
        </script>

    </s:layout-component>
</s:layout-render>