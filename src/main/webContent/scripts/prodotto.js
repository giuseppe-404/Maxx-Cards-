window.addEventListener("load", function(){
	HTMLCollection.prototype.forEach = Array.prototype.forEach;
	
	const button = document.getElementById("buy_button");
	const id = document.getElementById("buy_id");
	const qnt = document.getElementById("buy_qnt");
	const output = document.getElementById("carrello_output");
	
	button.addEventListener("click", function(){
		var quant=qnt.value;
        ajax("aggiungiCarrello", "get", "id="+id.value+"&qnt="+quant, function(request) {
            if (request.readyState < 4)
                return;
			output.classList.remove("hidden");
			if(request.status != 200)
				output.innerHTML = "Errore durante l'inserimento nel carrello";
			var diff = JSON.parse(request.responseText).diff;
			if(diff>0)
				output.innerHTML = "Aggiunte  "+diff+" unità al carrello! ("+quant+" totali)";
			else if(diff==0)
				output.innerHTML = quant+" unità già presenti nel carrello"
			else
				output.innerHTML = "Rimosse  "+diff+" unità dal carrello  ("+quant+" totali)"
			
        })
	})
	
	const show_deck_button = document.getElementById("mostra_carte");
	if (show_deck_button){
		const deck_images = document.getElementById("deck_section").getElementsByTagName("img");
		show_deck_button.addEventListener("click", function(){
			if (show_deck_button.checked)
				deck_images.forEach(function(image){image.classList.remove("hidden")});
			else
				deck_images.forEach(function(image){image.classList.add("hidden")});
		})
	}
})