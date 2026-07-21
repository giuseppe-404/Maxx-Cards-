window.addEventListener("load",function(event) {
    HTMLCollection.prototype.forEach = Array.prototype.forEach;
	
    themeSetup();
    updateTheme();

    optionsSetup(document.getElementById("options_button"));
	
	var search = document.getElementById("ricerca");
	var personal = document.getElementById("personal_area");
	if (search)
		autocomplete(search, "http://localhost/Max-Cards-/servletCercaProdottoJson", "get");  //[{id, nome, qnt, prezzo, descrizione, sconto, lingua, idSet, quality, idCarta, deck:[{nome:"", id:"", qnt:""}]]
	if (search && personal){
		search.addEventListener("focus", function(){
			if (window.matchMedia('screen and (min-width:481px)').matches){
				personal.style.maxWidth="0";
				personal.style.minWidth="0";
			}
		})
		search.addEventListener("blur", function(){
			personal.style.maxWidth="";
			personal.style.minWidth="";
		})
	}
})

function ajax(url, method, params, func, abortT = 15000){
    const request = creaAjaxRequest();
    if (!request)
        return request;
    request.onreadystatechange = function(){func(this)};

    setTimeout(function(){
        if (request.readyState < 4){
            request.abort();
        }
    }, abortT);

    if (method.toLowerCase() == "get"){
        if (params){
            request.open("GET", url + "?" + params, true);
        } else {
            request.open("GET", url, true);
        }
        request.send(null);
    } else {
        request.open("POST", url, true)
        if (params){
            request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            request.send(params)
        } else{
            request.send(null);
        }
    }
    return request;
}

function creaAjaxRequest(){
    var request;
    try{
        request = new XMLHttpRequest();
    } catch (e){
        try{
            request = new ActiveXObject("Msxml2.XMLHTTP");
        } catch(e){
            try{
                request = new ActiveXObject("Microsoft.XMLHTTP");
            } catch(e){
                return null;
            }
        }
    }
    return request;
}

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

var uploaded_image_url;
function imageUploadSetup(){
    var input = document.getElementById("image_upload");
    if (!input)
        return
    input.addEventListener("change", function(event){
        if(uploaded_image_url)
            URL.revokeObjectURL(uploaded_image_url);
        var file = event.target.files[0];
        var immagine = document.getElementById("image_preview");
        if(!file){
            immagine.classList.add("hidden");
            return
        }
        uploaded_image_url=URL.createObjectURL(file);
        immagine.classList.remove("hidden");
        immagine.src=uploaded_image_url;
    })
}

function fieldsetsResetSetup(fieldsets){
    fieldsets.forEach(function(fieldset){
        fieldset.saveState = function(){
            fieldset.state ={inputs:[], areas:[], selects:[]};
            fieldset.getElementsByTagName("input").forEach(function(input){
                if (input.type.toLowerCase()=="checkbox")
                    fieldset.state.inputs.push(input.checked);
                else
                    fieldset.state.inputs.push(input.value);
            })
            fieldset.getElementsByTagName("textarea").forEach(function(area){
                fieldset.state.areas.push(area.value);
            })
            fieldset.getElementsByTagName("select").forEach(function(select){
                fieldset.state.selects.push(select.selectedIndex);
            })
        }
        fieldset.loadState = function(){
            const inputs = fieldset.getElementsByTagName("input");
            for(var i = 0; i<inputs.length; i++){
                if (inputs[i].type.toLowerCase()=="checkbox"){
                    if (inputs[i].checked != fieldset.state.inputs[i]){
                        inputs[i].checked = fieldset.state.inputs[i];
                        inputs[i].dispatchEvent(new Event('change'));
                    }
                }
                else{
                    if (inputs[i].value != fieldset.state.inputs[i]){
                        inputs[i].value = fieldset.state.inputs[i];
                        inputs[i].dispatchEvent(new Event('change'));
                    }
                }
            }

            const areas = fieldset.getElementsByTagName("textarea");
            for(var i = 0; i<areas.length; i++){
                if (areas[i].value != fieldset.state.areas[i]){
                    areas[i].value = fieldset.state.areas[i];
                    areas[i].dispatchEvent(new Event('change'));
                }
            }

            const selects = fieldset.getElementsByTagName("select");
            for(var i = 0; i<selects.length; i++){
                if (selects[i].selectedIndex != fieldset.state.selects[i]){
                    selects[i].selectedIndex = fieldset.state.selects[i];
                    selects[i].dispatchEvent(new Event('change'));
                }
            }
        }
        fieldset.resetToDefault = function(){
            const inputs = fieldset.getElementsByTagName("input");
            inputs.forEach(function(input){
                if (input.value != input.defaultValue){
                    input.value = input.defaultValue;
                    input.dispatchEvent(new Event('change'));
                }
                else if (input.checked){
                    input.checked = false;
                    input.dispatchEvent(new Event('change'));
                }
            })
            const areas = fieldset.getElementsByTagName("textarea");
            areas.forEach(function(area){
                if (area.value != area.defaultValue){
                    area.value = area.defaultValue;
                    area.dispatchEvent(new Event('change'));
                }
            })
            const selects = fieldset.getElementsByTagName("select");
            selects.forEach(function(select){
                if (select.selectedIndex != 0){
                    select.selectedIndex = 0;
                    select.dispatchEvent(new Event('change'));
                }
            })
        }
    })
    
}

