package com.company.hrsystem.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.dto.RoomDto;

@Mapper
public interface RoomMapper {

	int insertRoom(RoomDto request);

	int updateRoom(RoomDto request);

}
