package com.qaelum.dms.commons.dto;


import com.qaelum.dms.ui.model.DmsFile;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Einhart on 1/15/2018.
 * © QAELUM NV
 */
public class DmsFileDTO implements IDmsFileDTO, Serializable, Comparable<DmsFileDTO>{

    private long fileId = 0;
    private DmsFile.FileType fileType;
    private String fileName;
    private String filePath;

    private String versionData;

    public DmsFileDTO(DmsFile.FileType fileType, String fileName) {
        this.fileType = fileType;
        this.fileName = fileName;
    }


    public void setFileId(long fileId) {
        this.fileId = fileId;
    }

    //GETTERS

    public long getFileId() {
        return fileId;
    }

    public DmsFile.FileType getFileType() {
        return fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    @Override
    public boolean isFolder() {
        if(this.fileType == DmsFile.FileType.FOLDER) {
            return true;
        }
        return false;
    }

    @Override
    public String getExtension() {
        switch (this.fileType) {
            case FOLDER:
                return "";
            case FILE:
                return "xml";
            default:
                return  "";
        }
    }

    /*
    public String getVersionData() {
        return versionData;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    */

    public void setVersionData(String versionData) {
        this.versionData = versionData;
    }

    @Override
    public String toString() {
        return fileName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileId, fileName, fileType);
    }

    @Override
    public boolean equals(Object obj) {
        if( obj == this) return true;
        if(!(obj instanceof DmsFileDTO)) {
            return false;
        }
        DmsFileDTO dmsFileDTO = (DmsFileDTO) obj;
        return fileId == dmsFileDTO.fileId &&
                Objects.equals(fileName, dmsFileDTO.fileName) &&
                Objects.equals(fileType, dmsFileDTO.fileType);
    }

    @Override
    public int compareTo(DmsFileDTO o) {
        Long long1 = new Long(fileId);
        Long long2 = new Long(o.fileId);
        return long1.compareTo(long2);
    }
}
