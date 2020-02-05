package com.epe.demo.adplatform.domain.rds.common;

import org.springframework.data.domain.Persistable;

import java.io.Serializable;

public interface MetadataAware<ID extends Serializable> extends Persistable<ID> {

    public Metadata getMetadata();
    public void setMetadata(Metadata metadata);

}
