package com.csed.mailServer.attachments;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
public class attachmentsController {
    @PostMapping(value = "/attachment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public attachments getAttachment(@RequestParam(value = "file") MultipartFile file) throws IOException {
        attachments attachment = new attachments();

        attachment.setFileName(file.getOriginalFilename());
        attachment.setContentType(file.getContentType());
        attachment.setFileLength(file.getSize());
        attachment.setIsFileEmpty(file.isEmpty());
        attachment.setIsReadable(file.getResource().isReadable());
        attachment.setFileData(file.getBytes());

        return attachment;
    }
}
