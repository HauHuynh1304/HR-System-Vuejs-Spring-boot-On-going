package com.company.hrsystem.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.request.CommentRequest;

@Mapper
public interface CommentMapper {

	int insertComment(CommentRequest request);

}
