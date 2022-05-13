package com.company.hrsystem.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.dto.DocumentDto;

@Mapper
public interface DocumentMapper {
	
	int insertDocument(DocumentDto request);
	
	int updateDocument(DocumentDto request);
	
}
