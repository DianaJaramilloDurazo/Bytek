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


	var formListo = true;
	var nombreEvento = document.getElementById('nombreEvento');
	var costo = document.getElementById('costo');
	var fSalida = document.getElementById('fSalida');
	var fRegreso = document.getElementById('fRegreso');
	var lugar = document.getElementById('lugar');
	var horaSalida = document.getElementById('horaSalida');
	var horaRegreso = document.getElementById('horaRegreso');
	var idCarrera = document.getElementById('idCarrera');
	if (nombreEvento.value === '') {
		var alertaEvento = document.getElementById('nombreEvento-alert');
		alertaEvento.style.display = 'block';
		formListo = false;
	} else {
		var alertaEvento = document.getElementById('nombreEvento-alert');
		alertaEvento.style.display = 'none';
	}

	if (costo.value === '' || costo.value.toString().charAt(0) === '-') {
		var alertaCosto = document.getElementById('costo-alert');
		alertaCosto.style.display = 'block';
		formListo = false;
	} else {
		var alertaCosto = document.getElementById('costo-alert');
		alertaCosto.style.display = 'none';
	}

	if (lugar.value === '') {
		var alertaLugar = document.getElementById('lugar-alert');
		alertaLugar.style.display = 'block';
	} else {
		var alertaLugar = document.getElementById('lugar-alert');
		alertaLugar.style.display = 'none';
	}
	if (fSalida.value === '') {
		var fSalidaAlerta = document.getElementById('fechas-alert');
		fSalidaAlerta.style.display = 'block';
		formListo = false;
	}
	if (fRegreso.value === '') {
		var alertafRegreso = document.getElementById('fechas-alert');
		alertafRegreso.style.display = 'block';
		formListo = false;
	} 

	if (lugar.value === '') {
		var alertaLugar = document.getElementById('fechas-alert');
		alertaLugar.style.display = 'block';
		formListo = false;
	} 

	if (horaSalida.value === '') {
		var alertahoraSalida = document.getElementById('fechas-alert');
		alertahoraSalida.style.display = 'block';
		formListo = false;
	} 
	if (horaRegreso.value === '') {
		var alertahoraRegreso = document.getElementById('fechas-alert');
		alertahoraRegreso.style.display = 'block'
		formListo = false;
	} 
	
	if(fSalida.value !== '' && fRegreso.value !== '' && horaSalida.value !== '' && horaRegreso.value !== ''){
		var alertahoraRegreso = document.getElementById('fechas-alert');
		alertahoraRegreso.style.display = 'none'
	}
	console.log(idCarrera.value);
	if (idCarrera.value == '0') {
		var alertaidCarrera = document.getElementById('idCarrera-alert');
		alertaidCarrera.style.display = 'block';
		formListo = false;
	}
	else {
		var alertaidCarrera = document.getElementById('idCarrera-alert');
		alertaidCarrera.style.display = 'none';
	}
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
	} else {
		var alertaRecurso = document.getElementById('recurso-alert');
		alertaRecurso.style.display = 'none';
	}
	if (!actvidadSeleccionada) {
		var alertaActividad = document.getElementById('actividad-alert');
		alertaActividad.style.display = 'block';
	} else {
		var alertaActividad = document.getElementById('actividad-alert');
		alertaActividad.style.display = 'none';
	}
	if (actvidadSeleccionada && recursoSeleccionado && formListo) {
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
	
	var formListo = true;
	var nombreEvento = document.getElementById('EnombreEvento');
	var costo = document.getElementById('Ecosto');
	var fSalida = document.getElementById('fSalidaE');
	var fRegreso = document.getElementById('fRegresoE');
	var lugar = document.getElementById('Elugar');
	var horaSalida = document.getElementById('EhoraSalida');
	var horaRegreso = document.getElementById('EhoraRegreso');
	console.log(fRegreso.value);
	if (nombreEvento.value === '') {
		var alertaEvento = document.getElementById('EnombreEvento-alert');
		alertaEvento.style.display = 'block';
		formListo = false;
	} else {
		var alertaEvento = document.getElementById('EnombreEvento-alert');
		alertaEvento.style.display = 'none';
	}
	if (costo.value === '' || costo.value.toString().charAt(0) === '-') {
		var alertaCosto = document.getElementById('Ecosto-alert');
		alertaCosto.style.display = 'block';
		formListo = false;
	} else {
		var alertaCosto = document.getElementById('Ecosto-alert');
		alertaCosto.style.display = 'none';
	}
	if(lugar.value === ''){
		var alertaLugar = document.getElementById('Elugar-alert');
		alertaLugar.style.display = 'block';
		formListo = false;
	}else{
		var alertaLugar = document.getElementById('Elugar-alert');
		alertaLugar.style.display = 'none';
	}	
	if (fSalida.value === '') {
		var fSalidaAlerta = document.getElementById('Efechas-alert');
		fSalidaAlerta.style.display = 'block';
		formListo = false;
	} 

	if (fRegreso.value == '') {
		var alertafRegreso = document.getElementById('Efechas-alert');
		alertafRegreso.style.display = 'block';
		formListo = false;
	} 
	if (horaSalida.value === '') {
		var alertahoraSalida = document.getElementById('Efechas-alert');
		alertahoraSalida.style.display = 'block';
		formListo = false;
	} 
	if (horaRegreso.value === '') {
		var alertahoraRegreso = document.getElementById('Efechas-alert');
		alertahoraRegreso.style.display = 'block'
		formListo = false;
	}
		if(fSalida.value !== '' && fRegreso.value !== '' && horaSalida.value !== '' && horaRegreso.value !== ''){
		var alertahoraRegreso = document.getElementById('Efechas-alert');
		alertahoraRegreso.style.display = 'none'
	}	

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
	} else {
		var alertaRecurso = document.getElementById('recursoEditar-alert');
		alertaRecurso.style.display = 'none';
	}
	if (!actvidadSeleccionada) {
		var alertaActividad = document.getElementById('actividadEditar-alert');
		alertaActividad.style.display = 'block';
	} else {
		var alertaActividad = document.getElementById('actividadEditar-alert');
		alertaActividad.style.display = 'none';
	}
	if (actvidadSeleccionada && recursoSeleccionado && formListo) {
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

function validarEditarActividad() {
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
		limpiarModalRechazar();
	}
	
}
function limpiarModalRechazar() {
	// Obtener el valor del textarea
	var texto = document.getElementById("motivoDescripcion")
	var alertaMotivos = document.getElementById('motivo-alert');
	alertaMotivos.style.display = 'none';
	texto.value = '';
}





