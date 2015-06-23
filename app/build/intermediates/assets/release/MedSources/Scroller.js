function findH1(text){
	text= text.toLowerCase();//reduce el texto a miniscula
	text= text.replace(/\s/g, '');
	//console.log(text);

	var titulos= document.getElementsByTagName("h1");	

	for (var i = titulos.length - 1; i >= 0; i--) {
		var h1= titulos[i];
		var h1Text= h1.textContent;
		h1Text= h1Text.toLowerCase();
		h1Text= h1Text.replace(/\s/g, '');
		//console.log(h1Text);


		if(text==h1Text){
			//console.log("una coincidencia: "+text);
			var py= h1.offsetTop;
			//console.log(py);
			$("body").animate({scrollTop : py},1100);
			//window.scrollTo(0, py);
		}
	};
};

/*function execute(){
	console.log("Pagina cargada");

	document.onclick= function(){
		findH1("DESCRIPCIÓN");
	};

	var imagen= document.getElementsByTagName("img");
	console.log("Atributo de img:  "+imagen[0]);

	$("p:first").html("img: "+imagen[0]);
	$("p:eq(1)").html("src: "+imagen[0].src);
};*/


/*$(document).ready(function(){
	execute();
});*/

