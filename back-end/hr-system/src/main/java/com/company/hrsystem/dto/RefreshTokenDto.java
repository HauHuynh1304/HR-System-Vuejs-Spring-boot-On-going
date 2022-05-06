package com.company.hrsystem.dto;

import java.io.Serializable;

import com.company.hrsystem.model.RefreshTokenModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RefreshTokenDto extends RefreshTokenModel implements Serializable {

	private static final long serialVersionUID = 1L;

}
