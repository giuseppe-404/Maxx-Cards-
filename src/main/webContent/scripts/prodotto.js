window.addEventListener("load", function() {
    HTMLCollection.prototype.forEach = Array.prototype.forEach;

    const button = document.getElementById("buy_button");
    const id = document.getElementById("buy_id");
    const qnt = document.getElementById("buy_qnt");
    const output = document.getElementById("carrello_output");
    const wish = document.getElementsByClassName("wish_button");

    button.addEventListener("click", function() {
        var quant = qnt.value;
        ajax("servletAggiungiCarrello", "get", "id=" + id.value + "&qnt=" + quant, function(request) {
            if (request.readyState < 4)
                return;
            output.classList.remove("hidden");
            if (request.status != 200) {
                output.innerHTML = "Errore durante l'inserimento nel carrello";
                return;
            }
            var diff = JSON.parse(request.responseText).diff;
            if (diff > 0)
                output.innerHTML = "Aggiunte  " + diff + " unità al carrello! (" + quant + " totali)";
            else if (diff == 0)
                output.innerHTML = quant + " unità già presenti nel carrello"
            else
                output.innerHTML = "Rimosse  " + Math.abs(diff) + " unità dal carrello  (" + quant + " totali)"

        })
    })

    var wish_switch = true;
    wish.forEach(function(input) {
        input.addEventListener("click", function() {
			ajax("gestioneWishList", "get", "id="+id.value+"&aggiungi="+wish_switch, function(request){
				if (request.readyState < 4)
                    return;
                output.classList.remove("hidden");
                if (request.status != 200) {
					if(wish_switch)
	                    output.innerHTML = "Errore durante l'inserimento nella wishlist";
					else
						output.innerHTML = "Errore durante la rimozione dalla wishlist";
                    return;
                }
				if (wish_switch) {
					output.innerHTML = "Aggiunto alla wishlist";
				    wish[0].classList.add("hidden");
				    wish[1].classList.remove("hidden");
				}
				else {
					output.innerHTML = "Rimosso dalla wishlist";
				    wish[0].classList.remove("hidden");
				    wish[1].classList.add("hidden");
				}
				wish_switch = !wish_switch
			})
        })
    })

    const show_deck_button = document.getElementById("mostra_carte");
    if (show_deck_button) {
        const deck_images = document.getElementById("deck_section").getElementsByTagName("img");
        show_deck_button.addEventListener("click", function() {
            if (show_deck_button.checked)
                deck_images.forEach(function(image) { image.classList.remove("hidden") });
            else
                deck_images.forEach(function(image) { image.classList.add("hidden") });
        })
    }
})