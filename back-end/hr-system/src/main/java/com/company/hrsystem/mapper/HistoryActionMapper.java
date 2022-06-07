package com.company.hrsystem.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.dto.HistoryActionDto;

@Mapper
public interface HistoryActionMapper {

	int insertHistoryAction(HistoryActionDto actionDto);
	
	List<HistoryActionDto> findHistoriesByEmployeeId(Integer id);

}