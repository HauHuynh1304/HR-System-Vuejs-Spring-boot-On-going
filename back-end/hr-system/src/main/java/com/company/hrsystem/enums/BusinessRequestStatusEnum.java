package com.company.hrsystem.enums;

public enum BusinessRequestStatusEnum {

	APPROVED, WAITING, CANCEL, REJECT;

	public static Boolean isForbidentEmployee(String value) {
		if (value.toLowerCase().equals(WAITING.toString().toLowerCase())
				|| value.toLowerCase().equals(CANCEL.toString().toLowerCase())) {
			return false;
		}
		return true;
	}

	public static boolean isExists(String value) {
		for (BusinessRequestStatusEnum e : BusinessRequestStatusEnum.values()) {
			if (value.equals(e.toString()))
				return true;
		}
		return false;
	}

}
