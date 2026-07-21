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
	
	document.getElementsByClassName("carte_contenute").forEach(function(div){
        setCartaEvents(div);
    })
	
    if (autocomplete) {
        var prodSearch = document.getElementById("ricerca_prodotto");
        var autArgs = [prodSearch, "http://localhost/Max-Cards-/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "get", null];
        autocomplete(...autArgs, true, ricercaAutoc.bind(null, ...autArgs));
        document.getElementById("carica_prodotto").addEventListener("click", function() {
            ajax("http://localhost/Max-Cards-/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "get", "nome=" + prodSearch.value, function(request) {
                if (request.readyState < 4)
                    return;
                var error_p = document.getElementById("prod_retrieve_error");
                if (request.status == 200) {
                    var prod = JSON.parse(request.responseText);
                    if (prod) {
                        document.getElementById("action").value = "alter";
                        loadProdData(prod);
                        error_p.classList.add("hidden")
                        return;
                    }
                }
                error_p.classList.remove("hidden");
            })
        })

        document.getElementById("action").addEventListener("change", function(event) {
            var old_id = document.getElementById("old_id");
            if (event.target.value == "add")
                fieldsets.forEach(function(fieldset) {
                    fieldset.resetToDefault();
                    fieldset.saveState();
                    old_id.required = false;
                })
            else
                old_id.required = true;
        })

        var loader_reset = document.getElementById("product_loader").getElementsByClassName("reset_button")[0];
        if (loader_reset)
            loader_reset.addEventListener("click", document.getElementById("prod_retrieve_error").classList.add("hidden"));
    }

	document.getElementById("tipo_prodotto").addEventListener("change", updateTipoProd)
})

function updateTipoProd(event){
	var quality_fieldset = document.getElementById("product_quality");
	var set_fieldset = document.getElementById("product_set");
	var deck_fieldset = document.getElementById("deck_composition");
	
    switch (event.target.value) {
		case "deck":
			quality_fieldset.classList.add("hidden");
			set_fieldset.classList.add("hidden");
			deck_fieldset.classList.remove("hidden");
			break;
        case "carta":
            quality_fieldset.classList.remove("hidden");
            set_fieldset.classList.remove("hidden");
			deck_fieldset.classList.add("hidden");
            break;
        case "box":
        case "tin":
        case "pacchetto":
        case "structure":
            quality_fieldset.classList.add("hidden");
            set_fieldset.classList.remove("hidden");
			deck_fieldset.classList.add("hidden");
            break;
        default:
            quality_fieldset.classList.add("hidden");
            set_fieldset.classList.add("hidden");
			deck_fieldset.classList.add("hidden");
    }
}

var next_id = 0;
function setCartaEvents(div){
    div.getElementsByClassName("aggiungi_carta")[0].addEventListener("click", aggiungiCarta);
    div.getElementsByClassName("rimuovi_carta")[0].addEventListener("click", rimuovi_carta);

	var cardSearch = div.getElementsByClassName("autocompletabile")[0];
	if (autocomplete && cardSearch){
		if (!cardSearch.id){
			cardSearch.id = "carta" + next_id;
			next_id++;
		}
		var autArgs = [cardSearch, "http://localhost/Max-Cards-/servletCercaCartaDeckJson", "get", null];
		autocomplete(...autArgs, true, ricercaAutoc.bind(null, ...autArgs));
	}
	
	var cardQnt = div.getElementsByClassName("qnt_carta")[0];
	if(cardSearch && cardQnt){
		cardSearch.addEventListener("input", function(){
			cardQnt.required = (cardSearch.value != "");
		})
	}
	
    function aggiungiCarta(){
        var fieldset = div.parentElement;
        var clone = div.cloneNode(true);
        setCartaEvents(clone);
        fieldset.appendChild(clone);
        div.classList.add("inserita");
    }

    function rimuovi_carta(){
        div.parentElement.removeChild(div);
    }
}

function ricercaAutoc(inp, link, method) {
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

function loadProdData(prod){
	
}
