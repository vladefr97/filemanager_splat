package com.vladefr97.filemanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;


import com.vladefr97.filemanager.entity.FileModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;
import java.nio.file.Paths;
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


    @GetMapping("rootFiles/")
    public String varIndex(@PathVariable String var) {
        File[] roots = File.listRoots();

        return "Hi!";
    }


    @RequestMapping("/getFile/{filePath}")
    public FileModel[] getChildFiles(@PathVariable String filePath) {
        System.out.println(filePath);
        if (filePath.contains("content")) {//returning root files
            File[] rootFiles = File.listRoots();
            rootFiles = rootFiles[0].listFiles();
            FileModel[] rootModels = new FileModel[rootFiles.length];
            for (int i = 0; i < rootModels.length; i++)
                rootModels[i] = new FileModel(rootFiles[i].getName(), rootFiles[i].getAbsolutePath(), rootFiles[i].isDirectory());

            if (rootModels != null)
                System.out.println("send");

            return rootModels;

        }
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
    public String getFileText(@PathVariable String filePath)  {

        filePath = filePath.replace("<prefix>", "/");
        String result = null;
        try {
            result = readUsingScanner(filePath);
        }
        catch (AccessDeniedException e){
            return e.toString();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return result;

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
