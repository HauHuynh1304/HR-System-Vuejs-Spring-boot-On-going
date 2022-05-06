package com.company.hrsystem.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.model.RequestModel;

@Mapper
public interface RequestMapper {
	
	int insertRequest (RequestModel requestModel);
	
}
