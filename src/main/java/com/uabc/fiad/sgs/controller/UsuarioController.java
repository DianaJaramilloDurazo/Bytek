package com.uabc.fiad.sgs.controller;


import com.uabc.fiad.sgs.DTO.UsuarioDTO;
import com.uabc.fiad.sgs.entity.Filtros;
import com.uabc.fiad.sgs.entity.Usuario;
import com.uabc.fiad.sgs.service.IUsuarioService;
import com.uabc.fiad.sgs.utils.SessionUtils;
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
     *
     * @param id        el id del usuario a editar
     * @param model     el modelo utilizado para pasar datos a la vista
     * @param perfil    bandera usada para entregar el formulario adecuado cuando se quiere editar el usuario en el modal
     *                  de perfil
     * @return          fragmento para mostrar la información a editar
     */
    @GetMapping("/get-editar-form")
    public String getRegistrarUsuarioForm(@RequestParam(value="id") Integer id,
                                          @RequestParam(value="idRol") Integer idRol,
                                          @RequestParam(value = "perfil", required = false) Boolean perfil,
                                          Model model) {

        Optional<Usuario> usuario;
        // Verficar si usuario tiene un rol
        if(idRol != 0){

            usuario =  usuarioService.findRolById(idRol);

        }else{
            usuario =  usuarioService.findById(id);
            // Se verifica si el Usuario tiene un rol, esto para no permitir que se le cambie el estado a inactivo
            Integer rol = usuarioService.findIdRolById(usuario.get().getIdUsuario());
            usuario.get().setIdRol(rol);
        }
        model.addAttribute("usuario", usuario.get());
        model.addAttribute("carreras", usuarioService.listarCarreras());
        model.addAttribute("categorias", usuarioService.listarCategorias());
        model.addAttribute("estados", usuarioService.listarEstado());
        model.addAttribute("roles", usuarioService.listarRoles());

        // Simplemente entrega la bandera de perfil
        model.addAttribute("perfil", perfil);

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
        String resultado = "";
        //Validar si no hay campos vacios
        Optional<Usuario> user = usuarioService.findById(usuario.getIdUsuario());

        if(usuario.getUsername().isEmpty()){
            usuario.setUsername(user.get().getUsername());
        }
        if(usuario.getApPaterno().isEmpty()){
            usuario.setApPaterno(user.get().getApPaterno());
        }
        if(usuario.getIdEstado() == null){
            usuario.setIdEstado(1);
        }
        boolean editado = usuarioService.update(usuario);
//        System.out.println(usuarioService.findIdRolById(usuario.getIdUsuario()));
//        System.out.println(usuario);
        // Verificar si hubo un cambio de rol en el usuario
        if(!(usuario.getIdRol() == null || usuario.getIdRol()==0)){
            // En caso de que haya un cambio se verifica si no es el mismo rol que tiene el usuario
            if(!Objects.equals(usuario.getIdRol(), usuarioService.findIdRolById(usuario.getIdUsuario()))){
                //Verificar si el usuario esta activo
                if(usuario.getIdEstado() != 2){
                    //En caso de que no sea el mismo rol, se actuliza el Rol del usuario
                    if(usuarioService.updateRol(usuario.getIdUsuario(), usuario.getIdRol())){
                        resultado+="<div class='alert alert-success' role='alert'> El rol fue actualizado con exito </div>";
                    }else{
                        resultado+= "<div class='alert alert-danger' role='alert'>Ha ocurrido un error al actualizar el rol </div>";
                    }
                }else{
                    resultado+= "<div class='alert alert-danger' role='alert'>Ha ocurrido un error al actualizar el rol, el usuario esta inactivo </div>";
                }


            }
        }
        if (editado) {
            resultado+= "<div class='alert alert-success' role='alert'> La información fue actualizada con exito </div>";
        }else{
            resultado+= "<div class='alert alert-danger' role='alert'>Ha ocurrido un error al actualizar la información </div>";
        }
        return resultado;
    }

    /**
     * Obtener el perfil del usuario basado en la sesión
     * @param model el modelo
     * @return      el fragmento para la vista del perfil de usuario
     */
    @GetMapping(value = "/perfil")
    public String getPerfil(Model model) {

        // Obtener el usuario de la sesión
        Usuario u = SessionUtils.getUsuario(usuarioService);



        // Si no existe redirigir al login
        if (u == null) {
            return "redirect:/login";
        }

        // Si sí existe, proceder a entregar la vista con los datos de usuario
        model.addAttribute("usuario", u);
        model.addAttribute("carrera", usuarioService.listarCarreras().get(u.getIdCarrera() - 1));
        model.addAttribute("categoria", usuarioService.listarCategorias().get(u.getIdCategoria() - 1));
        model.addAttribute("estado", usuarioService.listarEstado().get(u.getIdEstado() - 1));
        model.addAttribute("rol", "docente");

        return "fragments/usuario/perfil-usuario :: perfil-usuario";
    }

    /**
     * Método para obtener el formulario para editar la información en el perfil
     * @param model el modelo
     * @return      el fragmento del formulario para editar usuario, con la información de la sesión
     */
    @GetMapping("/perfil/editar")
    public String GetEditarPerfil(Model model) {
        // Obtener el usuario de la sesión
        Usuario u = SessionUtils.getUsuario(usuarioService);

        // Si no existe redirigir al login
        if (u == null) {
            return "redirect:/login";
        }

        // Si sí existe, proceder a entregar la vista con los datos de usuario
        // Se usa la misma vista que el formulario de editar usuario, con los datos de la sesión.
        // el rol se manda como 0 para que no se edite
        return getRegistrarUsuarioForm(u.getIdUsuario(), 0, true, model);
    }

    /**
     * Método para editar la información del perfil de usuario. Si el usuario entregado no coincide con el de la sesión
     * (sus ids son diferentes) no permite la edición
     * @param usuario   la información nueva del usuario
     * @return          html plano con mensaje de estado
     */
    @PostMapping(value = "/perfil/editar",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.TEXT_HTML_VALUE)
    @HxTrigger("refresh")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CREATED)
    public String editarPerfil(Usuario usuario) {

        // Revisar que el usuario a editar sea el mismo que tiene iniciada la sesión
        // Obtener el usuario de la sesión
        Usuario u = SessionUtils.getUsuario(usuarioService);

        // Si no existe redirigir al login
        if (u == null) {
            return "redirect:/login";
        }

        // Si sí existe, revisar que sea el mismo a editar
        if (!Objects.equals(u.getIdUsuario(), usuario.getIdUsuario())) {

            // Si no es el mismo, regresar el error
            return "<div class='alert alert-danger' role='alert'>El usuario que intentó editar no es el suyo </div>";
        }

        // Si sí es el mismo, editarlo y regresar el resultado del método acorde
        return editarUsuario(usuario);
    }
}
