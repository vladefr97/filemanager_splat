package com.vladefr97.filemanager.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;

@Controller
public class MainController {


    public static void main(String[] args) {
        File[] roots = File.listRoots();
        for (File file : roots) {
            File dir = new File(file.getAbsolutePath());
            File[] subfiles = dir.listFiles();

            for (File item : subfiles)

                System.out.println(item.getAbsolutePath());
        }

    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    public String index(Model model) {

        File[] roots = File.listRoots();
        model.addAttribute("files", roots);
        return "index";
    }


}
