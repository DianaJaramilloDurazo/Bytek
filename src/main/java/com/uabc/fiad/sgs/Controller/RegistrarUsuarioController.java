package com.uabc.fiad.sgs.Controller;

import com.uabc.fiad.sgs.Entity.Usuario;
import com.uabc.fiad.sgs.Service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Controller
@RequestMapping("/registrar-usuario")
public class RegistrarUsuarioController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUsuarioService usuarioService;

    /**
     * Controlador para la vista de registrar usuario, es temporal en lo que se termina la historia de usuario
     * relativa a la lista de usuarios.
     *
     * @return  vista con la lista de usuarios
     */
    @GetMapping("/registrar")
    public String getRegistrarUsuario() {
        return "ListarUsuarios.html";
    }

    /**
     * Método de POST para registrar un usuario. Mapea el cuerpo de la petición a un objeto de
     * tipo Usuario
     *
     * @param usuario   el usuario a registrar
     * @return          html plano con un mensaje de estado, si se registró correctamente o no
     */
    @PostMapping(value = "/registrar",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CREATED)
    public String registrarUsuario(Usuario usuario) {

        // Obtener la contraseña plana
        String rawPwd = usuario.getPassword();

        // Encriptar la contraseña
        String encodedPwd = passwordEncoder.encode(rawPwd);

        // Cambiar la contraseña por la encriptada
        usuario.setPassword(encodedPwd);

        // Guardar el usuario
        Boolean res = usuarioService.save(usuario);

        // Si el resultado es verdadero regresar un mensaje de éxito, si no regresar un mensaje de error
        if (res != null && res) {
            return "<span class='text-success'>Usuario registrado correctamente</span>";
        } else {
            return "<span class='text-danger'>Error al intentar registrar el usuario</span>";
        }
    }

    /**
     * Obtiene el cuerpo del formulario de registrar usuario
     *
     * @return  fragmento de thymeleaf con el cuerpo del formulario
     */
    @GetMapping("/get-registrar-form")
    public String getRegistrarUsuarioForm() {
        return "fragments/usuario/registrar-usuario-form :: registrar-usuario-form";
    }

    /**
     * Valida un correo enviado en post
     * @param correo    el correo a validar
     * @return          fragmento de thymealf con el html del input de correo del formulario con clases de validación
     *                  correspondientes con el resultado
     */
    @PostMapping("/validar/correo")
    public String validarCorreo(@RequestParam(value="correo") String correo) {
        // Obtener el valor del correo sin el nombre del parámetro
//        String correo_val = correo.split("=")[1];

        // Validar el correo
        boolean valid = correo.endsWith("@uabc.edu.mx");

        return "fragments/usuario/registrar-usuario-form :: correo(valid=" + valid + ", value='" + correo + "')";
    }
}
