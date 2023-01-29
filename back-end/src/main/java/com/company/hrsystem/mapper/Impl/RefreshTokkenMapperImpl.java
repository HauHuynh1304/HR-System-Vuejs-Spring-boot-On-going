package com.company.hrsystem.mapper.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.company.hrsystem.annotations.MapperImpl;
import com.company.hrsystem.dto.RefreshTokenDto;
import com.company.hrsystem.mapper.RefreshTokenMapper;

@MapperImpl
public class RefreshTokkenMapperImpl {

	@Autowired
	private RefreshTokenMapper refreshTokenMapper;

	@Transactional(propagation = Propagation.NOT_SUPPORTED, isolation = Isolation.READ_COMMITTED)
	public RefreshTokenDto findRefreshTokenByEmail(String email) {
		return refreshTokenMapper.findRefreshTokenByEmail(email);
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, isolation = Isolation.READ_COMMITTED)
	public RefreshTokenDto findRefreshTokenByToken(String token) {
		return refreshTokenMapper.findRefreshTokenByToken(token);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public int insertRefreshToken(RefreshTokenDto jwtRefreshModel) {
		return refreshTokenMapper.insertRefreshToken(jwtRefreshModel);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public int updateRefreshToken(RefreshTokenDto jwtRefreshModel) {
		return refreshTokenMapper.updateRefreshToken(jwtRefreshModel);
	}

}
