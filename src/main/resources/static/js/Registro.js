//window.addEventListener("Keyup", habilitar);

function habilitar()
{
     correo = document.getElementById("correo").value;
     pwd = document.getElementById("pwd").value;

    if(pwd != "" && correo != "") 
    {
        document.getElementById("btn-enviar").disabled = false;
        
    }else
    {
        document.getElementById("btn-enviar").disabled = true;
    }
}


