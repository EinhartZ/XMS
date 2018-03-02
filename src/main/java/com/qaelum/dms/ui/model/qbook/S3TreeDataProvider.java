package com.qaelum.dms.ui.model.qbook;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.qaelum.dms.commons.dto.S3FileDTO;
import com.qaelum.dms.domain.dao.S3DAO;
import com.vaadin.data.provider.AbstractBackEndHierarchicalDataProvider;
import com.vaadin.data.provider.HierarchicalQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Einhart on 2/28/2018.
 * © QAELUM NV
 */
public class S3TreeDataProvider extends AbstractBackEndHierarchicalDataProvider<S3FileDTO, Object> {

    private final S3FileDTO root;

    public S3TreeDataProvider(S3FileDTO root) {
        this.root = root;
    }

    @Override
    protected Stream<S3FileDTO> fetchChildrenFromBackEnd(HierarchicalQuery<S3FileDTO, Object> hierarchicalQuery) {
        final S3FileDTO parent = hierarchicalQuery.getParentOptional().orElse(root);
        Collection<S3FileDTO> children = findChildrenFiles(parent);
        return children.stream();
    }

    @Override
    public int getChildCount(HierarchicalQuery<S3FileDTO, Object> hierarchicalQuery) {
        return (int) fetchChildren(hierarchicalQuery).count();
    }

    @Override
    public boolean hasChildren(S3FileDTO s3FileDTO) {
        return S3DAO.getInstance().hasChildren("", s3FileDTO);
    }

    private Collection<S3FileDTO> findChildrenFiles(S3FileDTO s3FileDTO) {
        return S3DAO.getInstance().findChildrenFiles("", s3FileDTO);
    }

}
