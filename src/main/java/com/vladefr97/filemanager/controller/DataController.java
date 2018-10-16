package com.vladefr97.filemanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;


import com.vladefr97.filemanager.entity.FileModel;
import com.vladefr97.filemanager.entity.Message;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
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

    @RequestMapping(value = "/renameFile", method = RequestMethod.GET)
    public Message renameFile(@RequestParam("directory") String directoryName, @RequestParam("oldFileName") String oldFileName, @RequestParam("newFileName") String newFileName) {
        File file = new File(oldFileName);
        newFileName = directoryName + "/" + newFileName;
        boolean done = file.renameTo(new File(newFileName));
        if (done)
            return new Message("Файл успешно переименован", true);
        else
            return new Message("Не удалось переименовать файл...", false);
    }

    @RequestMapping(value = "/createFile", method = RequestMethod.POST)
    public Message createFile(@RequestParam("directory") String directoryName, @RequestParam("fileName") String fileName, @RequestParam("isFile") boolean isFile) {
        String filePath = directoryName + "/" + fileName;
        File file = new File(filePath);
        boolean done;
        if (isFile) {
            try {
                done = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return new Message(e.getMessage(), false);
            }
        } else {
            done = file.mkdir();
        }
        System.out.println(directoryName);
        System.out.println(fileName);
        if (done)
            return new Message("Файл успешно создан!", true);
        else return new Message("Не удалось создать файл", false);
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

    @RequestMapping(value = "/copyFile", method = RequestMethod.GET)
    public Message copyFile(@RequestParam("targetFile") String targetPath, @RequestParam("sourceFile") String sourcePath) {

        File target = new File(targetPath);
        File source = new File(sourcePath);
        if (!target.isDirectory())
            return new Message("Нельзя копировать в файл...", false);
        else {
            try {
                copy(target, source);
            } catch (Exception e) {
                e.printStackTrace();
                return new Message(e.toString(), false);
            }
        }
        return new Message("Файл удачно скопирован...", true);

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

    private static void copy(File target, File source) throws IOException, AccessDeniedException {

        File copyFile = new File(target.getAbsolutePath() + "/" + source.getName());
        if (source.isDirectory()) {
            copyFile.mkdir();
            for (File child : Objects.requireNonNull(source.listFiles()))
                copy(copyFile, child);

        } else {
            copyFile.createNewFile();
            FileCopyUtils.copy(source, copyFile);
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
