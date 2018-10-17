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
import java.util.*;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class DataController {
    private static Logger log = Logger.getLogger(DataController.class.getName());

    public static void main(String[] args) {

    }


    /*Getting root files on the computer*/
    @RequestMapping("/rootFiles")
    public FileModel[] rootFiles() {
        File[] roots = File.listRoots()[0].listFiles();
        FileModel[] models = new FileModel[roots.length];

        for (int i = 0; i < models.length; i++)
            models[i] = new FileModel(roots[i].getName(), roots[i].getAbsolutePath(), roots[i].isDirectory());
        return models;


    }

    /*Getting subfiles of the selected file*/
    @RequestMapping(value = "/getNodeFiles", method = RequestMethod.GET)
    public FileModel[] getNodeFiles(@RequestParam("filePath") String filePath) {

        File file = new File("/" + filePath);


        File[] resultFiles = file.listFiles();
        if (resultFiles == null) return null;
        FileModel[] fileModels = new FileModel[resultFiles.length];

        for (int i = 0; i < fileModels.length; i++)
            fileModels[i] = new FileModel(resultFiles[i].getName(), resultFiles[i].getAbsolutePath(), resultFiles[i].isDirectory());
        return fileModels;
    }

    /*Rename file */
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

    /*Delete file*/
    @RequestMapping(value = "/deleteFile", method = RequestMethod.GET)
    public Message renameFile(@RequestParam("filePath") String FilePath) {
        ArrayList<Boolean> results = new ArrayList<>();
        try {
            File file = new File(FilePath);
            results = deleteFile(file, results);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message("Не удалось удалить файл...", false);
        }

        if (!results.contains(true))
            return new Message("Не удалось удалить файл...", false);
        else if (results.contains(false))
            return new Message("Не все файлы были удалены...", true);
        else
            return new Message("Файл успешно удален...", true);

    }


    /*Create file*/
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
        log.info("opened " + fileName);
        if (done)
            return new Message("Файл успешно создан!", true);
        else return new Message("Не удалось создать файл", false);
    }


    /*Copy file to another directory with all subfiles*/
    @RequestMapping(value = "/copyFile", method = RequestMethod.GET)
    public Message copyFile(@RequestParam("targetFile") String targetPath, @RequestParam("sourceFile") String sourcePath) {
        File target = new File(targetPath);
        File source = new File(sourcePath);
        log.info("Started copying...");
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
        log.info("Finished copying...");
        return new Message("Файл удачно скопирован...", true);

    }


    /*Get the content of the file*/
    @RequestMapping("/getTextFile")
    public String getTextFile(@RequestParam("filePath") String filePath) throws IOException {

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


    /*Recursive deleting of all subfiles*/
    private static ArrayList<Boolean> deleteFile(File file, ArrayList<Boolean> results) {
        File[] childDirs = file.listFiles();
        if (file.isDirectory() && (childDirs.length != 0)) {
            for (File item : childDirs)
                results.addAll(deleteFile(item, results));
            results.add(file.delete());
        } else results.add(file.delete());
        return results;
    }

    /*Recursive copying of all subfiles*/
    private static void copy(File target, File source) throws IOException, AccessDeniedException {

        log.info("copying " + source.getAbsolutePath());
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

    /*Reading the content of the file*/
    private static String readUsingScanner(String fileName) throws IOException {
        Scanner scanner = new Scanner(Paths.get(fileName), StandardCharsets.UTF_8.name());
        //здесь мы можем использовать разделитель, например: "\\A", "\\Z" или "\\z"
        String data = scanner.useDelimiter("\\A").next();
        scanner.close();
        return data;
    }

}
