package br.com.unnamed.demo.domain.serviceExecution.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.unnamed.demo.domain.serviceExecution.service.ServiceExecutionService;

@Controller
@RequestMapping("serviceExecution")
public class ServiceExecutionController {

    private ServiceExecutionService service;

    public ServiceExecutionController() {

    }

    @GetMapping
    public String showServiceExecutionBoard(Model model) {

        model.addAttribute("activePage", "serviceExecution");
        model.addAttribute("view", "serviceExecution/serviceExecution");
        return "layout/base-layout";

    }

}
