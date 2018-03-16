package com.qaelum.dms.domain.dao;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.util.IOUtils;
import com.qaelum.dms.commons.dto.S3FileDTO;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Einhart on 3/2/2018.
 * © QAELUM NV
 */
public class S3DAO implements IDmsFileDAO{
    private static S3DAO instance;

    private final String REGION = "eu-west-1";
    private final String DELIMITER = "/";
    private final String BUCKET_NAME = "qaelum.dms.test";
    final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(REGION).build();;

    public static S3DAO getInstance() {
        if(instance == null) {
            instance = new S3DAO();
        }
        return instance;
    }

    @Override
    public boolean fileExist(String filePath) {
        return s3.doesObjectExist(BUCKET_NAME, filePath);
    }

    @Override
    public boolean hasChildren(String schema, S3FileDTO s3FileDTO) {
        return hasChildren(s3FileDTO.getFilePath());
    }

    private boolean hasChildren(String filePath) {
        ListObjectsV2Request request = new ListObjectsV2Request().withBucketName(BUCKET_NAME).withPrefix(filePath).withDelimiter(DELIMITER);
        ListObjectsV2Result result = s3.listObjectsV2(request);
        List<String> folders = result.getCommonPrefixes();
        List<S3ObjectSummary> files = result.getObjectSummaries();

        for(S3ObjectSummary file: files) {
            if(!file.getKey().equals(filePath)) {
                return true;
            }
        }

        if(!folders.isEmpty()) {
            return true;
        }

        return false;
    }

    @Override
    public Collection<S3FileDTO> findChildrenFiles(String schema, S3FileDTO s3FileDTO) {
        return findChildren(s3FileDTO.getFilePath());
    }

    private  Collection<S3FileDTO> findChildren(String filePath) {
        Collection<S3FileDTO> children = new ArrayList<>();

        ListObjectsV2Request request = new ListObjectsV2Request().withBucketName(BUCKET_NAME).withPrefix(filePath).withDelimiter(DELIMITER);
        ListObjectsV2Result result = s3.listObjectsV2(request);
        List<String> folders = result.getCommonPrefixes();
        List<S3ObjectSummary> files = result.getObjectSummaries();

        for(S3ObjectSummary file: files) {
            if(!file.getKey().equals(filePath)) {
                S3FileDTO childFile = new S3FileDTO(file.getKey());
                children.add(childFile);
            }
        }

        for(String folder: folders) {
            S3FileDTO childFolder = new S3FileDTO(folder);
            children.add(childFolder);
        }

        return children;
    }

    @Override
    public String findVersionDataById(String schema, long fileId) {
        return null;
    }

    @Override
    public String readWikiContent(String schema, String key_name) {
        S3Object o = s3.getObject(BUCKET_NAME, key_name);
        StringBuilder stringBuilder = new StringBuilder();

        try (S3ObjectInputStream s3is = o.getObjectContent();
             BufferedReader reader = new BufferedReader(new InputStreamReader(s3is));
        ) {
            String line;
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append('\n');
            }
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return stringBuilder.toString();
    }

    @Override
    public void writeWikiContent(String schema, S3FileDTO s3FileDTO, String content) {
        System.out.println("Writing to S3...");
        System.out.println(content);

        /*
        * Obtain the Content length of the Input stream for S3 header
        */
        byte[] contentBytes = new byte[0];
        try (InputStream is = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8))) {
            contentBytes = IOUtils.toByteArray(is);
        } catch (IOException e) {
            System.err.printf("Failed while reading bytes from %content", e.getMessage());
        }

        Long contentLength = Long.valueOf(contentBytes.length);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(contentLength);

        /*
        * Reobtain the tmp uploaded file as input stream
        */
        try (InputStream is = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8))){
            /*
            * Put the object in S3
            */
            s3.putObject(new PutObjectRequest(BUCKET_NAME, s3FileDTO.getFilePath(), is, metadata));
        } catch (AmazonServiceException ase) {
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Error Message: " + ace.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createFile(String filePath) {
        createFile(BUCKET_NAME, filePath, s3);
    }

    private void createFile(String bucketName, String filePath, AmazonS3 client) {
        // create meta-data for your folder and set content-length to 0
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(0);
        // create empty content
        InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
        // create a PutObjectRequest passing the folder name suffixed by /
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,
                filePath, emptyContent, metadata);
        // send request to S3 to create folder
        client.putObject(putObjectRequest);
    }


    @Override
    public void removeFile(S3FileDTO s3FileDTO) {
        removeFile(s3FileDTO.getFilePath());
    }

    private void removeFile(String filePath) {
        s3.deleteObject(BUCKET_NAME, filePath);
    }

    public void removeFolderRecursive(S3FileDTO s3fileDTO) {
        removeFolderRecursive(s3fileDTO.getFilePath());
    }

    private void removeFolderRecursive(String filePath) {
        for(S3FileDTO childFile : findChildren(filePath)) {
           removeFolderRecursive(childFile.getFilePath());
        }

        removeFile(filePath);
    }


    @Override
    public Upload uploadFileAsync(String filePath, InputStream inputStream) {
        Upload xfer = null;
        TransferManager xfer_mgr = TransferManagerBuilder.standard().build();
        try {
//            xfer = xfer_mgr.upload(BUCKET_NAME, filePath, file);
            xfer = xfer_mgr.upload(new PutObjectRequest(BUCKET_NAME, filePath, inputStream, new ObjectMetadata()));
            XferMgrProgress.showTransferProgress(xfer);
            XferMgrProgress.waitForCompletion(xfer);

        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
        }

        return xfer;
    }
}
