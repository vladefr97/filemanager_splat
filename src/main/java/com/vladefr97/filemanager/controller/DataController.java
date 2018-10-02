package com.vladefr97.filemanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;


import com.vladefr97.filemanager.entity.FileModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

@RestController
public class DataController {

    public static void main(String[] args) {

      /*  File[] roots = File.listRoots();

       Date start = new Date();
        System.out.println("Start: " + start);

        File[] subfiles = roots[0].listFiles();
        FileModel rootFile = new FileModel(roots[0].getName(), roots[0].getAbsolutePath(), roots[0].isDirectory(), subfiles.length);

        for(int i=0;i<subfiles.length;i++){
            FileModel.addNode(rootFile.getChildFile(i),rootFile,i,subfiles[i]);
        }

        System.out.println("Start:"+ start);
        System.out.println("Finish:" + new Date());*/

    }


    @RequestMapping("/rootFiles")
    public FileModel[] rootFiles() {
        File[] roots = File.listRoots()[0].listFiles();
        FileModel[] models = new FileModel[roots.length];

        for (int i = 0; i < models.length; i++)
            models[i] = new FileModel(roots[i].getName(), roots[i].getAbsolutePath(), roots[i].isDirectory());
        return models;


    }


    @RequestMapping("/getFile/{filePath}")
    public FileModel[] getChildFiles(@PathVariable String filePath) {
        System.out.println(filePath);
        filePath = filePath.replace("<prefix>", "/");

        System.out.println(filePath);
        File file = new File("/" + filePath);


        File[] resultFiles = file.listFiles();
        if (resultFiles == null) return null;
        FileModel[] fileModels = new FileModel[resultFiles.length];

        for (int i = 0; i < fileModels.length; i++)
            fileModels[i] = new FileModel(resultFiles[i].getName(), resultFiles[i].getAbsolutePath(), resultFiles[i].isDirectory());
        return fileModels;
    }

    @RequestMapping("/getFileText/{filePath}")
    public String getFileText(@PathVariable String filePath) throws IOException {

        filePath = filePath.replace("<prefix>", "/");
        Desktop desktop = null;
        if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
            desktop.open(new File(filePath));
            return "";
        } else {

            String result = null;
            try {
                result = readUsingScanner(filePath);
            } catch (AccessDeniedException e) {
                return e.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }


    }

    private static String readUsingScanner(String fileName) throws IOException {
        Scanner scanner = new Scanner(Paths.get(fileName), StandardCharsets.UTF_8.name());
        //здесь мы можем использовать разделитель, например: "\\A", "\\Z" или "\\z"
        String data = scanner.useDelimiter("\\A").next();
        scanner.close();
        return data;
    }

   /* @RequestMapping("/getFiles")
    public FileModel getFiles() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        File[] roots = File.listRoots();

        Date start = new Date();
        System.out.println("Start: " + start);

        File[] subfiles = roots[0].listFiles();
        FileModel rootFile = new FileModel(roots[0].getName(), roots[0].getAbsolutePath(), roots[0].isDirectory(), subfiles.length);

        for(int i=0;i<subfiles.length;i++){
            FileModel.addNode(rootFile.getChildFile(i),rootFile,i,subfiles[i]);
        }

        System.out.println("Start:"+ start);
        System.out.println("Finish:" + new Date());
*//*
        StringWriter writer = new StringWriter();

        mapper.writeValue(writer, rootFile);
        String result = writer.toString();*//*


        return rootFile;

    }*/
}
