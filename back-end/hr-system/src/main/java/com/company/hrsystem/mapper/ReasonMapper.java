package com.company.hrsystem.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.dto.ReasonDto;

@Mapper
public interface ReasonMapper {

	int insertReason(ReasonDto request);

	int updateReason(ReasonDto request);

}
