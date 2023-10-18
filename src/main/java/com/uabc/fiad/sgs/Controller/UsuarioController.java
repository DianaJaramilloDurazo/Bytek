package com.uabc.fiad.sgs.Controller;


import com.uabc.fiad.sgs.DTO.UsuarioDTO;
import com.uabc.fiad.sgs.Service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/lista")
    public String listarUsuario(Model model) {
        Integer id = 1;
        Optional<UsuarioDTO> user = usuarioService.findById(id);
        List<UsuarioDTO> users = usuarioService.findAll();
        model.addAttribute("users",users);
        model.addAttribute("user",user.get());
        return "ListarUsuarios";
    }
}
