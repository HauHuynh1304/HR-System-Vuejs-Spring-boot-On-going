package com.company.hrsystem.mapper.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.company.hrsystem.annotations.MapperImpl;
import com.company.hrsystem.dto.HistoryActionDto;
import com.company.hrsystem.mapper.HistoryActionMapper;

@MapperImpl
public class HistoryActionMapperImpl {

	@Autowired
	private HistoryActionMapper historyActionMapper;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	public void insertHistoryAction(HistoryActionDto actionDto) {
		historyActionMapper.insertHistoryAction(actionDto);
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void insertHistoryActionNoTransaction(HistoryActionDto actionDto) {
		historyActionMapper.insertHistoryAction(actionDto);
	}
	
}
