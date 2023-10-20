package com.uabc.fiad.sgs.controller;


import com.uabc.fiad.sgs.DTO.UsuarioDTO;
import com.uabc.fiad.sgs.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    /**
     *  Regresa la vista para admistrar cuentas y genera una paginacion con una lista de usuarios
     * @param model     el modelo utilizado para pasar datos a la vista
     * @return          La vista para administrar cuentas
     */
    @GetMapping("/administrarCuenta")
    public String listarUsuario(Model model) {

        int page = 1;
        int pageSize = 5;
        int offset = (page - 1) * pageSize;

        Integer totalRecords = usuarioService.TotalRecords();

        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        List<Integer> pages = IntStream.rangeClosed(1, totalPages).boxed().toList();

        model.addAttribute("pages", pages);
        model.addAttribute("current", page);
        model.addAttribute("next", page + 1);
        model.addAttribute("prev", page - 1);
        model.addAttribute("last", totalPages);
        List<UsuarioDTO> users = usuarioService.pagination(pageSize,offset);
        model.addAttribute("users",users);
        System.out.println(pages);
        System.out.println(page);
        System.out.println(totalPages);

        return "ListarUsuarios";
    }

    /**
     *
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
}
