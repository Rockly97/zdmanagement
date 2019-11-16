$(function () {

    $('#confirmButton').click(function () {
        var userName = $('#loginUserName').val();
        var nickName = $('#nickName').val();
        var email = $('#email').val();
        var phone = $('#phone').val();
        var password = $('#password').val();
        // var formData  = new FormData();
        // formData.append("userName",userName);
        // formData.append("nickName",nickName);
        // formData.append("email",email);
        // formData.append("phone",phone);
        // formData.append("password",password);
        if (validUserNameForUpdate(userName, nickName,email,phone,password)) {
            //ajax提交数据
            var params = $("#userNameForm").serialize();
            $.ajax({
                type: "POST",
                url: "/admin/user/add",
                data: params,
                success: function (r){
                    console.log(r);
                    if (r == 'success') {
                        swal({
                            title: '添加成功',
                            type: 'success',
                            showCancelButton: false,
                            confirmButtonColor: '#3085d6',
                            confirmButtonText: '返回用户列表',
                            confirmButtonClass: 'btn btn-success',
                            buttonsStyling: false
                        }).then(function () {
                            window.location.href = "/admin/user";
                        })
                        //  alert('添加成功');

                    } else {
                        swal(r, {
                            icon: "error",
                        });
                        // alert('添加失败');

                    }
                },
                error:function () {
                    alert('错误')

                }
            });

        }

    });


    $('#cancelButton').click(function () {
        window.location.href = "/admin/user";
    });
})

/**
 * 名称验证
 */
function validUserNameForUpdate(userName, nickName,email,phone,password) {
    if (isNull(userName) || userName.trim().length < 1) {
        $('#updateUserName-info').css("display", "block");
        $('#updateUserName-info').html("请输入登陆名称！");
        return false;
    }
    if (isNull(nickName) || nickName.trim().length < 1) {
        $('#updateUserName-info').css("display", "block");
        $('#updateUserName-info').html("昵称不能为空！");
        return false;
    }
    if (!validUserName(userName)) {
        $('#updateUserName-info').css("display", "block");
        $('#updateUserName-info').html("请输入符合规范的登录名！ 4到--16位(字母，数字，下划线，减号)");
        return false;
    }
    if (!validCN_ENString2_18(nickName)) {
        $('#updateUserName-info').css("display", "block");
        $('#updateUserName-info').html("请输入符合规范的昵称！");
        return false;
    }
    if (isNull(email) || email.trim().length < 1) {
        $('#updateUserName-info').css("display", "block");
        $('#updateUserName-info').html("请输入邮箱！");
        return false;
    }
    if (isNull(phone) || phone.trim().length < 1) {
        $('#updateUserName-info').css("display", "block");
        $('#updateUserName-info').html("请输入电话号码！");
        return false;
    }
    if (isNull(password) || password.trim().length < 1) {
        $('#updateUserName-info').css("display", "block");
        $('#updateUserName-info').html("请输入密码！");
        return false;
    }
    return true;
}

/**
 * 密码验证
 */
function validPasswordForUpdate(originalPassword, newPassword) {
    if (isNull(originalPassword) || originalPassword.trim().length < 1) {
        $('#updatePassword-info').css("display", "block");
        $('#updatePassword-info').html("请输入原密码！");
        return false;
    }
    if (isNull(newPassword) || newPassword.trim().length < 1) {
        $('#updatePassword-info').css("display", "block");
        $('#updatePassword-info').html("新密码不能为空！");
        return false;
    }
    if (!validPassword(newPassword)) {
        $('#updatePassword-info').css("display", "block");
        $('#updatePassword-info').html("请输入符合规范的密码！");
        return false;
    }
    return true;
}
