package com.csed.mailServer.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(path = "file")


public class FileController {
    FileServices folderService;

    @Autowired

    public FileController(FileServices folderService) {
        this.folderService = folderService;
    }

    @PostMapping
    public boolean createFile(@RequestBody Map<String, String> request) throws IOException {
        String email = request.get("email");
        String name = request.get("name");
        return folderService.createFile(email, name);
    }

    @DeleteMapping
    public boolean deleteFile(@RequestParam(value = "email") String email,
                              @RequestParam(value = "name") String name) {
        return folderService.deleteFile(email, name);
    }

    @PutMapping
    public boolean editFile(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String oldName = request.get("oldName");
        String newName = request.get("newName");
        return folderService.editFile(email, oldName, newName);
    }

}
