var blogEditor;

// Tags Input
// $('#blogTags').tagsInput({
//     width: '100%',
//     height: '38px',
//     defaultText: '文章标签'
// });

//Initialize Select2 Elements
$('.select2').select2()

/**
 * 校验数据
 */
$('#confirmButton').click(function () {
    var data=null;
    var url = null;
    var flag = $("input[name='flag']:checked").val();
    var id = $('#id').val();
    var title = $('#title').val();
    var content = $('#content').val();
    var kind = null;
    var addr = $('#addr').val();
    var remark = $('#remark').val();

    if (id != 0) {
         kind=$('#kindto').val();
    }else {
         kind = $('#kind').val();
    }
    if (isNull(title)) {
        swal("请输入活动计划合作资源名称", {
            icon: "error",
        });
        return;
    }
    if (!validLength(title, 150)) {
        swal("标题过长 字数限制在150", {
            icon: "error",
        });
        return;
    }
    if (!validLength(content, 450)) {
        swal("内容过长 字数限制在450", {
            icon: "error",
        });
        return;
    }
    if (!validLength(remark, 450)) {
        swal("备注过长 字数限制在450", {
            icon: "error",
        });
        return;
    }
    if (!validLength(addr, 150)) {
        swal("地址字数过多，字数限制在150", {
            icon: "error",
        });
        return;
    }
    if (isNull(kind)) {
        swal("请选择文章分类", {
            icon: "error",
        });
        return;
    }


    var swlMessage = '保存成功';
    if (id != 0) {
        var kind=$('#kindto').val();
        url = '/admin/activity/update';
        swlMessage = '修改成功';
        data = {
            "id": id, "title": title, "content": content,
            "kind":kind , "addr": addr, "remark": remark,
            "flag": flag
        };
    }else {
        url='/admin/activity/save'
        var kind=$('#kind').val();
        data = {
            "id": id, "title": title, "content": content,
            "kind":kind , "addr": addr, "remark": remark,
            "flag": flag
        };
    }


    console.log(data);

    $.ajax({
        type: 'POST',//方法类型
        url: url,
        data: data,
        success: function (result) {
            if (result.resultCode === 200) {
                swal({
                    title: swlMessage,
                    type: 'success',
                    showCancelButton: false,
                    confirmButtonColor: '#3085d6',
                    confirmButtonText: '返回合活动计划列表',
                    confirmButtonClass: 'btn btn-success',
                    buttonsStyling: false
                }).then(function () {
                    window.location.href = "/admin/activity";
                })
            }
            else {
                swal(result.message, {
                    icon: "error",
                });
            }
            ;
        },
        error: function () {
            swal("操作失败", {
                icon: "error",
            });
        }
    });


});

/**
 * 保存按钮
 */


/**
 * 调转到文章列表
 */
$('#cancelButton').click(function () {
    window.location.href = "/admin/activity";
});


