package com.company.hrsystem.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.dto.RequesterActionDto;

@Mapper
public interface RequesterActionMapper {

	int insertRequesterAction(RequesterActionDto requestAction);

	int updateRequesterAction(RequesterActionDto requestAction);

}
