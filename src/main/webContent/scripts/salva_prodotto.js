window.addEventListener("load", function() {
    HTMLCollection.prototype.forEach = Array.prototype.forEach;

    if (imageUploadSetup)
        imageUploadSetup()

    const fieldsets = document.getElementsByTagName("fieldset");
    if (fieldsetsResetSetup) {
        fieldsetsResetSetup(fieldsets);
        fieldsets.forEach(function(fieldset) {
            fieldset.saveState();
            var button = fieldset.getElementsByClassName("reset_button")[0];
            if (button)
                button.addEventListener("click", function() {
                    fieldset.loadState();
                })
        })

        const deck_fieldset = document.getElementById("deck_composition");
        deck_fieldset.oldLoad = deck_fieldset.loadState;
        deck_fieldset.loadState = function() {
            const cards = deck_fieldset.getElementsByClassName("carte_contenute");
            const last_card = cards[cards.length - 1];
            const deck_reset = deck_fieldset.getElementsByClassName("reset_button")[0];
            const children = [];
            for (var i = 1;i < (deck_fieldset.state.inputs.length - 1) / 4;i++) {
                const clone = last_card.cloneNode(true);
                clone.classList.add("inserita");
                setCartaEvents(clone);
                children.push(clone);
            }
            children.push(last_card)
            children.push(deck_reset);
            deck_fieldset.replaceChildren(...children);
            deck_fieldset.oldLoad();
        }
    }

    document.getElementsByClassName("carte_contenute").forEach(function(div) {
        setCartaEvents(div);
    })

    if (autocomplete) {
        var prodSearch = document.getElementById("ricerca_prodotto");
        var autArgs = [prodSearch, "http://localhost/Max-Cards-/servletProdottoElencoJson", "get", null];
        autocomplete(...autArgs, true, ricercaAutoc.bind(null, ...autArgs));
        document.getElementById("carica_prodotto").addEventListener("click", function() {
            ajax("http://localhost/Max-Cards-/servletProdottoNomeJson", "get", "nome=" + prodSearch.value, function(request) {
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
            loader_reset.addEventListener("click", function() {
                document.getElementById("prod_retrieve_error").classList.add("hidden")
            });
    }

    document.getElementById("tipo_prodotto").addEventListener("change", updateTipoProd)

    var prezzo = document.getElementById("prezzo_prodotto");
    var sconto = document.getElementById("sconto_prodotto");
    var prezzo_scontato = document.getElementById("prezzo_finale");
    const priceUpdate = function() {
        if (prezzo.value && sconto.value)
            prezzo_scontato.innerHTML = Math.floor(prezzo.value - (prezzo.value * (sconto.value / 100))) + "€";
        else
            prezzo_scontato.innerHTML = "";
    };
    prezzo.addEventListener("change", priceUpdate);
    sconto.addEventListener("change", priceUpdate);

    document.getElementById("set_prodotto").addEventListener("change", function(event) {
        var new_set = document.getElementById("nuovo_set");
        var data_set = document.getElementById("data_set");
        if (event.target.value == "nuovo") {
            new_set.parentNode.classList.remove("invisible");
            new_set.required = true;
            data_set.parentNode.classList.remove("invisible");
            data_set.required = true;
        }
        else {
            new_set.parentNode.classList.add("invisible");
            new_set.required = false;
            data_set.parentNode.classList.add("invisible");
            data_set.required = false;
        }
    })
})

function updateTipoProd(event) {
    var language_fieldset = document.getElementById("product_language");
    var quality_fieldset = document.getElementById("product_quality");
    var set_fieldset = document.getElementById("product_set");
    var deck_fieldset = document.getElementById("deck_composition");

    switch (event.target.value) {
        case "deck":
            language_fieldset.classList.remove("hidden");
            setLinguaRequired(true);
            quality_fieldset.classList.add("hidden");
            setQualityRequired(false);
            set_fieldset.classList.add("hidden");
            setCsetRequired(false);
            deck_fieldset.classList.remove("hidden");
            setDeckRequired(true);
            break;
        case "carta":
            language_fieldset.classList.remove("hidden");
            setLinguaRequired(true);
            quality_fieldset.classList.remove("hidden");
            setQualityRequired(true);
            quality_fieldset.classList.remove("hidden");
            setQualityRequired(true);
            set_fieldset.classList.remove("hidden");
            setCsetRequired(true);
            deck_fieldset.classList.add("hidden");
            setDeckRequired(false);
            break;
        case "box":
        case "tin":
        case "pacchetto":
        case "structure":
            language_fieldset.classList.remove("hidden");
            setLinguaRequired(true);
            quality_fieldset.classList.add("hidden");
            setQualityRequired(false);
            set_fieldset.classList.remove("hidden");
            setCsetRequired(true);
            deck_fieldset.classList.add("hidden");
            setDeckRequired(false);
            break;
        default:
            language_fieldset.classList.add("hidden");
            setLinguaRequired(false);
            quality_fieldset.classList.add("hidden");
            setQualityRequired(false);
            set_fieldset.classList.add("hidden");
            setCsetRequired(false);
            deck_fieldset.classList.add("hidden");
            setDeckRequired(false);
    }
}

var next_id = 0;
function setCartaEvents(div) {
    div.getElementsByClassName("aggiungi_carta")[0].addEventListener("click", aggiungiCarta);
    div.getElementsByClassName("rimuovi_carta")[0].addEventListener("click", rimuovi_carta);

    var cardSearch = div.getElementsByClassName("nome_carta")[0];
    if (autocomplete && cardSearch) {
        if (!cardSearch.id) {
            cardSearch.id = "carta" + next_id;
            next_id++;
        }
        var autArgs = [cardSearch, "http://localhost/Max-Cards-/servletCercaCartaDeckJson", "get", null];
        autocomplete(...autArgs, true, ricercaAutoc.bind(null, ...autArgs));
    }

    var cardQnt = div.getElementsByClassName("qnt_carta")[0];
    if (cardSearch && cardQnt) {
        cardSearch.addEventListener("change", function() {
            cardQnt.required = (cardSearch.value != "");
        })
    }

    function aggiungiCarta() {
        var fieldset = div.parentElement;
        var clone = div.cloneNode(true);
        setCartaEvents(clone);
        var cln_carta = clone.getElementsByClassName("nome_carta")[0];
        var cln_qnt = clone.getElementsByClassName("qnt_carta")[0];
        if (cln_carta && cln_qnt) {
            cln_carta.value = "";
            cln_qnt.value = "";
        }
        fieldset.appendChild(clone);
        div.after(clone);
        div.classList.add("inserita");
    }

    function rimuovi_carta() {
        div.parentElement.removeChild(div);
    }
}

function setLinguaRequired(bool) {
    document.getElementById("lingua_prodotto").required = bool;
}

function setCsetRequired(bool) {
    var set = document.getElementById("set_prodotto");
    set.required = bool;
    if (bool) {
        if (set.value == "nuovo") {
            document.getElementById("nuovo_set").required = true;
            document.getElementById("data_set").required = true;
        }
    }
    else {
        if (set.value != "nuovo") {
            document.getElementById("nuovo_set").required = false;
            document.getElementById("data_set").required = false;
        }
    }
}

function setQualityRequired(bool) {
    document.getElementById("qlt_prodotto").required = bool;
}

function setDeckRequired(bool) {
    document.getElementsByClassName("carte_contenute").forEach(function() {
        var carta = document.getElementsByClassName("nome_carta")[0];
        var qnt = document.getElementsByClassName("qnt_carta")[0];
        if (bool) {
            if (qnt) {
                qnt.required = (carta && carta.value != "");
            }
        }
        else {
            if (qnt) {
                qnt.required = false;
            }
        }
    })
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
            if (request.readyState < 4 || request.status != 200)
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

function loadProdData(prod) {//{id, tipo, nome, qnt, prezzo, descrizione, sconto, lingua, idSet, quality, idCarta, deck:[{nome:"", id:"", qnt:""}
    document.getElementById("old_id").value = prod.id;

    var tipo_select = document.getElementById("tipo_prodotto");
    tipo_select.getElementsByTagName("option").forEach(function(option) {
        if (option.value.toLowerCase() == prod.tipo.toLowerCase())
            tipo_select.value = option.value;
    })
    tipo_select.dispatchEvent(new Event("change"));
    document.getElementById("nome_prodotto").value = prod.nome;
    document.getElementById("qnt_prodotto").value = prod.qnt;
    document.getElementById("descr_prodotto").value = prod.descrizione;
    document.getElementById("product_data").saveState();

    var prezzo = document.getElementById("prezzo_prodotto");
    prezzo.value = prod.prezzo / 100;
    document.getElementById("sconto_prodotto").value = prod.sconto / 100;
    prezzo.dispatchEvent(new Event("change"));
    document.getElementById("product_price").saveState();

    document.getElementById("product_image").resetToDefault();
    document.getElementById("image_preview").src = "image?action=show&prodottoId=" + prod.id + "&isProdotto=true";

    if (prod.lingua) {
        var lingua_select = document.getElementById("lingua_prodotto");
        lingua_select.getElementsByTagName("option").forEach(function(option) {
            if (option.value.toLowerCase() == prod.lingua.toLowerCase())
                lingua_select.value = option.value;
        })
        document.getElementById("product_language").saveState();
    }

    if (prod.idSet) {
        var set_select = document.getElementById("set_prodotto");
        set_select.getElementsByTagName("option").forEach(function(option) {
            if (option.value.toLowerCase() == prod.idSet.toLowerCase())
                set_select.value = option.value;
        })
        set_select.dispatchEvent(new Event("change"));
        document.getElementById("product_set").saveState();
    }

    if (prod.quality) {
        var qlt_select = document.getElementById("qlt_prodotto");
        qlt_select.getElementsByTagName("option").forEach(function(option) {
            if (option.value.toLowerCase() == prod.quality.toLowerCase())
                qlt_select.value = option.value;
        })
        document.getElementById("product_quality").saveState();
    }

    var deck_section = document.getElementById("deck_composition");
    var deck = prod.deck;
    if (deck) {
        var inputs = [];
        for (var i = 0;i < deck.length;i++) {
            inputs.push(deck[i].nome);
            inputs.push(deck[i].qnt);
            inputs.push(deck_section.state.inputs[2]);
            inputs.push(deck_section.state.inputs[3]);
        }
        inputs.push(deck_section.state.inputs[deck_section.state.inputs.length - 1])
        deck_section.state.inputs = inputs;
        deck_section.loadState();
    }
}
