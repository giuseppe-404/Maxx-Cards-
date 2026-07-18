window.addEventListener("load",function(event) {
    HTMLCollection.prototype.forEach = Array.prototype.forEach;
	
    themeSetup();
    updateTheme();

    optionsSetup(document.getElementById("options_button"))
})

function themeSetup(){
    var theme = updateTheme();
    const toggle = document.getElementById("theme_toggle");
    if (!toggle)
        return;
    toggle.addEventListener("change", changeTheme, false);
    toggle.checked = (theme == "dark");
}

function updateTheme(){
    var theme="dark";

    if(localStorage.getItem("theme")){
        if(localStorage.getItem("theme") == "light"){
            var theme = "light";
        }
    }
    else if(window.matchMedia){  //controlla se il metodo è supportato
        if(window.matchMedia("(prefers-color-scheme: light)").matches){
            var theme = "light";
        }
    }

    if(theme)
        document.documentElement.setAttribute("theme", theme);

    return theme;
}

function changeTheme(event) {
    if (event.currentTarget.checked){
        localStorage.setItem('theme', 'dark');
        document.documentElement.setAttribute("theme", "dark");
    }
    else{
        localStorage.setItem('theme', 'light');
        document.documentElement.setAttribute("theme", "light");
    }
    updateTheme();
}

function optionsSetup(button){
    var options_div = document.getElementById("options");
    if (!options_div || !button)
        return;
    button.addEventListener("click", switchShowOptions);

    function switchShowOptions(){
        if(options_div.classList.contains("hidden"))
            options_div.classList.remove("hidden");
        else
            options_div.classList.add("hidden");
    }
}

