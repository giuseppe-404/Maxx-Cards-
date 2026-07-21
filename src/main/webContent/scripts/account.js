var nuovo_account = false;
window.addEventListener("load", function(){
	HTMLCollection.prototype.forEach = Array.prototype.forEach;
	
	const form = document.getElementById("account_form");
	const login_button = document.getElementById("login_button");
	const registra_button = document.getElementById("registra_button");
	
    login_button.addEventListener("click", function(event) {
        form.action = login_button.value;
		nuovo_account = false;
    })
    registra_button.addEventListener("click", function(event) {
        form.action = registra_button.value;
		nuovo_account = true;
    })
	
	form.addEventListener("submit", function(e){
		if (!psdValidation()){
			e.preventDefault();
			document.getElementById("pwd").focus();
		}
        else
            if (nuovo_account && !confirmPwdValidation()){
				e.preventDefault();	
				document.getElementById("conf_pwd").focus();
			}
	})
	
	document.getElementById("pwd").addEventListener("change", psdValidation);
})

function psdValidation() {
    var input = document.getElementById("pwd").value;
    var letter = document.getElementById("letter");
    var capital = document.getElementById("capital");
    var number = document.getElementById("number");
    var length = document.getElementById("length");
    var pwd_msg = document.getElementById("pwd_msg");
	var tutto_ok=true;

    var lowerCase = /[a-z]/g;
    if (input.match(lowerCase)) {
        letter.classList.add("hidden");
    } else {
        letter.classList.remove("hidden");
		tutto_ok=false;
    }

    var upperCase = /[A-Z]/g;
    if (input.match(upperCase)) {
        capital.classList.add("hidden");
    } else {
        capital.classList.remove("hidden");
		tutto_ok=false;
    }
    var numbers = /[0-9]/g;
    if (input.match(numbers)) {
        number.classList.add("hidden");
    } else {
        number.classList.remove("hidden");
		tutto_ok=false;
    }
    if (input.length >= 8) {
        length.classList.add("hidden");
    } else {
        length.classList.remove("hidden");
		tutto_ok=false;
    }
	
	if (tutto_ok)
		pwd_msg.classList.add("hidden");
	else
		pwd_msg.classList.remove("hidden");
	
	return tutto_ok;
}

function confirmPwdValidation(){
	var tutto_ok = document.getElementById("pwd").value == document.getElementById("conf_pwd").value;
	if (tutto_ok)
		document.getElementById("conf_msg").classList.add("hidden");
	else
		document.getElementById("conf_msg").classList.remove("hidden");
	return tutto_ok;
}