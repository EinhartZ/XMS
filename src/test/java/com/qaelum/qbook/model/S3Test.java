package com.qaelum.qbook.model;

import com.qaelum.dms.domain.dao.S3DAO;
import junit.framework.TestCase;

/**
 * Created by Einhart on 2/28/2018.
 * © QAELUM NV
 */
public class S3Test extends TestCase{
    protected S3DAO s3DAO;
    protected String fileKey1 = "wiki/2018_02_27/wiki_001.xml";
    protected String fileKey2 = "root.txt";

    protected void setUp() {
        s3DAO = S3DAO.getInstance();
    }

    public void testS3ReadFile() {

        System.out.println(fileKey1);
        System.out.println(s3DAO.readWikiContent("", fileKey1));

        System.out.println(fileKey2);
        System.out.println(s3DAO.readWikiContent("", fileKey2));
    }
}
