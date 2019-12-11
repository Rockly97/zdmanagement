$(function () {

    $("#jqGrid").jqGrid({
        url: '/admin/banner/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 10, key: true, hidden: true},
            {label: '预览图', name: 'img', index: 'img', width: 100,align:"center", formatter: coverImageFormatter},
            {label: '描述', name: 'descrip', index: 'descrip', align:"center",width: 100}
        ],
        height: 700,

        styleUI: 'Bootstrap',
        loadtext: '信息读取中...',
        rownumbers: false,
        rownumWidth: 20,
        autowidth: true,
        multiselect: true,
        jsonReader: {
            root: "data",
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order",
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });

    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });

    function coverImageFormatter(cellvalue) {
        return "<img src='" +'http://106.52.3.235:8888/'+ cellvalue + "' width=\"500px\" alt='coverImage'/>";
    }

    function statusFormatter(cellvalue) {
        if (cellvalue == 0) {
            return "<button type=\"button\" class=\"btn btn-block btn-secondary btn-sm\" style=\"width: 50%;\">草稿</button>";
        }
        else if (cellvalue == 1) {
            return "<button type=\"button\" class=\"btn btn-block btn-success btn-sm\" style=\"width: 50%;\">发布</button>";
        }
    }

});

/**
 * 搜索功能
 */

/**
 * jqGrid重新加载
 */


function addBlog() {
    window.location.href = "/admin/banner/edit";
}

function editBlog() {
    var id = getSelectedRow();
    if (id == null) {
        return;
    }
    window.location.href = "/admin/banner/edit/" + id;
}

function deleteBlog() {
    var ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    swal({
        title: "确认弹框",
        text: "确认要删除数据吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/admin/banner/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.resultCode == 200) {
                            swal("删除成功", {
                                icon: "success",
                            });
                            $("#jqGrid").trigger("reloadGrid");
                        } else {
                            swal(r.message, {
                                icon: "error",
                            });
                        }
                    }
                });
            }
        }
    );
}