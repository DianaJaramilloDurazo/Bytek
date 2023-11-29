package com.uabc.fiad.sgs.controller;

import com.uabc.fiad.sgs.DTO.UsuarioDTO;
import com.uabc.fiad.sgs.entity.Filtros;
import com.uabc.fiad.sgs.entity.Rol;
import com.uabc.fiad.sgs.entity.Solicitud;
import com.uabc.fiad.sgs.entity.Usuario;
import com.uabc.fiad.sgs.service.IUsuarioService;
import com.uabc.fiad.sgs.utils.SessionUtils;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUsuarioService usuarioService;

    /**
     * Regresa la vista para administrar cuentas y genera una paginación con una lista de usuarios
     * @param model     el modelo utilizado para pasar datos a la vista
     * @return          La vista para administrar cuentas
     */
    @HxTrigger("refresh")
    @GetMapping("/administrarCuenta")
    public String listarUsuario(Model model) {
        int page = 1;
        Filtros filtros = new Filtros();
        filtros.setPage(page);
//        model.addAttribute("current", page);
        model.addAttribute("filtros", filtros);
        return "ListarUsuarios";
    }

    /**
     * Obtiene una página de usuarios paginada.
     * @param filtros   los filstros aplicados, número de página, nombre, etc.
     * @param model     el modelo utilizado para pasar datos a la vista
     * @return          fragmento para mostrar la lista de usuarios
     */

    /**
     * Regresa una vista para registrar roles
     * @return  Vista para el registro del roles
     */
    @GetMapping("/registro-rol")
    String login() {
        return "registroRol";
    }

    /**
     * Método de POST para registrar un Rol. Mapea el cuerpo de la petición a un objeto de tipo Rol
     * @param rol    Objeto rol a registrar
     * @return       html plano con un mensaje de estado, si se registró correctamente o no
     */
    @PostMapping(value = "/registrar",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CREATED)
    public String registrarUsuario(Rol rol) {
        System.out.println(rol);
        String rawPwd =rol.getPassword();

        // Encriptar la contraseña
        String encodedPwd = passwordEncoder.encode(rawPwd);

        // Cambiar la contraseña por la encriptada
        rol.setPassword(encodedPwd);

        // Guardar el usuario
        Boolean res = usuarioService.saveRol(rol);
        if(res){
            return "<p>Rol registrado correctamente</p>";
        }
        return "<p>Error al registrar  el Rol</p>";
    }

    /**
     * Obtiene una página de usuarios paginada.
     * @param filtros   los filstros aplicados, número de página, nombre, etc.
     * @param model     el modelo utilizado para pasar datos a la vista
     * @return          fragmento para mostrar la lista de usuarios
     */

    @GetMapping("/pagination")
    public String paginacion(@ModelAttribute Filtros filtros, Model model) {

        // Manejar filtros
        model.addAttribute("filtros", filtros);

        // Tamaño de página: cuántos registros se mostrarán por página
        int pageSize = 4;

        // Calcular el desplazamiento (offset) para determinar desde qué registro se debe iniciar en la base de datos
        int offset = (filtros.getPage() - 1) * pageSize;

        //Obtener total de registros
        Integer totalRecords = usuarioService.TotalRecords(filtros);

        // Calcular el número total de páginas (totalPages) usando una división entera
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
        // Define el número máximo de páginas a mostrar en la barra de paginación
        int maxPagesToShow = 4;

        // Calcula el rango de páginas a mostrar
        int startPage = Math.max(1, filtros.getPage() - maxPagesToShow / 2);
        int endPage = Math.min(totalPages, startPage + maxPagesToShow - 1);

        // Crear una lista de números de página para mostrar en la interfaz de usuario
        List<Integer> pages = IntStream.rangeClosed(startPage, endPage).boxed().toList();
        model.addAttribute("first", startPage);
        model.addAttribute("pages", pages);
        model.addAttribute("current", filtros.getPage());
        model.addAttribute("next", filtros.getPage() + 1);
        model.addAttribute("prev", filtros.getPage() - 1);
        model.addAttribute("end", endPage);
        model.addAttribute("last", totalPages);
        List<UsuarioDTO> users = usuarioService.pagination(pageSize, offset, filtros);
        model.addAttribute("users",users);
        return "ListarUsuarios :: listaUsuarios";
    }

    


}
