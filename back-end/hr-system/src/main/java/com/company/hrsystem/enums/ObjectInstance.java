package com.company.hrsystem.enums;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.company.hrsystem.request.ApproverActionRequest;
import com.company.hrsystem.request.AuthenRequest;
import com.company.hrsystem.request.BusinessRequest;
import com.company.hrsystem.request.ChangePasswordRequest;
import com.company.hrsystem.request.DocumentRequest;
import com.company.hrsystem.request.MutipleUpdateRequestTicketStatusRequest;
import com.company.hrsystem.request.PositionRequest;
import com.company.hrsystem.request.ReasonRequest;
import com.company.hrsystem.request.RequestTypeRequest;
import com.company.hrsystem.request.RequesterActionRequest;
import com.company.hrsystem.request.RoomRequest;
import com.company.hrsystem.request.SignUpRequest;
import com.company.hrsystem.request.SupervisorActionRequest;
import com.company.hrsystem.request.SystemRoleRequest;
import com.company.hrsystem.request.UpdateAccountRequest;

public enum ObjectInstance {
	
	AUTHEN_REQUEST(AuthenRequest.class.getName()),
	SIGNUP_REQUEST(SignUpRequest.class.getName()),
	CHANGE_PASSWORD_REQUEST(ChangePasswordRequest.class.getName()),
	UPDATE_ACCOUNT_REQUEST(UpdateAccountRequest.class.getName()),
	BUSINESS_REQUEST(BusinessRequest.class.getName()),
	REQUESTER_ACTION(RequesterActionRequest.class.getName()),
	SUPERVISOR_ACTION(SupervisorActionRequest.class.getName()),
	APPROVER_ACTION(ApproverActionRequest.class.getName()),
	MULTIPLE_ACTION(MutipleUpdateRequestTicketStatusRequest.class.getName()),
	SYSTEM_ROLE(SystemRoleRequest.class.getName()),
	DOCUMENT(DocumentRequest.class.getName()),
	POSITION(PositionRequest.class.getName()),
	REASON(ReasonRequest.class.getName()),
	REQUEST_TYPE(RequestTypeRequest.class.getName()),
	ROOM(RoomRequest.class.getName());

	private String refClassname;

	private static final Map<String, ObjectInstance> ENUM_MAP;

	ObjectInstance(String refClassname) {
		this.refClassname = refClassname;
	}

	static {
		Map<String, ObjectInstance> map = new ConcurrentHashMap<String, ObjectInstance>();
		for (ObjectInstance instance : ObjectInstance.values()) {
			map.put(instance.refClassname, instance);
		}
		ENUM_MAP = Collections.unmodifiableMap(map);
	}

	public static ObjectInstance get(String name) {
		return ENUM_MAP.get(name);
	}
	
}
