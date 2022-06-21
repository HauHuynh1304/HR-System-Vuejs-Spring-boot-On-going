package com.company.hrsystem.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.dto.DocumentDto;

@Mapper
public interface DocumentMapper {
	
	int insertDocument(DocumentDto request);
	
	int updateDocument(DocumentDto request);
	
	List<DocumentDto> findAllDocuments();
	
}
