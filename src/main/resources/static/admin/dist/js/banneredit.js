var blogEditor;
// Tags Input


//Initialize Select2 Elements
$('.select2').select2()

$(function () {
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
                $("#bannerCoverImage").attr("src", r.data);
                $("#bannerCoverImage").attr("style", "max-width: 800px;display:block;");
                return false;
            } else {
                alert("error");
            }
        }
    });
});

$('#saveButton').click(function () {
    var bannerId = $('#bannerId').val();
    var bannerDescrip = $('#bannerDescrip').val();
    var bannerCoverImage = $('#bannerCoverImage')[0].src;
    if (isNull(bannerDescrip)) {
        swal("请输入轮播图描述", {
            icon: "error",
        });
        return;
    }
    if (!validLength(bannerDescrip, 150)) {
        swal("描述过长", {
            icon: "error",
        });
        return;
    }

    if (isNull(bannerCoverImage) || bannerCoverImage.indexOf('img-upload') != -1) {
        swal("封面图片不能为空", {
            icon: "error",
        });
        return;
    }
    var url = '/admin/banner/save';
    var swlMessage = '保存成功';
    var data = {
        "id": bannerId, "descrip": bannerDescrip, "img": bannerCoverImage
    };
    if (bannerId != 0) {
        url = '/admin/banner/update';
        swlMessage = '修改成功';
        data = {
            "id": bannerId,
            "descrip": bannerDescrip,
            "img": bannerCoverImage
        };
    }
    console.log(data);
    $.ajax({
        type: 'POST',//方法类型
        url: url,
        data: data,
        success: function (result) {
            if (result.resultCode == 200) {
                $('#articleModal').modal('hide');
                swal({
                    title: swlMessage,
                    type: 'success',
                    showCancelButton: false,
                    confirmButtonColor: '#3085d6',
                    confirmButtonText: '返回轮播图列表',
                    confirmButtonClass: 'btn btn-success',
                    buttonsStyling: false
                }).then(function () {
                    window.location.href = "/admin/banner";
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

$('#cancelButton').click(function () {
        window.location.href = "/admin/banner/deleteImg";

});

