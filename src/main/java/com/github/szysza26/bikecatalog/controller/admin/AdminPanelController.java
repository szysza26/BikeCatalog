package com.github.szysza26.bikecatalog.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPanelController {

    @GetMapping("/admin")
    public String index() {
        return "admin/index";
    }

}
