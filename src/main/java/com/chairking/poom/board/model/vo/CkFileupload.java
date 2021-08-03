package com.chairking.poom.board.model.vo;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.MediaType;

import java.io.File;
import java.nio.file.Paths;

public class CkFileupload {

    public static MediaType getMediatype(String fileName) {
        String contentType = FilenameUtils.getExtension(fileName);
        MediaType mediaType = null;

        if (contentType.equals("png")) {
            mediaType = mediaType.IMAGE_PNG;
        } else if (contentType.equals("jpeg")) {
            mediaType = mediaType.IMAGE_JPEG;
        } else if (contentType.equals("gif")) {
            mediaType = mediaType.IMAGE_GIF;
        }

        return mediaType;
    }

    public static File getDownloadFile(String fileName, String folderpath){

        return new File(folderpath, fileName ) ;
    }
}
