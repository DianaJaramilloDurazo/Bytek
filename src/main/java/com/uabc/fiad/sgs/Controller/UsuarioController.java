package com.uabc.fiad.sgs.controller;


import com.uabc.fiad.sgs.DTO.UsuarioDTO;
import com.uabc.fiad.sgs.entity.Usuario;
import com.uabc.fiad.sgs.service.IUsuarioService;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

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
        model.addAttribute("current", page);
        return "ListarUsuarios";
    }

    /**
     * Obtiene una página de usuarios paginada.
     * @param page      el número de página actual
     * @param model     el modelo utilizado para pasar datos a la vista
     * @return          fragmento para mostrar la lista de usuarios
     */

    @GetMapping("/pagination")
    public String paginacion(@RequestParam(value="page") Integer page, Model model) {

        // Tamaño de página: cuántos registros se mostrarán por página
        int pageSize = 5;

        // Calcular el desplazamiento (offset) para determinar desde qué registro se debe iniciar en la base de datos
        int offset = (page - 1) * pageSize;

        //Obtener total de registros
        Integer totalRecords = usuarioService.TotalRecords();

        // Calcular el número total de páginas (totalPages) usando una división entera
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        // Crear una lista de números de página para mostrar en la interfaz de usuario
        List<Integer> pages = IntStream.rangeClosed(1, totalPages).boxed().toList();

        model.addAttribute("pages", pages);
        model.addAttribute("current", page);
        model.addAttribute("next", page + 1);
        model.addAttribute("prev", page - 1);
        model.addAttribute("last", totalPages);
        List<UsuarioDTO> users = usuarioService.pagination(pageSize,offset);
        model.addAttribute("users",users);
        return "ListarUsuarios :: listaUsuarios";
    }

    /**
     *
     * @param id        el id del usuario a editar
     * @param model     el modelo utilizado para pasar datos a la vista
     * @return          fragmento para mostrar la información a editar
     */
    @GetMapping("/get-editar-form")
    public String getRegistrarUsuarioForm(@RequestParam(value="id") Integer id,
                                          @RequestParam(value="idRol") Integer idRol,Model model) {
        Optional<Usuario> usuario;
        // Verficar si usuario tiene un rol
        if(idRol != 0){
            usuario =  usuarioService.findRolById(idRol);

        }else{
            usuario =  usuarioService.findById(id);
            System.out.println(usuario);
        }
        model.addAttribute("usuario", usuario.get());
        model.addAttribute("carreras", usuarioService.listarCarreras());
        model.addAttribute("categorias", usuarioService.listarCategorias());
        model.addAttribute("estados", usuarioService.listarEstado());
        System.out.println(usuarioService.listarRoles());
        model.addAttribute("roles", usuarioService.listarRoles());
        return "fragments/usuario/editar-usuario-form :: editar-usuario-form";
    }

    /**
     * Método POST para editar un usuario.
     * @param usuario   usuario a editar
     * @return          html plano con un mensaje de estado
     */
    @PostMapping(value = "/editar",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.TEXT_HTML_VALUE)
    @HxTrigger("refresh")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CREATED)
    public String editarUsuario(Usuario usuario) {
        boolean editado = usuarioService.update(usuario);
        System.out.println(usuarioService.findIdRolById(usuario.getIdUsuario()));
        System.out.println(usuario);
        // Verificar si hubo un cambio de rol en el usuario
        if(usuario.getIdRol()==0){
            System.out.println("No hay cambios");
        }else{
            // En caso de que haya un cambio se verifica si no es el mismo rol que tiene el usuario
            if(!Objects.equals(usuario.getIdRol(), usuarioService.findIdRolById(usuario.getIdUsuario()))){
                //En caso de que no sea el mismo rol, se actuliza el Rol del usuario
                usuarioService.updateRol(usuario.getIdUsuario(), usuario.getIdRol());
            }else{
                System.out.println("No hay cambios");
            }
        }
        if (editado) {
            return "<div class='alert alert-success' role='alert'> La información fue actulizada con exito </div>";
        }else{
            return "<div class='alert alert-danger' role='alert'>Ha ocurrido un error al actualzar la información </div>";
        }
    }
}
