package com.company.hrsystem.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.dto.CommentDto;

@Mapper
public interface CommentMapper {

	int insertComment(CommentDto request);

	List<CommentDto> findListComment(Integer requestId, Integer employeeId);

}
