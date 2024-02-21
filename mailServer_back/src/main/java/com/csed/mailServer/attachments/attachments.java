package com.csed.mailServer.attachments;

import lombok.Data;

@Data
public class attachments {
    private String fileName;
    private Long fileLength;
    private String contentType;
    private Boolean isReadable;
    private Boolean isFileEmpty;
    private byte[] fileData;
}
