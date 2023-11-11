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