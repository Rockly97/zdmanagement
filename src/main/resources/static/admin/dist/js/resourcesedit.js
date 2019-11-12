var blogEditor;

// Tags Input
// $('#blogTags').tagsInput({
//     width: '100%',
//     height: '38px',
//     defaultText: '文章标签'
// });

//Initialize Select2 Elements
$('.select2').select2()

$(function () {
    /**
     * 上传封面
     */
    new AjaxUpload('#uploadCoverImage', {
        action: '/admin/upload/file',
        name: 'file',
        autoSubmit: true,
        responseType: "json",
        onSubmit: function (file, extension) {
            if (!(extension && /^(jpg|jpeg|png|gif)$/.test(extension.toLowerCase()))) {
                alert('只支持jpg、png、gif格式的文件！');
                return false;
            }
        },
        onComplete: function (file, r) {
            console.log(r);
            if (r != null && r.resultCode === 200) {
                $("#blogCoverImage").attr("src", r.data);
                $("#blogCoverImage").attr("style", "max-width: 400px;display:block;");
                return false;
            } else {
                alert("error");
            }
        }
    });
});

/**
 * 校验数据
 */
$('#confirmButton').click(function () {
    var id = $('#id').val();
    var title = $('#title').val();
    var description = $('#description').val();
    var kind = null;
    var url = $('#url').val();
    if (id != 0) {
         kind=$('#kindto').val();
    }else {
         kind = $('#kind').val();
    }
    if (isNull(title)) {
        swal("请输入合作资源名称", {
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
    if (!validLength(url, 150)) {
        swal("网址过长 字数限制在150", {
            icon: "error",
        });
        return;
    }
    if (!validLength(description, 450)) {
        swal("描述字数过多", {
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
    if(!isNull(url)){
        var b = isURL(url);
        if(!b) {
            swal("请输入正确的网址", {
                icon: "error",
            });
            return;
        }
    }


    $('#articleModal').modal('show');
});

/**
 * 保存按钮
 */
$('#saveButton').click(function () {
    var data=null;
    var url = null;
    var id = $('#id').val();
    var title = $('#title').val();
    var description = $('#description').val();
    var lujin = $('#url').val();
    var coverImage = $('#blogCoverImage')[0].src;
    var flag = $("input[name='flag']:checked").val();
    if (isNull(coverImage) || coverImage.indexOf('img-upload') != -1) {
        swal("封面图片不能为空", {
            icon: "error",
        });
        return;
    }

    var swlMessage = '保存成功';
    if (id != 0) {
        var kind=$('#kindto').val();
        url = '/admin/resources/update';
        swlMessage = '修改成功';
        data = {
            "id": id, "title": title, "description": description,
            "kind":kind , "url": lujin, "img": coverImage,
            "flag": flag
        };
    }else {
        url='/admin/resources/save'
        var kind=$('#kind').val();
        data = {
            "id": id, "title": title, "description": description,
            "kind": kind, "url": lujin, "img": coverImage,
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
                $('#articleModal').modal('hide');
                swal({
                    title: swlMessage,
                    type: 'success',
                    showCancelButton: false,
                    confirmButtonColor: '#3085d6',
                    confirmButtonText: '返回合作资源列表',
                    confirmButtonClass: 'btn btn-success',
                    buttonsStyling: false
                }).then(function () {
                    window.location.href = "/admin/resources";
                })
            }
            else {
                $('#articleModal').modal('hide');
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
 * 调转到文章列表
 */
$('#cancelButton').click(function () {
    window.location.href = "/admin/resources";
});


