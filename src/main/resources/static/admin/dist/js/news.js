$(function () {


    $("#jqGrid").jqGrid({
        url: '/admin/news/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 50, key: true, hidden: true},
            {label: '标题', name: 'title', index: 'title', width: 80},
            {label: '封面图', name: 'firstPicture', index: 'firstPicture', width: 100, formatter: coverImageFormatter},
            {label: '文章描述', name: 'description', index: 'description', width: 40},
            {label: '发布状态', name: 'flag', index: 'flag', width: 40, formatter: statusFormatter},
            {label: '新闻分类', name: 'kind', index: 'kind', width: 40},
            {label: '置顶状态', name: 'level', index: 'level', width: 40, formatter: levelstatusFormatter},
            {label: '新闻作者', name: 'author', index: 'author', width: 40},
            {label: '创建时间', name: 'createTime', index: 'createTime', width: 80}
        ],
        height: 700,
        rowNum: 10,
        rowList: [10, 20, 50],
        styleUI: 'Bootstrap',
        loadtext: '信息读取中...',
        rownumbers: false,
        rownumWidth: 20,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "data.list",
            page: "data.currPage",
            total: "data.totalPage",
            records: "data.totalCount"
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
        return "<img src='" + cellvalue + "' height=\"120\" width=\"160\" alt='coverImage'/>";
    }

    function statusFormatter(cellvalue) {
        if (cellvalue === 0) {
            return "<button type=\"button\" class=\"btn btn-block btn-secondary btn-sm\" style=\"width: 50%;\">草稿</button>";
        }
        else if (cellvalue === 1) {
            return "<button type=\"button\" class=\"btn btn-block btn-success btn-sm\" style=\"width: 50%;\">发布</button>";
        }
    }

    function levelstatusFormatter(cellvalue) {
        switch (cellvalue){
            case 1:
                return "<button type=\"button\" class=\"btn btn-block btn-primary btn-sm\" style=\"width: 50%;\">大图显示</button>";
                break;
            case 2:
                return "<button type=\"button\" class=\"btn btn-block btn-success btn-sm\" style=\"width: 50%;\">小图显示</button>";
                break;
            default:
                return "<button type=\"button\" class=\"btn btn-block btn-secondary btn-sm\" style=\"width: 50%;\">无置顶</button>";
                break;
        }
    }

});

/**
 * 搜索功能
 */
function search() {
    //标题关键字
    var keyword = $('#keyword').val();
    // if (!validLength(keyword, 20)) {
    //     swal("搜索字段长度过大!", {
    //         icon: "error",
    //     });
    //     return false;
    // }
    //数据封装
    var searchData = {keyword: keyword};
    //传入查询条件参数
    $("#jqGrid").jqGrid("setGridParam", {postData: searchData});
    //点击搜索按钮默认都从第一页开始
    $("#jqGrid").jqGrid("setGridParam", {page: 1});
    //提交post并刷新表格
    $("#jqGrid").jqGrid("setGridParam", {url: '/admin/news/list'}).trigger("reloadGrid");
}

/**
 * jqGrid重新加载
 */
function reload() {
    var page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        page: page
    }).trigger("reloadGrid");
}

function addBlog() {
    window.location.href = "/admin/news/edit";
}

function editBlog() {
    var id = getSelectedRow();
    if (id == null) {
        return;
    }
    window.location.href = "/admin/news/edit/" + id;
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
                    url: "/admin/news/delete",
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