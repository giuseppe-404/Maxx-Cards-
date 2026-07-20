window.addEventListener("load", function() {
    HTMLCollection.prototype.forEach = Array.prototype.forEach;

    if (imageUploadSetup)
        imageUploadSetup()

    if (fieldsetsResetSetup) {
        var fieldsets = document.getElementsByTagName("fieldset");
        fieldsetsResetSetup(fieldsets);
        fieldsets.forEach(function(fieldset) {
            fieldset.saveState();
            var button = fieldset.getElementsByClassName("reset_button")[0];
            if (button)
                button.addEventListener("click", function() {
                    fieldset.loadState()
                })
        });
    }

    if (autocomplete) {
        var cardSearch = document.getElementById("ricerca_carta");
        var autArgs = [cardSearch, "http://localhost/Max-Cards-/servletCercaCartaDeckJson", "get", null];
        autocomplete(...autArgs, true, ricercaCartaAutoc.bind(null, ...autArgs));
        document.getElementById("carica_carta").addEventListener("click", function() {
            ajax("http://localhost/Max-Cards-/JsonTest", "get", "nome=" + cardSearch.value, function(request) {
                if (request.readyState < 4)
                    return;
				var error_p = document.getElementById("card_retrieve_error");
				if (request.status == 200){
					var carta = JSON.parse(request.responseText);
					if (carta){
						document.getElementById("action").value = "alter";
						loadCardData(carta);
						error_p.classList.add("hidden")
						return;
					}
				}
				error_p.classList.remove("hidden");
            })
        })
		
		document.getElementById("action").addEventListener("change", function(event){
			var old_id = document.getElementById("old_id");
            if (event.target.value == "add")
                fieldsets.forEach(function(fieldset){
					fieldset.resetToDefault();
					fieldset.saveState();
					old_id.required=false;
				})
			else
				old_id.required=true;
        })
		
		var loader_reset = document.getElementById("product_loader").getElementsByClassName("reset_button")[0];
		if (loader_reset)
			loader_reset.addEventListener("click", document.getElementById("card_retrieve_error").classList.add("hidden"));
    }

    document.getElementById("classe_carta").addEventListener("change", updateCardClasss);

    document.getElementById("tipologia_carta").addEventListener("change", updateMonsterTypology);

    var boxes = document.getElementById("frecce_label").getElementsByTagName("input");
    boxes.forEach(function(box) {
        box.addEventListener("change", function(event) {
            var sum = 0;
            boxes.forEach(function(check) {
                if (check.checked)
                    sum += 1;
            })
            document.getElementById("livello_mostro").value = sum;
        })
    });

    document.getElementById("tipo_mostro").addEventListener("change", function(event) {
        var new_type = document.getElementById("nuovo_tipo");
        if (event.target.value = "nuovo") {
            new_type.parentNode.classList.remove("invisible");
            new_type.required = true;
        }
        else {
            new_type.parentNode.classList.add("invisible");
            new_type.required = false;
        }
    })

    document.getElementById("difesa_mostro").addEventListener("change", function(event) {
        if (event.target.value == "" && event.target.readOnly)
            document.getElementById("tipologia_carta").dispatchEvent(new Event("change"));
    })

    document.getElementById("cancella_carta").addEventListener("click", function() {
        document.getElementById("action").value = "delete";
        document.getElementById("card_form").submit();
    })
	
})

var lastClass = "";
function updateCardClasss(event) {
    var tipologia_select = document.getElementById("tipologia_carta");
    var tipology_fieldset = document.getElementById("card_tipology");
    var monster_fieldset = document.getElementById("monster_data");
    var frecce_fieldset = document.getElementById("arrow_data");
    var type_fieldset = document.getElementById("monster_type");
    var figli = [tipologia_select.firstElementChild];
    var valori = [];
    var value = event.target.value.toLocaleLowerCase();
    switch (value) {
        case "mostro":
            valori = ["Normale", "Fusione", "Synchro", "XYZ", "Rituale", "Link"];
            monster_fieldset.classList.remove("hidden");
            type_fieldset.classList.remove("hidden");
            tipology_fieldset.classList.remove("hidden");
            setMonsterRequired(true);
            break;
        case "magia":
            valori = ["Normale", "Rapida", "Continua", "Equipaggiamento", "Rituale", "Terreno"];
            setMonsterRequired(false);
            monster_fieldset.classList.add("hidden");
            frecce_fieldset.classList.add("hidden");
            type_fieldset.classList.add("hidden");
            tipology_fieldset.classList.remove("hidden");
            break;
        case "trappola":
            valori = ["Normale", "Contro", "Continua"];
            setMonsterRequired(false);
            monster_fieldset.classList.add("hidden");
            frecce_fieldset.classList.add("hidden");
            type_fieldset.classList.add("hidden");
            tipology_fieldset.classList.remove("hidden");
            break;
        default:
            monster_fieldset.classList.add("hidden");
            frecce_fieldset.classList.add("hidden");
			type_fieldset.classList.add("hidden");
            tipology_fieldset.saveState();
            tipology_fieldset.classList.add("hidden");
            return;
    }
    valori.forEach(function(string) {
        var option = document.createElement("option");
        option.value = string;
        option.innerHTML = string;
        figli.push(option);
    })
    tipologia_select.replaceChildren(...figli);
    if (value == lastClass)
        tipology_fieldset.loadState();
    else {
        lastClass = value;
        tipologia_select.selectedIndex = 0;
    }
}

