package com.company.hrsystem.mapper.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.company.hrsystem.annotations.MapperImpl;
import com.company.hrsystem.dto.CommentDto;
import com.company.hrsystem.mapper.CommentMapper;

@MapperImpl
public class CommentMapperImpl {

	@Autowired
	private CommentMapper commentMapper;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public int insertComment(CommentDto request) {
		return commentMapper.insertComment(request);
	}

	public List<CommentDto> findListComment(Integer requestId, Integer employeeId) {
		return commentMapper.findListComment(requestId, employeeId);
	}
	
}
