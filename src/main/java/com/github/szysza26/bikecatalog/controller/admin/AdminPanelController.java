package com.github.szysza26.bikecatalog.controller.admin;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPanelController {

    @GetMapping("/admin")
    public String index(Model model, Authentication authentication) {
        model.addAttribute("authentication", authentication);
        return "admin/index";
    }

}
