package com.company.hrsystem.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.company.hrsystem.Exeption.NullPointRequestException;
import com.company.hrsystem.Exeption.GlobalException;
import com.company.hrsystem.constants.CommonConstant;
import com.company.hrsystem.dto.DocumentDto;
import com.company.hrsystem.dto.PositionDto;
import com.company.hrsystem.dto.ReasonDto;
import com.company.hrsystem.dto.RequestTypeDto;
import com.company.hrsystem.dto.RoomDto;
import com.company.hrsystem.dto.SystemRoleDto;
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
	private String version;

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
		SystemRoleDto obj = request.getData().getSystemRole();
		int inseartRows = 0;
		if (ObjectUtils.isEmpty(obj) || StringUtils.isBlank(obj.getRoleName())) {
			LogUtil.warn(messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.ROLE_INSERT_NOT_NULL));
			throw new NullPointRequestException(system, version,
					messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.ROLE_INSERT_NOT_NULL));
		}
		try {
			inseartRows = roleMapper.insertSystemRoleSelected(obj);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, version, e.getCause().getMessage());
		}
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getFlexMessageLangUS("insert.row", String.valueOf(inseartRows)), null, null);
	}

	public ResponseTemplate updateSystemRole(SystemRoleRequest request) {
		int updateRows = 0;
		SystemRoleDto obj = request.getData().getSystemRole();
		obj.setUpdatedAt(DateUtil.getCurrentDayHourSecond());
		if (ObjectUtils.isEmpty(obj) || ObjectUtils.isEmpty(obj.getSystemRoleId())) {
			LogUtil.warn(messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.ROLE_UPDATE_NOT_NULL));
			throw new NullPointRequestException(system, version,
					messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.ROLE_UPDATE_NOT_NULL));
		}
		try {
			updateRows = roleMapper.updateSystemRoleSelected(obj);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, version, e.getCause().getMessage());
		}
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getFlexMessageLangUS("update.row", String.valueOf(updateRows)), null, null);
	}

	public ResponseTemplate insertDocument(DocumentRequest request) {
		int inseartRows = 0;
		DocumentDto obj = request.getData().getDocument();
		if (ObjectUtils.isEmpty(obj) || StringUtils.isBlank(obj.getDocumentName())) {
			throw new NullPointRequestException(system, version,
					messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.DOCUMENT_INSERT_NOT_NULL));
		}
		try {
			inseartRows = documentMapper.insertDocument(obj);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, version, e.getCause().getMessage());
		}
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getFlexMessageLangUS("insert.row", String.valueOf(inseartRows)), null, null);
	}

	public ResponseTemplate updateDocument(DocumentRequest request) {
		int updateRows = 0;
		DocumentDto obj = request.getData().getDocument();
		obj.setUpdatedAt(DateUtil.getCurrentDayHourSecond());
		if (ObjectUtils.isEmpty(obj) || ObjectUtils.isEmpty(obj.getDocumentId())) {
			LogUtil.warn(
					messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.DOCUMENT_UPDATE_NOT_NULL));
			throw new NullPointRequestException(system, version,
					messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.DOCUMENT_UPDATE_NOT_NULL));
		}
		try {
			updateRows = documentMapper.updateDocument(obj);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, version, e.getCause().getMessage());
		}
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getFlexMessageLangUS("update.row", String.valueOf(updateRows)), null, null);
	}

	public ResponseTemplate insertPosition(PositionRequest request) {
		int insertRows = 0;
		PositionDto obj = request.getData().getPosition();
		if (ObjectUtils.isEmpty(obj) || StringUtils.isBlank(obj.getPositionName())) {
			LogUtil.warn(
					messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.POSITION_INSERT_NOT_NULL));
			throw new NullPointRequestException(system, version,
					messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.POSITION_INSERT_NOT_NULL));
		}
		try {
			insertRows = positionMapper.insertPosition(obj);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, version, e.getCause().getMessage());
		}
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getFlexMessageLangUS("insert.row", String.valueOf(insertRows)), null, null);
	}

	public ResponseTemplate updatePosition(PositionRequest request) {
		int updateRows = 0;
		PositionDto obj = request.getData().getPosition();
		obj.setUpdatedAt(DateUtil.getCurrentDayHourSecond());
		if (ObjectUtils.isEmpty(obj) || ObjectUtils.isEmpty(obj.getPositionId())) {
			LogUtil.warn(
					messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.POSITION_UPDATE_NOT_NULL));
			throw new NullPointRequestException(system, version,
					messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.POSITION_UPDATE_NOT_NULL));
		}
		try {
			updateRows = positionMapper.updatePosition(obj);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, version, e.getCause().getMessage());
		}
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getFlexMessageLangUS("update.row", String.valueOf(updateRows)), null, null);
	}

	public ResponseTemplate insertReason(ReasonRequest request) {
		int insertRows = 0;
		ReasonDto obj = request.getData().getReason();
		if (ObjectUtils.isEmpty(obj) || StringUtils.isBlank(obj.getReasonName())) {
			LogUtil.warn(messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.REASON_INSERT_NOT_NULL));
			throw new NullPointRequestException(system, version,
					messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.REASON_INSERT_NOT_NULL));
		}
		try {
			insertRows = reasonMapper.insertReason(obj);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, version, e.getCause().getMessage());
		}
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getFlexMessageLangUS("insert.row", String.valueOf(insertRows)), null, null);
	}

	public ResponseTemplate updateReason(ReasonRequest request) {
		int updateRows = 0;
		ReasonDto obj = request.getData().getReason();
		obj.setUpdatedAt(DateUtil.getCurrentDayHourSecond());
		if (ObjectUtils.isEmpty(obj) || ObjectUtils.isEmpty(obj.getReasonId())) {
			LogUtil.warn(messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.REASON_UPDATE_NOT_NULL));
			throw new NullPointRequestException(system, version,
					messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.REASON_UPDATE_NOT_NULL));
		}
		try {
			updateRows = reasonMapper.updateReason(obj);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, version, e.getCause().getMessage());
		}
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getFlexMessageLangUS("update.row", String.valueOf(updateRows)), null, null);
	}

	public ResponseTemplate insertRequestType(RequestTypeRequest request) {
		int insertRows = 0;
		RequestTypeDto obj = request.getData().getRequestType();
		if (ObjectUtils.isEmpty(obj) || obj.getRequestTypeName().isBlank()) {
			LogUtil.warn(messageUtil.getFlexMessageLangUS("null.request.empty",
					CommonConstant.REQUEST_TYPE_INSERT_NOT_NULL));
			throw new NullPointRequestException(system, version, messageUtil.getFlexMessageLangUS("null.request.empty",
					CommonConstant.REQUEST_TYPE_INSERT_NOT_NULL));
		}
		try {
			insertRows = requestTypeMapper.insertRequestType(obj);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, version, e.getCause().getMessage());
		}
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getFlexMessageLangUS("insert.row", String.valueOf(insertRows)), null, null);
	}

	public ResponseTemplate updateRequestType(RequestTypeRequest request) {
		int updateRows = 0;
		RequestTypeDto obj = request.getData().getRequestType();
		obj.setUpdatedAt(DateUtil.getCurrentDayHourSecond());
		if (ObjectUtils.isEmpty(obj) || ObjectUtils.isEmpty(obj.getRequestTypeId())) {
			LogUtil.warn(messageUtil.getFlexMessageLangUS("null.request.empty",
					CommonConstant.REQUEST_TYPE_UPDATE_NOT_NULL));
			throw new NullPointRequestException(system, version, messageUtil.getFlexMessageLangUS("null.request.empty",
					CommonConstant.REQUEST_TYPE_UPDATE_NOT_NULL));
		}
		try {
			updateRows = requestTypeMapper.insertRequestType(obj);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, version, e.getCause().getMessage());
		}
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getFlexMessageLangUS("udpate.row", String.valueOf(updateRows)), null, null);
	}

	public ResponseTemplate insertRoom(RoomRequest request) {
		int insertRows = 0;
		RoomDto obj = request.getData().getRoom();
		if (ObjectUtils.isEmpty(obj) || obj.getRoomName().isBlank()) {
			LogUtil.warn(messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.ROOM_INSERT_NOT_NULL));
			throw new NullPointRequestException(system, version,
					messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.ROOM_INSERT_NOT_NULL));
		}
		try {
			insertRows = roomMapper.insertRoom(obj);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, version, e.getCause().getMessage());
		}
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getFlexMessageLangUS("insert.row", String.valueOf(insertRows)), null, null);
	}

	public ResponseTemplate updateRoom(RoomRequest request) {
		int updateRows = 0;
		RoomDto obj = request.getData().getRoom();
		obj.setUpdatedAt(DateUtil.getCurrentDayHourSecond());
		if (ObjectUtils.isEmpty(obj) || ObjectUtils.isEmpty(obj.getRoomId())) {
			LogUtil.warn(messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.ROOM_UPDATE_NOT_NULL));
			throw new NullPointRequestException(system, version,
					messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.ROOM_UPDATE_NOT_NULL));
		}
		try {
			updateRows = roomMapper.insertRoom(obj);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, version, e.getCause().getMessage());
		}
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getFlexMessageLangUS("udpate.row", String.valueOf(updateRows)), null, null);
	}

}
