window.addEventListener("load", function(){
	HTMLCollection.prototype.forEach = Array.prototype.forEach;
	
	const metodo_scelto = document.getElementById("metodo_scelto");
	const metodo = document.getElementById("metodo");
	
	const info_scelte = document.getElementById("info_scelte");
	const info_sections = document.getElementsByClassName("info_section");
	
	if(metodo_scelto && metodo){
        metodo_scelto.addEventListener("change", function() {
            metodo.required = (metodo_scelto.selectedIndex == 1);
            metodo.value = "";
        })	
	}
	
	if(info_scelte && info_sections){
		info_scelte.addEventListener("change", function(){
            var info_section = document.getElementById("info_" + info_scelte.value);

            info_sections.forEach(function(section) {
                section.getElementsByTagName("input").forEach(function(input) {
                    input.disabled = true;
                })
                section.classList.add("hidden")
            })

            info_section.getElementsByTagName("input").forEach(function(input) {
                input.disabled = false;
            })
            info_section.classList.remove("hidden");
        })
	}
	
	
})