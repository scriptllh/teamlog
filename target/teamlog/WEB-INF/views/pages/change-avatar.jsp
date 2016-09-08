<%@include file="/WEB-INF/views/public/tagLib.jsp"%>
<fmt:message key="change.avatar.title" var="titleStr"/>
<s:layout-render name="/WEB-INF/views/layout/login-layout.jsp" title="${titleStr}">
    <s:layout-component name="mainContent">
        <link rel="stylesheet" href="${rootUri}/res/jcrop/jquery.Jcrop.css" media="all" type="text/css">
        <%--<link rel="stylesheet" href="/res/upload/css/jquery.fileupload-ui.css" media="all" type="text/css">--%>
        <script type="text/javascript" src="${rootUri}/res/jcrop/jquery.Jcrop.js"></script>
        <script src="${rootUri}/res/upload/vendor/jquery.ui.widget.js"></script>
        <script src="${rootUri}/res/upload/jquery.iframe-transport.js"></script>
        <script src="${rootUri}/res/upload/jquery.fileupload.js"></script>
        <div class="page-header">
            <h2>${titleStr}</h2>
        </div>
        <h3 class="sub-content"><fmt:message key="change.avatar.description"/></h3>
        <div class="row-fluid sub-content" style="margin-top: 20px;">
            <div class="span7">
                <div class="avatar-result"><img id="jcrop-view" src="${actionBean.bigAvatar}" alt="default avatar"></div>
                <s:errors field="demistion"/>
            </div>
            <div class="span5" style="position: relative;">
                <span id="upload" class="btn upload-btn"><fmt:message key="change.avatar.upload"/><input id="avatarInput" name="avatar" type="file"></span>
                <div style="position: absolute;top:182px;">
                    <p><fmt:message key="change.avatar.preview"/></p>
                    <div class="avatar-preview">
                        <img id="preview" src="${actionBean.bigAvatar}" alt="preview avatar">
                    </div>
                </div>
            </div>
        </div>

        <div class="sub-content control-group" style="margin-top: 20px;">
            <s:form beanclass="com.wiseach.teamlog.web.actions.ChangeAvatarActionBean">
                <s:hidden name="dimension" id="dimension"/>
                <s:submit name="resizeAvatar" class="btn btn-primary"><fmt:message key="button.finish.label"/></s:submit>
            </s:form>
        </div>
        <script type="text/javascript">
            $(function(){


                function initJcrop() {
                    var jcropView = $('#jcrop-view');

                    jcropView.Jcrop({
                        onChange: showPreview,
                        onSelect: showPreview,
                        onRelease: hidePreview,
                        setSelect:[0,0,160,160],
                        aspectRatio: 1
                    });

                    // Our simple event handler, called from onChange and onSelect
                    // event handlers, as per the Jcrop invocation above
                    function showPreview(coords)
                    {
                        if (parseInt(coords.w) > 0)
                        {
                            var rx = 100 / coords.w;
                            var ry = 100 / coords.h;

                            $('#preview').css({
                                width: Math.round(rx * $('#jcrop-view').width()) + 'px',
                                height: Math.round(ry * $('#jcrop-view').height()) + 'px',
                                marginLeft: '-' + Math.round(rx * coords.x) + 'px',
                                marginTop: '-' + Math.round(ry * coords.y) + 'px'
                            }).show();
                        }
                        $("#dimension").attr('value',coords.x+','+coords.y+','+coords.w);
                    }

                    function hidePreview()
                    {
                        $('#preview').stop().fadeOut('fast');
                    }
                }

                initJcrop();

                $('#upload').fileupload({
                    url:'${rootUri}/change-avatar/uploadAvatar',
                    fileInput:$('#avatarInput'),
                    done:function(e,d){
                        if (d!='') {
                            var src = "${rootUri}/avatar/avatarTemp/"+ d.result+"?t="+new Date().getMilliseconds();
                            $('.avatar-result').empty().append($('<img id="jcrop-view">').attr('src',src));
                            initJcrop();
                            $('#preview').attr("src",src).show();
                        }
                    }
                })
            });
        </script>
    </s:layout-component>
</s:layout-render>