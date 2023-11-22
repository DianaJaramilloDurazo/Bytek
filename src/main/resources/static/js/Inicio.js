function quitarModal(modalId) {
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

		document.getElementById("btn_IrArriba").style.visibility = "visible";

	} else {

		document.getElementById("btn_IrArriba").style.visibility = "hidden";

	}
});

function activarInput(checkboxid, inputid) {
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
		input.value = '';
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
function validarForm() {
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
	if (!actvidadSeleccionada) {
		var alertaActividad = document.getElementById('actividad-alert');
		alertaActividad.style.display = 'block';
	}
	if (actvidadSeleccionada && recursoSeleccionado) {
		mostrarModal();
	}

}
// Función para mostrar el modal
function mostrarModalEditar() {
	var modal = document.getElementById("miModalEditar");
	modal.style.display = "block";
}

// Función para cerrar el modal
function cerrarModalEditar() {
	var modal = document.getElementById("miModalEditar");
	modal.style.display = "none";
}
function validarFormEditar() {
	var recursos = document.getElementsByName('recursosEditar');
	var checkboxes = document.getElementsByName('actividadesEditar');
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
		var alertaRecurso = document.getElementById('recursoEditar-alert');
		alertaRecurso.style.display = 'block';
	}
	if (!actvidadSeleccionada) {
		var alertaActividad = document.getElementById('actividadEditar-alert');
		alertaActividad.style.display = 'block';
	}
	if (actvidadSeleccionada && recursoSeleccionado) {
		mostrarModalEditar();
	}

}


const myDiv = document.getElementById('result-accion');
// Crea una instancia de MutationObserver
const observer = new MutationObserver((mutationsList, observer) => {
	// Se ejecutará cada vez que haya un cambio en el div
	console.log("Hubo cambios");
	setTimeout(function() {
		// Elimina el elemento
		myDiv.innerHTML = "";
	}, 3000); // 3000 milisegundos = 3 segundos
	quitarModal(ModalCrearSolicitud);
});
// Configura las opciones para observar cambios en el contenido del div
const config = { childList: true };

// Inicia la observación del div con las opciones configuradas
observer.observe(myDiv, config);

document.addEventListener('DOMContentLoaded', function() {
	// Tu código JavaScript aquí

});

function validarFechas(idFechaSalida, idFechaRegreso) {
	//Validar fechas
	var fechaSalida = document.getElementById(idFechaSalida);
	console.log(fechaSalida);
	var fechaRegreso = document.getElementById(idFechaRegreso);
	// Obtener referencias a los elementos de fecha
	var fechaActual = new Date().toISOString().split('T')[0];

	// Añadir un event listener al input de fecha de inicio
	fechaSalida.addEventListener('change', function() {
		console.log("CAMBIO DE FECHA");
		// Obtener la fecha seleccionada en el input de inicio
		var fechaInicio = new Date(fechaSalida.value);
		// Verificar si la fecha de inicio es anterior a la fecha actual
		if (fechaInicio < new Date()) {
			// Restaurar la fecha actual
			//fechaSalida.value = fechaActual;
		}
		// Verificar si la fecha de fin es anterior a la fecha de inicio
		var fechaFin = new Date(fechaRegreso.value);
		if (fechaFin < fechaInicio) {
			// Asignar la fecha de inicio + 1 día al input de fecha de fin
			fechaRegreso.value = new Date(fechaInicio.getTime() + 86400000).toISOString().split('T')[0];
		}
	});

	// Añadir un event listener al input de fecha de fin
	fechaRegreso.addEventListener('change', function() {
		// Verificar si la fecha de fin es anterior a la fecha de inicio
		var fechaInicio = new Date(fechaSalida.value);
		var fechaFin = new Date(fechaRegreso.value);
		if (fechaFin < fechaInicio) {
			// Asignar la fecha de inicio + 1 día al input de fecha de fin
			fechaRegreso.value = new Date(fechaInicio.getTime() + 86400000).toISOString().split('T')[0];
		}
	});

}

function validarEditarRecursos(idCheckbox) {
	if (idCheckbox == '2') {
		var checkbox2 = document.getElementById('Transporte');
		var input2 = document.getElementById('ITransporte');

		// Verificar si el checkbox está seleccionado
		if (checkbox2.checked) {
			// Activar el input
			input2.disabled = false;
		} else {
			// Desactivar el input
			input2.disabled = true;
			input2.value = '';
		}
	}
		if (idCheckbox == '5') {
		var checkbox5 = document.getElementById('Otro');
		var input5 = document.getElementById('IOtro');

		// Verificar si el checkbox está seleccionado
		if (checkbox5.checked) {
			// Activar el input
			input5.disabled = false;
		} else {
			// Desactivar el input
			input5.disabled = true;
			input5.value = '';
		}
	}
}

function validarEditarActividad(){
			var checkbox2 = document.getElementById('Otra');
		var input2 = document.getElementById('IOtra');

		// Verificar si el checkbox está seleccionado
		if (checkbox2.checked) {
			// Activar el input
			input2.disabled = false;
		} else {
			// Desactivar el input
			input2.disabled = true;
			input2.value = '';
		}
}
function validarMotivo() {
	// Obtener el valor del textarea
	var texto = document.getElementById("motivoDescripcion").value;
	var botonRechazar = document.getElementById("botonRechazar");
	var alertaMotivos = document.getElementById('motivo-alert');
	// Verificar si el textarea está vacío
	if (texto.trim() === "") {
		alertaMotivos.style.display = 'block';
	} else {
		alertaMotivos.style.display = 'none';
		botonRechazar.click();
	}
	limpiarModalRechazar()
}
function limpiarModalRechazar(){
	// Obtener el valor del textarea
	var texto = document.getElementById("motivoDescripcion")
	var alertaMotivos = document.getElementById('motivo-alert');
	alertaMotivos.style.display = 'none';
	texto.value = '';
}





