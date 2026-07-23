window.addEventListener("load", function(){
	HTMLCollection.prototype.forEach = Array.prototype.forEach;
	
	const orders = document.getElementById("orders_field");
	const template = document.getElementsByClassName("order_section")[0];
	const mail_input = document.getElementById("email_input");
	const output = document.getElementById("output");	
	
	document.getElementById("email_button").addEventListener("click", function(){
		const mail = mail_input.value;
		ajax("servletGetOrdine", "post", "email="+mail, function(request){
            if (request.readyState < 4)
                return;
            output.classList.remove("hidden");
            if (request.status != 200) {
                output.innerHTML = "Impossibile caricare gli ordini";
                return;
            }
			output.innerHTML = "ordini caricati";
			const arr = JSON.parse(request.responseText);
			arr.forEach(function(obj){
				var clone=template.cloneNode(true);
				clone.classList.remove("hidden");
				orders.appendChild(clone);
				
				var ordine = obj.ordine;
				clone.getElementsByClassName("id")[0].value = ordine.id_ordine;
				clone.getElementsByClassName("data_acquisto")[0].value = ordine.data_acquisto;
				if(ordine.data_consegna){
					clone.getElementsByClassName("data_consegna")[0].value = ordine.data_consegna;
					clone.getElementsByClassName("data_consegna")[0].readOnly = true;;
				}
				
				var stato = clone.getElementsByClassName("stato")[0];
				stato.getElementsByTagName("option").forEach(function(option){
					if(option.value.toLowerCase() == ordine.stato.toLowerCase())
						stato.value = option.value
				})
				
				clone.getElementsByClassName("update_button")[0].addEventListener("click", function(){
					ajax("servletSetStatoOrdine", "post", "id="+ordine.id_ordine+"&stato="+stato.value+"&data="+clone.getElementsByClassName("data_consegna")[0].value, function(request){
						if (request.readyState < 4)
							return;
						output.classList.remove("hidden");
						if (request.status != 200){
							output.innerHTML = "Impossibile aggiornare l'ordine";
							return;
						}
						output.innerHTML = "Ordine aggiornato";
					})
				})
				
				clone.getElementsByClassName("show_check")[0].addEventListener("click", function(e){
					if (e.target.checked)
						clone.getElementsByClassName("order_product")[0].classList.remove("hidden");
					else
						clone.getElementsByClassName("order_product")[0].classList.add("hidden");
				})
				
				
				var prodotti = obj.prodotti;
				var product_header = clone.getElementsByClassName("product_header")[0];
				var product_data = clone.getElementsByClassName("product_data")[0];
				var product_desc = clone.getElementsByClassName("product_desc")[0];
				var elenco = [product_header];
				prodotti.forEach(function(prodotto){
					var frow = product_data.cloneNode(true);
					frow.getElementsByClassName("product_nome")[0].innerHTML = prodotto.nome;
					frow.getElementsByClassName("product_prezzo")[0].innerHTML = prodotto.prezzo;
					frow.getElementsByClassName("product_qnt")[0].innerHTML = prodotto.qnt;
					var srow = product_desc.cloneNode(true);
					srow.getElementsByClassName("product_info")[0].innerHTML = prodotto.info;
					elenco.push(frow);
					elenco.push(srow);
				})
				product_data.parentNode.replaceChildren(...elenco);
			})
		});
	})
})