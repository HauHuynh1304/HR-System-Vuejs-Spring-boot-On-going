package com.company.hrsystem.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.company.hrsystem.dto.HistoryActionDto;

@Mapper
public interface HistoryActionMapper {
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	int insertHistoryAction(HistoryActionDto actionDto);
	
	List<HistoryActionDto> findHistoriesByEmployeeId(Integer id);

}