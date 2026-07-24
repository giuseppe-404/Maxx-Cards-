window.addEventListener("load", function() {
    HTMLCollection.prototype.forEach = Array.prototype.forEach;

    const wish_divs = document.getElementsByClassName("prodotto_wishlist");
    wish_divs.forEach(function(div) {
        const wish = div.getElementsByClassName("wish_button");
		const id = div.getElementsByClassName("wish_id")[0];
		var output = div.getElementsByClassName("output_msg")[0];
        var wish_switch = false;
		
        wish.forEach(function(input) {
            input.addEventListener("click", function() {
                ajax("gestioneWishList", "get", "id=" + id.value + "&aggiungi=" + wish_switch, function(request) {
                    if (request.readyState < 4)
                        return;
                    output.classList.remove("hidden");
                    if (request.status != 200) {
                        if (wish_switch)
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
	})
})
