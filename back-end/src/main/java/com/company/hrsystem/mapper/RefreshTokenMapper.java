package com.company.hrsystem.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.dto.RefreshTokenDto;

@Mapper
public interface RefreshTokenMapper {

	public RefreshTokenDto findRefreshTokenByEmail(String email);
	
	public RefreshTokenDto findRefreshTokenByToken(String token);

	public int insertRefreshToken(RefreshTokenDto jwtRefreshModel);

	public int updateRefreshToken(RefreshTokenDto jwtRefreshModel);

}
