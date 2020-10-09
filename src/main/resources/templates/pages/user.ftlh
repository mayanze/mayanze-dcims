<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Pagination 分页表格</title>
    <script src="${ctx}/boot.js" type="text/javascript"></script>
</head>
<body >
<h1>Pagination 分页表格</h1>

<div style="padding-bottom:5px;">

    <span>员工姓名：</span><input type="text" id="key"  />
    <input type="button" value="查找" onclick="search()"/>

</div>

<div style="width:100%;">
    <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
        <table style="width:100%;">
            <tr>
                <td style="width:100%;">
                    <a class="mini-button" iconCls="icon-add" onclick="addRow()" plain="true" tooltip="增加...">增加</a>
                    <a class="mini-button" iconCls="icon-remove" onclick="removeRow()" plain="true">删除</a>
                    <span class="separator"></span>
                    <a class="mini-button" iconCls="icon-save" onclick="saveData()" plain="true">保存</a>
                </td>
            </tr>
        </table>
    </div>
</div>

<div id="datagrid1" class="mini-datagrid" style="width:100%;height:250px;"
     url="${ctx}/sys/user/queryPage"
     ajaxType="get"
     idField="id" allowResize="true"
     sizeList="[20,30,50,100]" pageSize="20"
     showHeader="true" title="表格面板"
     allowCellEdit="true" allowCellSelect="true" multiSelect="true"
     data="records">
    <div property="columns">
        <div type="indexcolumn" ></div>
        <div type="checkcolumn"></div>
        <div field="code" width="50" headerAlign="center" allowSort="true">员工帐号
            <input property="editor" class="mini-textbox" style="width:100%;" />
        </div>
        <div field="name" width="50" headerAlign="center" allowSort="true">姓名
            <input property="editor" class="mini-textbox" style="width:100%;" />
        </div>
        <div field="sex" width="50" renderer="onGenderRenderer" headerAlign="center" headerAlign="center">性别
            <input property="editor" class="mini-combobox" style="width:100%;" data="Genders" />
        </div>
        <div field="age"  headerAlign="center" width="50" allowSort="true" decimalPlaces="2" dataType="int">年龄
            <input property="editor" class="mini-spinner"  minValue="0" maxValue="200" value="25" style="width:100%;"/>
        </div>
        <div field="idCard" headerAlign="center" width="100" allowSort="true">身份证号
            <input property="editor" class="mini-textbox" style="width:100%;" />
        </div>
        <div field="address"  headerAlign="center" width="100" allowSort="true">住址
            <input property="editor" class="mini-textbox" style="width:100%;" />
        </div>
        <div field="phoneNo" headerAlign="center" width="100" allowSort="true">电话
            <input property="editor" class="mini-textbox" style="width:100%;" />
        </div>
        <div field="createTime" width="100" headerAlign="center" dateFormat="yyyy-MM-dd HH:mm:ss" allowSort="true">创建日期
            <input class="mini-datepicker" null property="editor" style="width:100%;" nullValue=""
                   format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm:ss" showTime="true" showOkButton="true" showClearButton="false"/>
        </div>
        <div field="name" width="50" headerAlign="center" allowSort="true">备注
            <input property="editor" class="mini-textbox" style="width:100%;" />
        </div>
    </div>
</div>




<script type="text/javascript">

    mini.parse();

    var grid = mini.get("datagrid1");

    grid.load();

    function search() {
        var key = document.getElementById("key").value;
        grid.load({ key: key});
    }
    $("#key").bind("keydown", function (e) {
        if (e.keyCode == 13) {
            search();
        }
    });
    /**
     * 性别
     */
    var Genders = [{ id: 0, text: '男' }, { id: 1, text: '女'}];
    function onGenderRenderer(e) {
        for (var i = 0, l = Genders.length; i < l; i++) {
            var g = Genders[i];
            if (g.id == e.value) return g.text;
        }
        return "";
    }

    function addRow() {
        var newRow = { name: "New Row" };
        grid.addRow(newRow, 0);

        grid.beginEditCell(newRow, "LoginName");
    }
    function removeRow() {
        var rows = grid.getSelecteds();
        if (rows.length > 0) {
            mini.confirm("确定删除记录？", "确定？",
                function (action) {
                    if (action == "ok") {
                        deleteGrid(rows, {
                            url: "${ctx}/sys/user/removeByIds"
                        });
                        grid.removeRows(rows, true);
                    } else {
                        alert("不删除!");
                    }
                }
            );
        }
    }
    function saveData() {
        saveGrid(grid, {
            url: "${ctx}/sys/user/saveOrUpdateBatch"
        });
    }


    grid.on("celleditenter", function (e) {
        var index = grid.indexOf(e.record);
        if (index == grid.getData().length - 1) {
            var row = {};
            grid.addRow(row);
        }
    });

    grid.on("beforeload", function (e) {
        if (grid.getChanges().length > 0) {
            if (confirm("有增删改的数据未保存，是否取消本次操作？")) {
                e.cancel = true;
            }
        }
    });
</script>
</body>
</html>