$(function () {
    var ue = UE.getEditor('editor');
    $('.select2').select2();
    console.log($("#content").text());
    ue.ready(function () {

        ue.setContent($("#content").val());
    });



    /**
     * 校验数据
     */
    $('#confirmButton').click(function () {
        var title = $('#newsTitle').val();
        var author = $('#newsAuthor').val();
        var content = ue.getContent();
        console.log(content);
        if (isNull(title)) {
            swal("请输入文章标题", {
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
        if (!validLength(description, 150)) {
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
        if (isNull(author)) {
            swal("请输入作者", {
                icon: "error",
            });
            return;
        }
        if (isNull(description)) {
            swal("请输入文章描述", {
                icon: "error",
            });
            return;
        }
        if (!validLength(description, 400)) {
            swal("文章描述过长", {
                icon: "error",
            });
            return;
        }
        if (isNull(content)) {
            swal("请输入文章内容", {
                icon: "error",
            });
            return;
        }
        if (!validLength(content, 100000)) {
            swal("文章内容过长", {
                icon: "error",
            });
            return;
        }
        $('#articleModal').modal('show');
    });
    /**
     * 保存按钮
     */
    $('#saveButton').click(function () {
        var id = $('#id').val();
        var title = $('#newsTitle').val();
        var author = $('#newsAuthor').val();
        var content = ue.getContent();
        var flag = $("input[name='flag']:checked").val();

        var url = '/admin/news/save';
        var swlMessage = '保存成功';
        var data = {
            "title": title,
            "author": author,
            "content": content,
            "flag": flag
        };
        if (id > 0) {
            url = '/admin/news/update';
            swlMessage = '修改成功';
            data = {
                "id": id,
                "title": title,
                "author": author,
                "content": content,
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
                        confirmButtonText: '返回博客列表',
                        confirmButtonClass: 'btn btn-success',
                        buttonsStyling: false
                    }).then(function () {
                        window.location.href = "/admin/germanynews";
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
     * 调转到新闻列表
     */
    $('#cancelButton').click(function () {
        window.location.href = "/admin/germanynews";
    });

});




