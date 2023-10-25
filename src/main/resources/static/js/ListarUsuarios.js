

function habilitar_edicion(){
	var nombre = document.getElementById("nombre");
	var apPat = document.getElementById("apPat");
	var apMat = document.getElementById("apMat");
	var correo = document.getElementById("correo");
	var categoria = document.getElementById("categoria");
	var carrera = document.getElementById("carrera");
	var numEmpleado = document.getElementById("numEmpleado");
	var pwd = document.getElementById("pwd");

	nombre.disabled = false;
	apMat.disabled = false;
	apPat.disabled = false;
	//correo.disabled = false;
	categoria.disabled = false;
	carrera.disabled = false;
	numEmpleado.disabled = false;
	pwd.disabled = false;
}

function quitarModal(){
	const modal = bootstrap.Modal.getInstance(ModalPerfil);
	modal.hide();
}

/*var guardarCambiosBtn = document.getElementById("guardarCambiosBtn");

guardarCambiosBtn.addEventListener("click", function() {
	var elemento = document.getElementById("result");
	setTimeout(function() {
		// Elimina el elemento
		elemento.innerHTML = "";
	}, 5000); // 5000 milisegundos = 5 segundos5
});*/

const myDiv = document.getElementById('result');
// Crea una instancia de MutationObserver
const observer = new MutationObserver((mutationsList, observer) => {
	// Se ejecutará cada vez que haya un cambio en el div
	console.log("Hubo cambios");
	setTimeout(function() {
		// Elimina el elemento
		myDiv.innerHTML = "";
	}, 3000); // 3000 milisegundos = 3 segundos
});
// Configura las opciones para observar cambios en el contenido del div
const config = { childList: true };

// Inicia la observación del div con las opciones configuradas
observer.observe(myDiv, config);