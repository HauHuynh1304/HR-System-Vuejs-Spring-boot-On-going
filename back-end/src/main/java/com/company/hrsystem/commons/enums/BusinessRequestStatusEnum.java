package com.company.hrsystem.commons.enums;

public enum BusinessRequestStatusEnum {

	APPROVED, WAITING, CANCEL, REJECT;

	public static Boolean isForbidentEmployee(String value) {
		if (value.toLowerCase().equals(CANCEL.toString().toLowerCase())) {
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

	public static Boolean isForbidenManager(String value) {
		if (value.toLowerCase().equals(CANCEL.toString().toLowerCase())) {
			return true;
		}
		return false;
	}

	public static Boolean isForbidenByRequestStatus(String value) {
		if (value.toLowerCase().equals(WAITING.toString().toLowerCase())) {
			return false;
		}
		return true;
	}

}
