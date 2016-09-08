<%@include file="/WEB-INF/views/public/tagLib.jsp"%>
<!DOCTYPE HTML>
<s:layout-render name="/WEB-INF/views/layout/common-layout.jsp">
    <s:layout-component name="mainContent">
        <div class="row">
            <div class="span2">
                <img src="${rootUri}/res/imgs/default-avatar.png">
                <p style="margin-top: 10px;text-align: center;">
                    张三丰(<a href="mailto:zhangsf@Gmail.com">zhangsf@Gmail.com</a>)
                </p>
            </div>
            <div class="span6">
                <table class="table">
                    <tbody>
                        <tr>
                            <td>个人简介：。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。</td>
                        </tr>
                        <tr>
                            <td>电话：0755-12345667</td>
                        </tr>
                        <tr>
                            <td>手机：136-31528241</td>
                        </tr>
                        <tr>
                            <td>QQ：2233448</td>
                        </tr>
                        <tr>
                            <td>备注：2233448</td>
                        </tr>
                    </tbody>
                </table>
                <div class="control-group">
                    <button type="submit" class="btn btn-primary">查看Ta的工作日志</button>
                    <button class="btn">查看Ta的留言</button>
                </div>
            </div>
        </div>

    </s:layout-component>
</s:layout-render>
