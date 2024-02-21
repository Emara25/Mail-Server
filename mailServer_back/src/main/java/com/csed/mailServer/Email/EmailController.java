package com.csed.mailServer.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(path = "email")

public class EmailController {
    EmailServices emailServices;

    @Autowired
    public EmailController(EmailServices emailServices) {
        this.emailServices = emailServices;

    }

    @PostMapping
    public boolean sendEmail(@RequestBody Email email) {
        return emailServices.sendMail(email);
    }

    @PostMapping(value = "draft")
    public boolean saveAsDraft(@RequestBody Email email) {
        return emailServices.saveAsDraft(email);
    }

    @PutMapping
    public boolean copyAtFile(@RequestBody Map<String, String> request) {
        String user = request.get("user");
        String source = request.get("source");
        String id = request.get("id");
        String name = request.get("file");
        return emailServices.copyMail(user, source, id, name);
    }

    @GetMapping
    public List<Email> getMails(@RequestParam(value = "user") String email,
                                @RequestParam(value = "type") String type) {
        return emailServices.getMails(email, type);
    }

    @DeleteMapping
    public void delete(@RequestParam(value = "user") String user,
                       @RequestParam(value = "file") String file, @RequestParam(value = "id") String id) {
        this.emailServices.deleteMail(user, file, id);
    }

    @GetMapping(value = "search")
    public List search(@RequestParam(value = "file") String folderName,
                       @RequestParam(value = "email") String email,@RequestParam(value = "criteria") String word) throws IOException {
        return emailServices.search(folderName, email, word);
    }

    @GetMapping(value = "filter")
    public List filter(@RequestParam(value = "email") String userEmail,
                       @RequestParam(value = "file") String currentFolder,
                       @RequestParam(value = "criteria") String word,
                       @RequestParam(value = "choice") String choice)throws IOException{
        return emailServices.filter(userEmail, currentFolder, word, choice);
    }

    @GetMapping(value = "sortByPrio")
    public List sortByPriority(@RequestParam(value = "file") String folderName,
                               @RequestParam(value = "email") String email,
                               @RequestParam(value = "sortType") String sortType)throws IOException{
        return emailServices.sortByPrio(folderName, email, sortType);
    }

    @GetMapping(value = "sortByDate")
    public List sortByDate(@RequestParam(value = "file") String folderName,
                               @RequestParam(value = "email") String email,
                               @RequestParam(value = "sortType") String sortType)throws IOException{
        if(sortType.equals("descending"))
            return emailServices.getMails(email, folderName);
        else{
            return emailServices.getMails(email, folderName).reversed();
        }
    }

    @PutMapping(value = "changePriority")
    public List<Email> changePriority(@RequestBody Map<String, Object> payload) {
        String folderName = (String) payload.get("file");
        String userEmail = (String) payload.get("email");
        String emailID = (String) payload.get("id");
        Integer priority = (Integer) payload.get("priority");

        // Rest of your logic
        return emailServices.changePriority(folderName, userEmail, emailID, priority);
    }



}