package com.company.hrsystem.mapper.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.company.hrsystem.annotations.MapperImpl;
import com.company.hrsystem.dto.DocumentDto;
import com.company.hrsystem.mapper.DocumentMapper;

@MapperImpl
public class DocumentMapperImpl {

	@Autowired
	private DocumentMapper documentMapper;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public int insertDocument(DocumentDto request) {
		return documentMapper.insertDocument(request);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public int updateDocument(DocumentDto request) {
		return documentMapper.updateDocument(request);
	}

	public List<DocumentDto> findDocuments() {
		return documentMapper.findDocuments();
	}

	public List<DocumentDto> findAllDocuments() {
		return documentMapper.findAllDocuments();
	}
}
