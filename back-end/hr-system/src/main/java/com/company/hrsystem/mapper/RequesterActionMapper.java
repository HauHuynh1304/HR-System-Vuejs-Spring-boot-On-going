package com.company.hrsystem.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.company.hrsystem.dto.RequesterActionDto;

@Mapper
public interface RequesterActionMapper {

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	int insertRequesterAction(RequesterActionDto requestAction);

	int updateRequesterAction(RequesterActionDto requestAction);

}
