package com.go.basetool.commonreq;

import lombok.Data;

import javax.persistence.criteria.CriteriaBuilder;

@Data
public class PageReq {
    Integer page;
    Integer pageSize;
}
