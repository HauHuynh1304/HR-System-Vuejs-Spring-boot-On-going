package com.company.hrsystem.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.request.DocumentRequest;

@Mapper
public interface DocumentMapper {
	
	int insertDocument(DocumentRequest request);
	int updateDocument(DocumentRequest request);
	
}
