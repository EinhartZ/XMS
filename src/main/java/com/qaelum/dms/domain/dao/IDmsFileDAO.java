package com.qaelum.dms.domain.dao;

import com.qaelum.dms.commons.dto.DmsFileDTO;
import com.qaelum.dms.commons.dto.S3FileDTO;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;

/**
 * Created by Einhart on 1/18/2018.
 * © QAELUM NV
 */
public interface IDmsFileDAO {

    boolean hasChildren(String schema, S3FileDTO s3FileDTO);

    Collection<S3FileDTO> findChildrenFiles(String schema, S3FileDTO s3FileDTO);

    String findVersionDataById(String schema, long fileId);

    String readWikiContent(String schema, String key_name);

    void writeWikiContent(String schema, S3FileDTO s3FileDTO, String content);
}
