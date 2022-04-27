package com.company.hrsystem.service;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.company.hrsystem.Exeption.NullPointRequestException;
import com.company.hrsystem.Exeption.GlobalException;
import com.company.hrsystem.constants.CommonConstant;
import com.company.hrsystem.mapper.DocumentMapper;
import com.company.hrsystem.mapper.PositionMapper;
import com.company.hrsystem.mapper.ReasonMapper;
import com.company.hrsystem.mapper.RequestTypeMapper;
import com.company.hrsystem.mapper.RoomMapper;
import com.company.hrsystem.mapper.SytemRoleMapper;
import com.company.hrsystem.request.DocumentRequest;
import com.company.hrsystem.request.PositionRequest;
import com.company.hrsystem.request.ReasonRequest;
import com.company.hrsystem.request.RequestTypeRequest;
import com.company.hrsystem.request.RoomRequest;
import com.company.hrsystem.request.SystemRoleRequest;
import com.company.hrsystem.response.ResponseTemplate;
import com.company.hrsystem.utils.DateUtil;
import com.company.hrsystem.utils.LogUtil;
import com.company.hrsystem.utils.MessageUtil;

@Service
public class MasterService {

	@Value("${system.name}")
	private String system;

	@Value("${system.version}")
	private String verion;

	@Autowired
	SytemRoleMapper roleMapper;

	@Autowired
	DocumentMapper documentMapper;

	@Autowired
	PositionMapper positionMapper;

	@Autowired
	ReasonMapper reasonMapper;

	@Autowired
	RequestTypeMapper requestTypeMapper;

	@Autowired
	RoomMapper roomMapper;

	@Autowired
	private MessageUtil messageUtil;