function updateMonsterTypology(event) {
    var frecce_fieldset = document.getElementById("arrow_data");
    var livello = document.getElementById("livello_mostro");
    var difesa = document.getElementById("difesa_mostro");
    switch (event.target.value.toLocaleLowerCase()) {
        case "link":
            frecce_fieldset.classList.remove("hidden");
            livello.readOnly = true;
            difesa.readOnly = true;
            document.getElementById("freccia1").dispatchEvent(new Event("change"));
            difesa.value = 0;
            break;
        default:
            frecce_fieldset.classList.add("hidden");
            livello.readOnly = false;
            difesa.readOnly = false;
            return;
    }
}

function setMonsterRequired(bool = true) {
    document.getElementById("livello_mostro").required = bool;
    document.getElementById("categoria_mostro").required = bool;
    document.getElementById("attributo_mostro").required = bool;
    document.getElementById("attacco_mostro").required = bool;
    document.getElementById("difesa_mostro").required = bool;
    document.getElementById("tipo_mostro").required = bool;
}

function ricercaCartaAutoc(inp, link, method) {
    closeAllLists();

    if (timer)
        window.clearTimeout(timer);

    timer = window.setTimeout(function() {
        var val = inp.value;
        if (!val)
            return false;

        var list_div = document.createElement("div");
        list_div.setAttribute("id", inp.id + "autocomplete-list");
        list_div.setAttribute("class", "autocomplete-items");

        inp.parentNode.appendChild(list_div);

        var params = "nome=" + inp.val;

        ajax(link, method, params, function(request) {
            if (request.readyState < 4)
                return;
            var arr = JSON.parse(request.responseText).array;
            console.log(arr);
            const rg = new RegExp(val, "i");
            arr.forEach(function(item) {
                var index = item.search(rg);
                if (index >= 0) {
                    var list_elem = document.createElement("div");
                    list_elem.innerHTML = item.substr(0, index);
                    list_elem.innerHTML += "<strong>" + item.substr(index, val.length) + "</strong>";
                    list_elem.innerHTML += item.substr(index + val.length);
                    list_elem.innerHTML += "<input type='hidden' value='" + item + "'>";
                    list_elem.addEventListener("click", function(e) {
                        inp.value = this.getElementsByTagName("input")[0].value;
                        closeAllLists();
                    });
                    list_div.appendChild(list_elem);
                }
            })
        })
    }, 1000);
}

function loadCardData(carta) {
	document.getElementById("old_id").value = carta.id;
	
	var classe_select = document.getElementById("classe_carta");
	var isMostro = false;
	switch (carta.classe.toLocaleLowerCase()){
		case "mostro":
			classe_select.selectedIndex = 1;
			isMostro = true;
			break;
        case "magia":
            classe_select.selectedIndex = 2;
            break;
        case "trappola":
            classe_select.selectedIndex = 3;
            break;
		default:
			classe_select.selectedIndex = 0;
	}
	classe_select.dispatchEvent(new Event("change"));
	document.getElementById("id_carta").value = carta.id;
	document.getElementById("nome_it").value = carta.nomeIt;
	document.getElementById("nome_en").value = carta.nomeEn;
	document.getElementById("nome_jp").value = carta.nomeJp;
	document.getElementById("pnt_carta").value = carta.punteggio;
	document.getElementById("testo_carta").value = carta.testo;
	document.getElementById("card_data").saveState();
	
	document.getElementById("card_image").resetToDefault();
	document.getElementById("image_preview").src = "image?action=show&prodottoId="+carta.id+"&isProdotto=false";
	
	var tipologia_select = document.getElementById("tipologia_carta");
	tipologia_select.getElementsByTagName("option").forEach(function(option){
		if (option.value.toLowerCase() == carta.tipologia.toLowerCase())
			tipologia_select.value = option.value;
	})
    tipologia_select.dispatchEvent(new Event("change"));
	document.getElementById("card_tipology").saveState();
	
	if (!isMostro)
		return;
	
	document.getElementById("livello_mostro").value = carta.livello;
    var categoria_select = document.getElementById("categoria_mostro");
    categoria_select.getElementsByTagName("option").forEach(function(option) {
        if (option.value.toLowerCase() == carta.categoria.toLowerCase())
            categoria_select.value = option.value;
    })
    var attributo_select = document.getElementById("attributo_mostro");
    attributo_select.getElementsByTagName("option").forEach(function(option) {
        if (option.value.toLowerCase() == carta.attributo.toLowerCase())
            attributo_select.value = option.value;
    })
	document.getElementById("attacco_mostro").value = carta.attacco;
	document.getElementById("difesa_mostro").value = carta.difesa;
	document.getElementById("tuner_mostro").checked = carta.tuner>0;
	document.getElementById("scala_mostro").value = carta.scalaPendulum;
	document.getElementById("monster_data").saveState();

	
	document.getElementById("arrow_data").resetToDefault();
	var frecceLink = carta.frecceLink;
    if (frecceLink)
        frecceLink.forEach(function(number) {
            document.getElementById("freccia" + number).checked = true;
        })
	document.getElementById("freccia1").dispatchEvent(new Event("change"));	
	document.getElementById("arrow_data").saveState();
	
    var tipo_select = document.getElementById("tipo_mostro");
    tipo_select.getElementsByTagName("option").forEach(function(option) {
        if (option.value.toLowerCase() == carta.tipo.toLowerCase())
            tipo_select.value = option.value;
    })
	document.getElementById("monster_type").saveState();
}