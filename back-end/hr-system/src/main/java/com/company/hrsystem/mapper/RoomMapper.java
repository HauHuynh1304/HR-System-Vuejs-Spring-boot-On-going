package com.company.hrsystem.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.dto.RoomDto;

@Mapper
public interface RoomMapper {

	int insertRoom(RoomDto request);

	int updateRoom(RoomDto request);

	List<RoomDto> findAllRooms();

}
