package com.qaelum.dms.commons.dto;

import org.apache.commons.io.FilenameUtils;

import java.io.File;

/**
 * Created by Einhart on 2/28/2018.
 * © QAELUM NV
 */
public class S3FileDTO implements IDmsFileDTO {

    private String filePath;
    private File file;

    public S3FileDTO(String filePath) {
        this.filePath = filePath;
        file = new File(filePath);
    }

    public String getFilePath() {
        return filePath;
    }

    public boolean isFolder() {
        return filePath.endsWith("/");
    }

    public String getExtension() {
        return FilenameUtils.getExtension(filePath).toLowerCase();
    }

    public String getFileName() {
            return file.getName();
    }


    @Override
    public String toString() {
        return "S3FileDTO{" +
                "filePath='" + filePath + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if(!(obj instanceof S3FileDTO)) return false;

        S3FileDTO s3FileDTO = (S3FileDTO) obj;
        return this.filePath.equals(s3FileDTO.getFilePath());
    }
}
