package com.company.hrsystem.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.dto.CommentDto;

@Mapper
public interface CommentMapper {

	int insertComment(CommentDto request);

}
