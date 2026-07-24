window.addEventListener("load", function() {
    HTMLCollection.prototype.forEach = Array.prototype.forEach;

	const cerca = document.getElementById("cerca_prod");
    const classe = document.getElementById("classe");
    const nome = document.getElementById("nome");
    const pnt = document.getElementById("pnt");
    const testo = document.getElementById("testo");
    const tipologia = document.getElementById("tipologia");
    const livello = document.getElementById("livello");
    const categoria = document.getElementById("categoria");
    const attributo = document.getElementById("attributo");
    const attacco = document.getElementById("attacco");
    const difesa = document.getElementById("difesa");
    const tuner = document.getElementById("tuner");
    const scala = document.getElementById("scala");
    const tipo = document.getElementById("tipo");
    const freccia1 = document.getElementById("freccia1");
    const freccia2 = document.getElementById("freccia2");
    const freccia3 = document.getElementById("freccia3");
    const freccia4 = document.getElementById("freccia4");
    const freccia5 = document.getElementById("freccia5");
    const freccia6 = document.getElementById("freccia6");
    const freccia7 = document.getElementById("freccia7");
    const freccia8 = document.getElementById("freccia8");
	const monster_fieldset = document.getElementById("monster_fieldset");
    const fail = document.getElementById("fail");
    const template = document.getElementsByClassName("product_link")[0];
	
	if (classe) {
	        classe.addEventListener("change", function(){
	            var figli = [tipologia.firstElementChild];
	            var valori = [];
	            switch (classe.value) {
	                case "mostro":
	                    valori = ["Normale", "Fusione", "Synchro", "XYZ", "Rituale", "Link"];
	                    monster_fieldset.classList.remove("hidden");
	                    break;
	                case "magia":
	                    valori = ["Normale", "Rapida", "Continua", "Equipaggiamento", "Rituale", "Terreno"];
	                    monster_fieldset.classList.add("hidden")
	                    break;
	                case "trappola":
	                    valori = ["Normale", "Contro", "Continua"];
	                    monster_fieldset.classList.add("hidden")
	                    break;
	            }
	            valori.forEach(function(string) {
	                var option = document.createElement("option");
	                option.value = string;
	                option.innerHTML = string;
	                figli.push(option);
	            })
	            tipologia.replaceChildren(...figli);
	        })
	    }
	
    if (cerca) {
        cerca.addEventListener("click", function() {
            var param = "classe=" + classe.value;
            if (nome && nome.value)
                param += "&nome=" + nome.value
            if (pnt && pnt.value)
                param += "&pnt=" + pnt.value
            if (testo && testo.value)
                param += "&testo=" + testo.value
            if (tipologia && tipologia.value)
                param += "&tipologia=" + tipologia.value
            if (livello && livello.value)
                param += "&livello=" + livello.value
            if (categoria && categoria.value)
                param += "&categoria=" + categoria.value
            if (attributo && attributo.value)
                param += "&attributo=" + attributo.value
            if (attacco && attacco.value)
                param += "&attacco=" + attacco.value
            if (difesa && difesa.value)
                param += "&difesa=" + difesa.value
            if (tuner && tuner.value)
                param += "&tuner=" + tuner.value
            if (scala && scala.value)
                param += "&scala=" + scala.value
            if (tipo && tipo.value)
                param += "&tipo=" + tipo.value
            if (freccia1 && freccia1.checked)
                param += "&freccia1=" + freccia1.value
            if (freccia2 && freccia2.checked)
                param += "&freccia2=" + freccia2.value
            if (freccia3 && freccia3.checked)
                param += "&freccia3=" + freccia3.value
            if (freccia4 && freccia4.checked)
                param += "&freccia4=" + freccia4.value
            if (freccia5 && freccia5.checked)
                param += "&freccia5=" + freccia5.value
            if (freccia6 && freccia6.checked)
                param += "&freccia6=" + freccia6.value
            if (freccia7 && freccia7.checked)
                param += "&freccia7=" + freccia7.value
            if (freccia8 && freccia8.checked)
                param += "&freccia8=" + freccia8.value

            ajax("servletCercaCartaJson", "post", param, function(request) {
                if (request.readyState < 4)
                    return
                if (request.status != 200)
                    return

                var arr = JSON.parse(request.responseText);
                if (arr.length == 0) {
                    fail.classList.remove("hidden");
                    return
                }
                fail.classList.add("hidden");

                var children = [template]
                arr.forEach(function(card) {
                    var clone = template.cloneNode(true);
                    clone.classList.remove("hidden")
                    children.push(clone);

                    clone.href = "getCartaPage?id=" + card.id;

                    clone.getElementsByTagName("img")[0].src = "uploadImmagine?action=show&prodottoId=" + card.id + "&isProdotto=false"
                    clone.getElementsByTagName("img")[0].alt = "immagine di" + card.nomeIt;

                    var product_description = clone.getElementsByClassName("product_description")[0];
                    product_description.getElementsByTagName("h3")[0].innerHTML = card.nomeIt;
                })
                template.parentNode.replaceChildren(...children);
            })
        })

        cerca.click();
    }
})