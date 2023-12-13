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

function verificarContraseñas()
{
	var pwd1 = document.getElementById("password").value;
	var pwd2 = document.getElementById("confPassword").value;
	
	if(pwd1 === pwd2)
	{
		
	}else{
		
	}
}
