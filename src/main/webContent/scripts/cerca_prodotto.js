window.addEventListener("load", function() {
    HTMLCollection.prototype.forEach = Array.prototype.forEach;

    const search_category = document.getElementById("search_category");
    const cerca = document.getElementById("cerca_prod");
    const nome = document.getElementById("nome");
    const descrizione = document.getElementById("descrizione");
    const prezzo = document.getElementById("prezzo");
    const lingua = document.getElementById("lingua");
    const quality = document.getElementById("quality");
    const idSet = document.getElementById("idSet");
    const fail = document.getElementById("fail");
    const template = document.getElementsByClassName("product_link")[0];

    if (cerca) {
        var param = "idProdotto=" + search_category.value;
        if (nome && nome.value)
            param += "&nome=" + nome.value
        if (descrizione && descrizione.value)
            param += "&descrizione=" + descrizione.value
        if (prezzo && prezzo.value)
            param += "&prezzo=" + prezzo.value
        if (lingua && lingua.value)
            param += "&lingua=" + lingua.value
        if (quality && quality.value)
            param += "&quality=" + quality.value
        if (idSet && idSet.value)
            param += "&idSet=" + idSet.value
        /*nome, descrizione, prezzo, lingua, idSet, quality, idProdotto (
            null o 0 > prodotot
            1 > carta sing
            2 > prod yugi 
            3 > confezionato
            4 > pacchetto
            5 > tin
            6 > box
            7 > structure
            8 > deck
        */
        cerca.addEventListener("click", function() {
            ajax("servletCercaProdottoJson", "post", param, function(request) {
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
                arr.forEach(function(prod) {
                    var clone = template.cloneNode(true);
                    clone.classList.remove("hidden")
                    children.push(clone);

                    clone.href = "getProdottoPage?id=" + prod.id;

                    if (prod.sconto > 0) {
                        var product_sale = clone.getElementsByClassName("product_sale")[0];
                        product_sale.classList.remove("hidden");
                        product_sale.getElementsByTagName("p")[1].innerHTMl = (prod.sconto / 100) + "%";
                    }

                    clone.getElementsByTagName("img")[0].src = "image?action=show&prodottoId=" + prod.id + "&isProdotto=true"
                    clone.getElementsByTagName("img")[0].alt = "immagine di" + prod.nome;

                    var product_description = clone.getElementsByClassName("product_description")[0];
                    product_description.getElementsByTagName("h3")[0].innerHTMl = prod.nome;
                    product_description.getElementsByTagName("p")[0].innerHTMl = prod.descrizione;
                })
                template.parentNode.replaceChildren(...children);
            })
        })

        cerca.click();
    }
})