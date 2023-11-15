function quitarModal(modalId){
  const modal = bootstrap.Modal.getInstance(modalId);
  modal.hide();

  var btn_Guardar = document.getElementById("btn_Guardar");
  btn_Guardar.style.display = "none";
  var btn_editar = document.getElementById("btn_editar");
  btn_editar.style.display = "block";
}

window.addEventListener('scroll', function() {
  var scrollPosition = window.pageYOffset;
  // Comprobar si el desplazamiento es igual o mayor a 500px
  if (scrollPosition >= 500) {

    document.getElementById("btn_IrArriba").style.visibility ="visible";
    
  }else{
    
    document.getElementById("btn_IrArriba").style.visibility ="hidden";
    
  }
});

function activarInput(checkboxid,inputid) {
      // Obtener el checkbox y el input
      var checkbox = document.getElementById(checkboxid);
      var input = document.getElementById(inputid);
       
      // Verificar si el checkbox está seleccionado
      if (checkbox.checked) {
        // Activar el input
        input.disabled = false;
      } else {
        // Desactivar el input
        input.disabled = true;
      }
    }
   
   // Función para mostrar el modal
function mostrarModal() {
	var modal = document.getElementById("miModal");
	modal.style.display = "block";
}

// Función para cerrar el modal
function cerrarModal() {
	var modal = document.getElementById("miModal");
	modal.style.display = "none";
}  

var boton = document.getElementById('crearSolicitud');
function validarForm(){
var recursos = document.getElementsByName('recursos');
  var checkboxes = document.getElementsByName('actividades');
  var actvidadSeleccionada = false;
  var recursoSeleccionado = false;
  // Verifica si al menos uno de los checkboxes está seleccionado
  for (var i = 0; i < checkboxes.length; i++) {
    if (checkboxes[i].checked) {
      actvidadSeleccionada = true;
      break;
    }
  }
  // Verifica si al menos uno de los checkboxes está seleccionado
  for (var i = 0; i < recursos.length; i++) {
    if (recursos[i].checked) {
      recursoSeleccionado = true;
      break;
    }
  }
  // Muestra un mensaje si no hay ningún checkbox seleccionado
  if (!recursoSeleccionado) {
      var alertaRecurso = document.getElementById('recurso-alert');
	  alertaRecurso.style.display = 'block';
  }
  if(!actvidadSeleccionada){
	  var alertaActividad = document.getElementById('actividad-alert');
	  alertaActividad.style.display = 'block';
  }
  if(actvidadSeleccionada && recursoSeleccionado){
	  mostrarModal();
  }

}
const myDiv = document.getElementById('result-cancelar');
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
