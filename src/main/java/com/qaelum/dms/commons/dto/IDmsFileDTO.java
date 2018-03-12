package com.qaelum.dms.commons.dto;

/**
 * Created by Einhart on 3/1/2018.
 * © QAELUM NV
 */
public interface IDmsFileDTO {
    public String getFileKey();

    public String getFilePath();
    public boolean isFolder();
    public String getFileName();
    public String getExtension();
}
