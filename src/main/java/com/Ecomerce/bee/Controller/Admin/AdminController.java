package com.Ecomerce.bee.Controller.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping({"/Admin"})
    public String admin(){
        return "Admin/index";
    }

    @GetMapping({"/ui-buttons"})
    public String button(){
        return "Admin/ui-buttons";
    }

    @GetMapping({"/ui-alerts"})
    public String alerts(){
        return "Admin/ui-alerts";
    }

    @GetMapping({"/ui-card"})
    public String card(){
        return "Admin/ui-card";
    }

    @GetMapping({"/ui-forms"})
    public String forms(){
        return "Admin/ui-forms";
    }

    @GetMapping({"/ui-typography"})
    public String typography(){
        return "Admin/ui-typography";
    }


    @GetMapping({"/icon-tabler"})
    public String tabler(){
        return "Admin/icon-tabler";
    }

    @GetMapping({"/sample-page"})
    public String page(){
        return "Admin/sample-page";
    }

}
