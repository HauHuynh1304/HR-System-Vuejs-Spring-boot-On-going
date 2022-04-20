package com.company.hrsystem.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.model.JwtRefreshModel;

@Mapper
public interface JwtResfreshMapper {

	public JwtRefreshModel findRefreshTokenByEmail(String email);
	
	public JwtRefreshModel findRefreshTokenByToken(String token);

	public int insertRefreshToken(JwtRefreshModel jwtRefreshModel);

	public int updateRefreshToken(JwtRefreshModel jwtRefreshModel);

}
