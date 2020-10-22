<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>员工面板</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />

    <script src="${ctx}/boot.js" type="text/javascript"></script>


    <style type="text/css">
        html, body
        {
            padding:0;
            margin:0;
            border:0;
            height:100%;
            overflow:hidden;
        }
    </style>
</head>
<body>

<form id="form1" method="post">
    <input name="id" class="mini-hidden" />
    <div style="padding-left:11px;padding-bottom:5px;">
        <table style="table-layout:fixed;">
            <tr>
                <td>就诊人姓名：</td>
                <td style="width:150px;">
                    <div id="name" name="userId" class="mini-combobox" style="width:150px;"  popupWidth="400" textField="name" valueField="id"
                         url="${ctx}/sys/user/queryList" value="" multiSelect="false"  showClose="false" >
                        <div property="columns">
                            <div header="ID" field="id"></div>
                            <div header="姓名" field="name"></div>
                        </div>
                    </div>
                </td>
                <td>就诊号：</td>
                <td style="width:150px;">
                    <input id="code" name="code" class="mini-textbox" style="width:200px;"  required="true"  emptyText="请输入就诊号" readonly/>
                </td>
            </tr>
            <tr>
                <td >备注：</td>
                <td colspan="3">
                    <input name="remarks" class="mini-textarea" style="width:464px;" />
                </td>
            </tr>
        </table>
    </div>
    <div style="text-align:center;padding:10px;">
        <a class="mini-button" onclick="onOk" style="width:60px;margin-right:20px;">确定</a>
        <a class="mini-button" onclick="onCancel" style="width:60px;">取消</a>
    </div>
</form>
<script type="text/javascript">
    mini.parse();


    var form = new mini.Form("form1");

    function SaveData() {
        saveForm(form, {
            url: "${ctx}/yl/jzxx/saveOrUpdateBatch",
            callback: function (success) {
                if (success) CloseWindow("save");
                else CloseWindow();
            }
        });
    }

    ////////////////////
    //标准方法接口定义
    function SetData(data) {
        if (data.action == "edit") {
            //跨页面传递的数据对象，克隆后才可以安全使用
            data = mini.clone(data);

            $.ajax({
                url: "${ctx}/yl/jzxx/getById?id=" + data.id,
                cache: false,
                success: function (text) {
                    var o = mini.decode(text);
                    form.setData(o);
                    form.setChanged(false);
                }
            });
        }
    }

    function GetData() {
        var o = form.getData();
        return o;
    }
    function CloseWindow(action) {
        if (action == "close" && form.isChanged()) {
            if (confirm("数据被修改了，是否先保存？")) {
                return false;
            }
        }
        if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
        else window.close();
    }
    function onOk(e) {
        SaveData();
    }
    function onCancel(e) {
        CloseWindow("cancel");
    }

    $(function () {
        mini.get("code").setValue(new Date().getTime());
    })
</script>
</body>
</html>