var timer;
function autocomplete(inp, link, method="get", params=null, override_func=false, func=null){
    var current_focus = -1;  //elemento nella lista attualmente selezionato
	
    if(!override_func)
        func=defaultFunc;
    inp.addEventListener("input", func);

    //cambia elemento selezionato nella lista premendo freccia su/giù
    inp.addEventListener("keydown", function(e) {
        var x = document.getElementById(this.id + "autocomplete-list");
        if (x) 
            x = x.getElementsByTagName("div");
        
        if (e.keyCode == 40) { //freccia giù -> seleziona l'elemento in basso
            current_focus++;
            addActive(x);
        }  else if (e.keyCode == 38) { //freccia su -> seleziona l'elemento in alto
            current_focus--;
            addActive(x);
        } else if (e.keyCode == 13) { //enter -> previene l'azione di default (submit del form) e simula il click sull'elemento selezionato
            e.preventDefault();
            if (current_focus > -1) {
                if (x) 
                    x[current_focus].click();
            }
        }
    });

    //seleziona l'elemento scelto nella lista
    function addActive(elems) {
        if (!elems) 
            return false;
        
        removeActive(elems);

        if (current_focus >= elems.length) 
            current_focus = 0;
        if (current_focus < 0) 
            current_focus = (elems.length - 1);
        
        elems[current_focus].classList.add("autocomplete-active");
    }

    //deseleziona tutti gli elementi della lista
    function removeActive(elems) {
        elems.forEach(function(elem){
            elem.classList.remove("autocomplete-active")
        })
    }

    //chiude tutte le liste con autocompletamento nel documento, tranne l'argomento
    function defaultFunc(request){
        closeAllLists();

        if (timer)
            window.clearTimeout(timer);

        timer = window.setTimeout(function(){
            var val = inp.value;
            if (!val) 
                return false;
            current_focus = -1;

            var list_div = document.createElement("div");
            list_div.setAttribute("id", inp.id + "autocomplete-list");
            list_div.setAttribute("class", "autocomplete-items");

            inp.parentNode.appendChild(list_div);

            ajax(link, method, params, function(request){
                if (request.readyState<4 || request.status != 200)
                    return;
                var arr = JSON.parse(request.responseText).array;
                const rg = new RegExp(val, "i");
                arr.forEach(function(item){
                    var index = item.search(rg);
                    if (index >= 0){
                        var list_elem = document.createElement("div");
                        list_elem.innerHTML = item.substr(0, index);
                        list_elem.innerHTML += "<strong>" + item.substr(index, val.length) + "</strong>";
                        list_elem.innerHTML += item.substr(index+val.length);
                        list_elem.innerHTML += "<input type='hidden' value='" + item + "'>";
                        list_elem.addEventListener("click", function(e) {
                            inp.value = this.getElementsByTagName("input")[0].value;
                            closeAllLists();
                        });
                        list_div.appendChild(list_elem);
                    }
                })
            })
        }.bind(this)
        , 1000);
    }

    //chiude tutte le liste al click di qualcosa sullo schermo (tranne l'eventuali lista cliccata)
    //una funzione con nome non viene aggiunta più volte con l'addEventListener (quindi closeAllTargeted non si duplica)
    document.addEventListener("click", closeAllTargeted);
    document.getElementsByTagName("input").forEach(function(input){
        input.addEventListener("focus", closeAllTargeted);
    })

    function closeAllTargeted(event){
		current_focus = -1;
        closeAllLists(event.target, inp);
    }
}

function closeAllLists(elem) {
    var liste = document.getElementsByClassName("autocomplete-items");
    liste.forEach(function(lista){
    if (elem != lista)
        lista.parentNode.removeChild(lista)
    })
}