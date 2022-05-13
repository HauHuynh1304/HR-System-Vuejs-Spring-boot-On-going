package com.company.hrsystem.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BooleanEnum {
	
	TRUE(1), FALSE(0);

	private Integer value;

}
