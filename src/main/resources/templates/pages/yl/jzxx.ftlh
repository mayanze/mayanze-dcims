<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>就诊信息（挂号）</title>
    <script src="${ctx}/boot.js" type="text/javascript"></script>
</head>
<body >

<div style="padding-bottom:5px;">

    <span>就诊人姓名：</span><input type="text" id="key"  />
    <input type="button" value="查找" onclick="search()"/>

</div>

<div style="width:100%;">
    <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
        <table style="width:100%;">
            <tr>
                <td style="width:100%;">
                    <a class="mini-button" iconCls="icon-add" onclick="add()" plain="true" tooltip="增加...">增加</a>
                    <a class="mini-button" iconCls="icon-add" onclick="edit()" plain="true">编辑</a>
                    <a class="mini-button" iconCls="icon-remove" onclick="removeRow()" plain="true">删除</a>
                </td>
            </tr>
        </table>
    </div>
</div>

<div id="datagrid1" class="mini-datagrid" style="width:100%;height:250px;"
     url="${ctx}/yl/jzxx/queryPage"
     ajaxType="get"
     idField="id" allowResize="true"
     sizeList="[20,30,50,100]" pageSize="20"
     showHeader="true" title="表格面板"
     allowCellEdit="true" allowCellSelect="true" multiSelect="true"
     data="records">
    <div property="columns">
        <div type="indexcolumn" ></div>
        <div type="checkcolumn"></div>
        <div field="userName" width="50" headerAlign="center"  align="center" allowSort="true">就诊人姓名
        </div>
        <div field="code" width="50" headerAlign="center" align="center" allowSort="true">就诊号
        </div>
<#--        <div field="time" width="100" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss" allowSort="true">就诊日期-->
<#--        </div>-->
        <div field="createTime" width="100" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss" allowSort="true">创建日期
        </div>
        <div field="remarks" width="50" headerAlign="center" allowSort="true">备注
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

    function add() {
        mini.open({
            targetWindow: window,

            url: "${ctx}/pages/yl/jzxxWindow",
            title: "新增就诊信息", width: 600, height: 400,
            onload: function () {
                var iframe = this.getIFrameEl();
                var data = { action: "new" };
                iframe.contentWindow.SetData(data);
            },
            ondestroy: function (action) {

                grid.reload();
            }
        });
    }

    function edit() {

        var row = grid.getSelected();
        if (row) {
            mini.open({
                url: "${ctx}/pages/yl/jzxxWindow",
                title: "编辑就诊信息", width: 600, height: 400,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = { action: "edit", id: row.id };
                    iframe.contentWindow.SetData(data);

                },
                ondestroy: function (action) {
                    //var iframe = this.getIFrameEl();

                    grid.reload();

                }
            });

        } else {
            alert("请选中一条记录");
        }
    }

    function removeRow() {
        var rows = grid.getSelecteds();
        if (rows.length > 0) {
            mini.confirm("确定删除记录？", "确定？",
                function (action) {
                    if (action == "ok") {
                        deleteGrid(rows, {
                            url: "${ctx}/yl/jzxx/removeByIds"
                        });
                        grid.removeRows(rows, true);
                    } else {
                        alert("不删除!");
                    }
                }
            );
        }
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