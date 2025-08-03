package br.com.unnamed.demo.presentation.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("home")
public class HomeController {

    @GetMapping
    public String getMethodName(ModelMap model) {

        model.addAttribute("view", "layout/home");
        model.addAttribute("activePage", "home");
        return new String("layout/base-layout");

    }

}
