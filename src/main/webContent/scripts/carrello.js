window.addEventListener("load", function(){
	HTMLCollection.prototype.forEach = Array.prototype.forEach;
	
	document.getElementsByClassName("prodotto_carrello").forEach(function(td){
		const button = td.getElementsByClassName("update_button")[0];
		const id = td.getElementsByClassName("ord_id")[0];
		const price = td.getElementsByClassName("ord_price")[0];
		const qnt = td.getElementsByClassName("ord_qnt")[0];
		var og_qnt = qnt.value;
		const msg = td.getElementsByClassName("output_msg")[0];
		const final_price = td.getElementsByClassName("final_price")[0];
		
		qnt.addEventListener("change", function(){
			button.disabled = (og_qnt == qnt.value)
		})
		
		button.addEventListener("click", function(){
			msg.classList.add("hidden");
			const quant = qnt.value;
            ajax("servletAggiungiCarrello", "get", "idProdotto=" + id.value + "&qnt=" + quant, function(request) {
                if (request.readyState < 4)
                    return;
                msg.classList.remove("hidden");
                if (request.status != 200){
                    msg.innerHTML = "Error";
					msg.classList.add("error_msg");
					return;
				}
				msg.classList.remove("error_msg");
                msg.innerHTML = "Fatto. (" + quant + ")";
				final_price.innerHTML = (price * quant / 100) +"€"
				og_qnt = quant;
				qnt.dispatchEvent(new Event("change"))
            })
		})
	})
})