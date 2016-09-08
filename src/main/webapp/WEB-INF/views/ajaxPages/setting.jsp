<%@include file="/WEB-INF/views/public/tagLib.jsp"%>
<!DOCTYPE HTML>
<s:layout-render name="/WEB-INF/views/layout/common-layout.jsp">
    <s:layout-component name="mainContent">
        <div class="row">
            <div class="span2">
                <img src="${rootUri}/res/imgs/default-avatar.png">
            </div>
            <div class="span2">
                <ul class="nav nav-list">
                    <li class="nav-header">
                        个人设置
                    </li>
                    <li>
                        <a href="#">修改密码</a>
                    </li>
                    <li>
                        <a href="#">修改头像</a>
                    </li>
                    <li>
                        <a href="#">修改个人资料</a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a href="#">注销(退出)</a>
                    </li>
                </ul>
            </div>
        </div>
    </s:layout-component>
</s:layout-render>
