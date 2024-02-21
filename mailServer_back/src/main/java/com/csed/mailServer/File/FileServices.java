package com.csed.mailServer.File;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

import static java.lang.StringTemplate.STR;

@Service
public class FileServices {


    public boolean createFile( String user ,String name) throws IOException {
        File file = new File(STR."database/\{user}/\{name}.json");
        return file.createNewFile();
    }

    public boolean deleteFile(String user, String name) {
        File file = new File(STR."database/\{user}/\{name}.json");
        return file.delete();
    }

    public boolean editFile(String user, String oldName , String newName) {
        File file = new File(STR."database/\{user}/\{oldName}.json");
        File fileNew = new File(STR."database/\{user}/\{newName}.json");
       return  file.renameTo(fileNew);
    }
    public String[] getFileNames(String user) {
        File directory = new File(STR."database/\{user}");
        return directory.list();
    }

}
