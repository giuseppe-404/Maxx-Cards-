var banner;
var children_number;
var banner_animation;

window.addEventListener("load", function(event){
    if (bannerScrollSetup()){
        bannerScrollUpdate();
        mediaBannerSetup();
    }
})

function bannerScrollSetup(){
    banner = document.getElementById("product_selection");
    if(!banner || banner.children.length == 0)
        return false;

	do{
	    var children = banner.children;
	    children_number = children.length;
	    
	    children.forEach(function(item){
	        var clone = item.cloneNode(true);
	        banner.appendChild(clone);
	    });
	} while (children_number<6);

    banner.addEventListener("mouseover", function(){
        banner_animation.pause();
    }, false);

    banner.addEventListener("mouseleave", function(){
        banner_animation.play();
    }, false);

    return true;
}

function bannerScrollUpdate(){
    if (banner_animation)
        banner_animation.cancel();

    var banner_width = parseInt(window.getComputedStyle(banner).width, 10);
    var children_width = parseInt(window.getComputedStyle(banner.firstElementChild).width, 10);
    var children_width_percentage = Math.round(children_width / banner_width * 10) * 10;

    var animation_steps = [{transform: "translateX(-"+children_width_percentage+"%)"}];
    var interval = 1/children_number;
    for (var i = 0; i<children_number; i++){
        animation_steps.push({transform: "translateX(-"+ children_width_percentage * (i+1) +"%)", offset: interval * i + interval*0.7})
        animation_steps.push({transform: "translateX(-"+ children_width_percentage * (i+2) +"%)", offset: interval * (i+1)})
    }    
    
    banner_animation = banner.animate(
        animation_steps, {
        id: "banner_scroll",
        duration: children_number*1500, 
        iterations: Infinity
    })
}

function mediaBannerSetup(){
    window.matchMedia('screen and (max-width:480px)').addEventListener('change',mediaBannerUpdate);
    window.matchMedia('screen and (min-width: 481px) and (max-width:1080px)').addEventListener('change',mediaBannerUpdate);
    window.matchMedia('screen and (min-width:1081px)').addEventListener('change',mediaBannerUpdate);
}

function mediaBannerUpdate(media_query){
    console.log(media_query);
    if (!media_query.matches)
        return;
    bannerScrollUpdate();
}