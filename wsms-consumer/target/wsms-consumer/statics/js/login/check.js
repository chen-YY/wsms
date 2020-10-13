function check() {
    var account = document.forms["myForm"]["account"];
    var password = document.forms["myForm"]["password"];
    var accountType = document.forms["myForm"]["accountType"];
    var myForm = document.forms["myForm"]

    if(account.value == "" || account.value == null){
        alert("必须输入账户");
        return false;
    }else if(password.value == "" || password.value == null){
        alert("请输入密码");
        return false;
        account.value() == "" || account.value == null
    }else if(accountType.value == "" || accountType.value == null){
        alert("请选择您的账户类型");
        return false;
    }
}