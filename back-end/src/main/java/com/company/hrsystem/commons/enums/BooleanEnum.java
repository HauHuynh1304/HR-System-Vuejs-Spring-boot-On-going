package com.company.hrsystem.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BooleanEnum {

	TRUE(1), FALSE(0);

	private Integer value;

	public static BooleanEnum getValue(int x) {
		BooleanEnum value = null;
		for (BooleanEnum type : BooleanEnum.values()) {
			if (type.getValue() == x)
				value = type;
		}
		return value;
	}

}
