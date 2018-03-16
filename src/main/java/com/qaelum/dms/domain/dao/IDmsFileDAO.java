package com.qaelum.dms.domain.dao;

import com.amazonaws.services.s3.transfer.Upload;
import com.qaelum.dms.commons.dto.S3FileDTO;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;

/**
 * Created by Einhart on 1/18/2018.
 * © QAELUM NV
 */
public interface IDmsFileDAO {

    boolean fileExist(String filePath);

    boolean hasChildren(String schema, S3FileDTO s3FileDTO);

    Collection<S3FileDTO> findChildrenFiles(String schema, S3FileDTO s3FileDTO);

    String findVersionDataById(String schema, long fileId);

    String readWikiContent(String schema, String key_name);

    void writeWikiContent(String schema, S3FileDTO s3FileDTO, String content);

    void createFile(String filePath);

    void removeFile(S3FileDTO s3FileDTO);

    void removeFolderRecursive(S3FileDTO s3fileDTO);

//    void removeFolderRecursive(String filePath);

    Upload uploadFileAsync(String filePath, InputStream inputStream);
}
