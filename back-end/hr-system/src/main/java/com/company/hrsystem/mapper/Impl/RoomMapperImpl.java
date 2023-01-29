package com.company.hrsystem.mapper.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.company.hrsystem.annotations.MapperImpl;
import com.company.hrsystem.dto.RoomDto;
import com.company.hrsystem.mapper.RoomMapper;

@MapperImpl
public class RoomMapperImpl {

	@Autowired
	private RoomMapper roomMapper;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public int insertRoom(RoomDto request) {
		return roomMapper.insertRoom(request);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public int updateRoom(RoomDto request) {
		return roomMapper.updateRoom(request);
	}

	public List<RoomDto> findAllRooms() {
		return roomMapper.findAllRooms();
	}

	public List<RoomDto> findRooms() {
		return roomMapper.findRooms();
	}
	
}
