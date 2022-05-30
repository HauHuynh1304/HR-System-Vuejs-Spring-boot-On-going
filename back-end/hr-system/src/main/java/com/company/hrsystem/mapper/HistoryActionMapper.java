package com.company.hrsystem.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.dto.HistoryActionDto;

@Mapper
public interface HistoryActionMapper {

	int insertHistoryAction(HistoryActionDto actionDto);

}