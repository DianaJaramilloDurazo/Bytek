function seleccionInicio(){
	var ruta = window.location.pathname;
	//window.alert('Estás en la página: ' + ruta);
	if(ruta === "/")
	{
	    document.getElementById("inicio").style.border="3px solid var(--text-color-verde)";
	 	document.getElementById("inicio").style.borderTop="none";
	 	document.getElementById("inicio").style.borderLeft="none";
	 	document.getElementById("inicio").style.borderRight="none";
	 	//otras opciones
	 	document.getElementById("administrar").style.border="none";
	}
	if(ruta === "/admin/administrarCuenta")
	{
		document.getElementById("administrar").style.border="3px solid var(--text-color-verde)";
	 	document.getElementById("administrar").style.borderTop="none";
	 	document.getElementById("administrar").style.borderLeft="none";
	 	document.getElementById("administrar").style.borderRight="none";
	 	//otras opciones
	 	document.getElementById("inicio").style.border="none";
	}

  
}
 
window.onload = seleccionInicio;

function habilitar_edicion(){
	var nombre = document.getElementById("nombre");
	var apPat = document.getElementById("apPat");
	var apMat = document.getElementById("apMat");
	//var correo = document.getElementById("correo");
	var categoria = document.getElementById("categoria");
	var carrera = document.getElementById("carrera");
	var numEmpleado = document.getElementById("numEmpleado");
	var pwd = document.getElementById("pwd");
	var editPerfil = document.getElementById("editPerfil");

	nombre.disabled = false;
	apMat.disabled = false;
	apPat.disabled = false;
	//correo.disabled = false;
	categoria.disabled = false;
	carrera.disabled = false;
	numEmpleado.disabled = false;
	pwd.disabled = false;
	editPerfil.style.display = "none";
}

function quitarModal(){
	const modal = bootstrap.Modal.getInstance(ModalPerfil);
	modal.hide();
	
	var nombre = document.getElementById("nombre");
	var apPat = document.getElementById("apPat");
	var apMat = document.getElementById("apMat");
	//var correo = document.getElementById("correo");
	var categoria = document.getElementById("categoria");
	var carrera = document.getElementById("carrera");
	var numEmpleado = document.getElementById("numEmpleado");
	var pwd = document.getElementById("pwd");
	var editPerfil = document.getElementById("editPerfil");

	nombre.disabled = true;
	apMat.disabled = true;
	apPat.disabled = true;
	//correo.disabled = true;
	categoria.disabled = true;
	carrera.disabled = true;
	numEmpleado.disabled = true;
	pwd.disabled = true;
	editPerfil.style.display = "block";
}

function validarCampos(campo, idcampo){
	var input = document.getElementById(idcampo);
	if (input.value.trim() === "") {
		// El input está vacío
		input.value=campo;
	}
}
