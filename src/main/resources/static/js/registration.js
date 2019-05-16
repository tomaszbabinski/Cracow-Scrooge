document.addEventListener("DOMContentLoaded", function () {

    var repeated = document.getElementById("repeatedPass");
    repeated.oninput = checkPasswords;

});

function checkPasswords() {

    var repeated = document.getElementById("repeatedPass");
    var pass = document.getElementById("pass");
    var infoCheck = document.getElementById("infoCheck");

    if (pass.value !== repeated.value) {
        infoCheck.innerText = "Password is not matching";
    } else {
        infoCheck.innerText = "Password is matching";
    }

    if(repeated.value === ""){
        infoCheck.innerText = "";
    }
}