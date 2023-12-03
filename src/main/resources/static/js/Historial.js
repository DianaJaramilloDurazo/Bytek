function quitarModal(modalId){
  const modal = bootstrap.Modal.getInstance(modalId);
  modal.hide();
}

function activarFiltros()
{
	var div = document.getElementById("filtrosid");
	var estiloCalculado = window.getComputedStyle(div);
	var estado = estiloCalculado.display;
	
	if(estado === "none")
	{
		div.style.display = "flex";
		divact = true;
	}else{
		div.style.display ="none";
	}
}