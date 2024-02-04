let frm = $("#frm");

function hideAgree(){
    $("#myModal").modal('hide');
}

$(document).ready(function() {
    $("#myModal").modal('show');
});

function fnLogin() {
    location.href = '/login/';
}
function fnSubmit() {
    frm.submit();
}

$(function() {
    $("#password").on("keyup", function(e) {
        if (e.key == "Enter") fnSubmit();
    });

    frm.validate({
        submitHandler: function (form) {
            var password = document.getElementById("password"), confirm_password = document.getElementById("confirm_password");
            if(password.value != confirm_password.value) {
                alert("비밀번호가 일치하지 않습니다.");
            }else{
            form.submit();
            }

        }
    });
});







