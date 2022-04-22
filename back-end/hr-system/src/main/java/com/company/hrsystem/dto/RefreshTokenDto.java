package com.company.hrsystem.dto;

import com.company.hrsystem.model.RefreshTokenModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RefreshTokenDto extends RefreshTokenModel {

	public RefreshTokenDto(Integer systemAccountId, String refreshTokenName, String expiryDate) {
		super(systemAccountId, refreshTokenName, expiryDate);
	}

}
