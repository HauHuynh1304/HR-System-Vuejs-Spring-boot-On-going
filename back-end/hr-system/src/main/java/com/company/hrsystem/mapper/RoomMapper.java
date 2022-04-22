package com.company.hrsystem.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.request.RoomRequest;

@Mapper
public interface RoomMapper {

	int insertRoom(RoomRequest request);

	int updateRoom(RoomRequest request);
	
}
