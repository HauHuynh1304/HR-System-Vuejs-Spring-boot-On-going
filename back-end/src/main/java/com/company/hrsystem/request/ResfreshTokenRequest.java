package com.company.hrsystem.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResfreshTokenRequest implements Serializable {

	private static final long serialVersionUID = 4614168610165319668L;

	private String refreshTokenName;

	private ResfreshTokenRequest data;

}