	public ResponseTemplate insertSystemRole(SystemRoleRequest request) {
		SystemRoleRequest obj = request.getData();
		if (ObjectUtils.isEmpty(obj) || obj.getRoleName().isBlank()) {
			throw new NullPointRequestException(system, verion,
					messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.ROLE_INSERT_NOT_NULL));
		}
		try {
			roleMapper.insertSystemRoleSelected(obj);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, verion, e.getCause().getMessage());
		}
		return new ResponseTemplate(system, verion, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("insert.role.success"), null, null);
	}

	public ResponseTemplate updateSystemRole(SystemRoleRequest request) {
		SystemRoleRequest obj = request.getData();
		obj.setUpdatedAt(DateUtil.getCurrentDayHourSecond());
		if (ObjectUtils.isEmpty(obj) || obj.getRoleName().isBlank() || obj.getApplyScope().isEmpty()
				|| obj.getDeletedFlag().toString().isBlank()) {
			throw new NullPointRequestException(system, verion,
					messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.ROLE_UPDATE_NOT_NULL));
		}
		try {
			roleMapper.updateSystemRoleSelected(obj);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, verion, e.getCause().getMessage());
		}
		return new ResponseTemplate(system, verion, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("udpate.role.success"), null, null);
	}

	public ResponseTemplate insertDocument(DocumentRequest request) {
		DocumentRequest obj = request.getData();
		if (ObjectUtils.isEmpty(obj) || obj.getDocumentName().isBlank()) {
			throw new NullPointRequestException(system, verion,
					messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.DOCUMENT_INSERT_NOT_NULL));
		}
		try {
			documentMapper.insertDocument(obj);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, verion, e.getCause().getMessage());
		}
		return new ResponseTemplate(system, verion, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("insert.document.success"), null, null);
	}

	public ResponseTemplate updateDocument(DocumentRequest request) {
		DocumentRequest obj = request.getData();
		obj.setUpdatedAt(DateUtil.getCurrentDayHourSecond());
		if (ObjectUtils.isArray(obj) || obj.getDocumentName().isBlank() || obj.getDeletedFlag().toString().isBlank()) {
			throw new NullPointRequestException(system, verion,
					messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.DOCUMENT_UPDATE_NOT_NULL));
		}
		try {
			documentMapper.updateDocument(obj);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, verion, e.getCause().getMessage());
		}
		return new ResponseTemplate(system, verion, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("udpate.document.success"), null, null);
	}

	public ResponseTemplate insertPosition(PositionRequest request) {
		PositionRequest obj = request.getData();
		if (ObjectUtils.isEmpty(obj) || obj.getPositionName().isBlank()) {
			throw new NullPointRequestException(system, verion,
					messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.POSITION_INSERT_NOT_NULL));
		}
		try {
			positionMapper.insertPosition(obj);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, verion, e.getCause().getMessage());
		}
		return new ResponseTemplate(system, verion, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("insert.position.success"), null, null);
	}

	public ResponseTemplate updatePosition(PositionRequest request) {
		PositionRequest obj = request.getData();
		obj.setUpdatedAt(DateUtil.getCurrentDayHourSecond());
		if (ObjectUtils.isEmpty(obj) || obj.getPositionName().isBlank() || obj.getDeletedFlag().toString().isBlank()) {
			throw new NullPointRequestException(system, verion,
					messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.POSITION_UPDATE_NOT_NULL));
		}
		try {

			positionMapper.updatePosition(request.getData());
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, verion, e.getCause().getMessage());
		}
		return new ResponseTemplate(system, verion, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("udpate.position.success"), null, null);
	}

	public ResponseTemplate insertReason(ReasonRequest request) {
		ReasonRequest obj = request.getData();
		if (ObjectUtils.isEmpty(obj) || obj.getReasonName().isBlank()) {
			throw new NullPointRequestException(system, verion,
					messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.REASON_INSERT_NOT_NULL));
		}
		try {
			reasonMapper.insertReason(obj);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, verion, e.getCause().getMessage());
		}
		return new ResponseTemplate(system, verion, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("insert.reason.success"), null, null);
	}

	public ResponseTemplate updateReason(ReasonRequest request) {
		ReasonRequest obj = request.getData();
		obj.setUpdatedAt(DateUtil.getCurrentDayHourSecond());
		if (ObjectUtils.isEmpty(obj) || obj.getReasonName().isBlank() || obj.getDeletedFlag().toString().isBlank()) {
			throw new NullPointRequestException(system, verion,
					messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.REASON_UPDATE_NOT_NULL));
		}
		try {
			reasonMapper.updateReason(obj);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, verion, e.getCause().getMessage());
		}
		return new ResponseTemplate(system, verion, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("udpate.reason.success"), null, null);
	}

	public ResponseTemplate insertRequestType(RequestTypeRequest request) {
		RequestTypeRequest obj = request.getData();
		if (ObjectUtils.isEmpty(obj) || obj.getRequestTypeName().isBlank()) {
			throw new NullPointRequestException(system, verion, messageUtil.getFlexMessageLangUS("null.request.empty",
					CommonConstant.REQUEST_TYPE_INSERT_NOT_NULL));
		}
		try {
			requestTypeMapper.insertRequestType(obj);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, verion, e.getCause().getMessage());
		}
		return new ResponseTemplate(system, verion, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("insert.request.type.success"), null, null);
	}

	public ResponseTemplate updateRequestType(RequestTypeRequest request) {
		RequestTypeRequest obj = request.getData();
		obj.setUpdatedAt(DateUtil.getCurrentDayHourSecond());
		if (ObjectUtils.isEmpty(obj) || obj.getRequestTypeName().isBlank()
				|| obj.getDeletedFlag().toString().isBlank()) {
			throw new NullPointRequestException(system, verion, messageUtil.getFlexMessageLangUS("null.request.empty",
					CommonConstant.REQUEST_TYPE_UPDATE_NOT_NULL));
		}
		try {
			requestTypeMapper.insertRequestType(obj);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, verion, e.getCause().getMessage());
		}
		return new ResponseTemplate(system, verion, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("udpate.request.type.success"), null, null);
	}

	public ResponseTemplate insertRoom(RoomRequest request) {
		RoomRequest obj = request.getData();
		if (ObjectUtils.isEmpty(obj) || obj.getRoomName().isBlank()) {
			throw new NullPointRequestException(system, verion,
					messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.ROOM_INSERT_NOT_NULL));
		}
		try {
			roomMapper.insertRoom(obj);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, verion, e.getCause().getMessage());
		}
		return new ResponseTemplate(system, verion, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("insert.room.success"), null, null);
	}

	public ResponseTemplate updateRoom(RoomRequest request) {
		RoomRequest obj = request.getData();
		obj.setUpdatedAt(DateUtil.getCurrentDayHourSecond());
		if (ObjectUtils.isEmpty(obj) || obj.getRoomName().isBlank() || obj.getDeletedFlag().toString().isBlank()) {
			throw new NullPointRequestException(system, verion,
					messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.ROOM_UPDATE_NOT_NULL));
		}
		try {
			roomMapper.insertRoom(obj);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, verion, e.getCause().getMessage());
		}
		return new ResponseTemplate(system, verion, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("udpate.room.success"), null, null);
	}

}
