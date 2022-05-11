package com.company.hrsystem.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PartialDateEnum {

	ALL_DAY("ALL DAY"), HALF_DAY("HALF DAY");

	private String value;

	public static boolean isExists(String value) {
		for (PartialDateEnum e : PartialDateEnum.values()) {
			if (value.equals(e.value))
				return true;
		}
		return false;
	}

}
