package com.company.hrsystem.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.company.hrsystem.Exeption.GlobalException;
import com.company.hrsystem.Exeption.NullPointRequestException;
import com.company.hrsystem.annotations.WriteLogToDB;
import com.company.hrsystem.config.SystemProperties;
import com.company.hrsystem.constants.CommonConstant;
import com.company.hrsystem.dto.DocumentDto;
import com.company.hrsystem.dto.PositionDto;
import com.company.hrsystem.dto.ReasonDto;
import com.company.hrsystem.dto.RequestTypeDto;
import com.company.hrsystem.dto.RoomDto;
import com.company.hrsystem.dto.SystemRoleDto;
import com.company.hrsystem.mapper.Impl.DocumentMapperImpl;
import com.company.hrsystem.mapper.Impl.PositionMapperImpl;
import com.company.hrsystem.mapper.Impl.ReasonMapperImpl;
import com.company.hrsystem.mapper.Impl.RequestTypeMapperImpl;
import com.company.hrsystem.mapper.Impl.RoomMapperImpl;
import com.company.hrsystem.mapper.Impl.SystemAccountMapperImpl;
import com.company.hrsystem.mapper.Impl.SystemRoleMapperImpl;
import com.company.hrsystem.request.DocumentRequest;
import com.company.hrsystem.request.PositionRequest;
import com.company.hrsystem.request.ReasonRequest;
import com.company.hrsystem.request.RequestTypeRequest;
import com.company.hrsystem.request.RoomRequest;
import com.company.hrsystem.request.SystemRoleRequest;
import com.company.hrsystem.response.ResponseTemplate;
import com.company.hrsystem.serviceInterface.MasterServiceInterface;
import com.company.hrsystem.utils.DateUtil;
import com.company.hrsystem.utils.LogUtil;
import com.company.hrsystem.utils.MessageUtil;

@Service
public class MasterService implements MasterServiceInterface {

	@Autowired
	private SystemRoleMapperImpl roleMapperImpl;

	@Autowired
	private DocumentMapperImpl documentMapperImpl;

	@Autowired
	private PositionMapperImpl positionMapperImpl;

	@Autowired
	private ReasonMapperImpl reasonMapperImpl;

	@Autowired
	private RequestTypeMapperImpl requestTypeMapperImpl;

	@Autowired
	private RoomMapperImpl roomMapperImpl;

	@Autowired
	private SystemAccountMapperImpl systemAccountMapperImpl;

	@Transactional
	@WriteLogToDB
	public ResponseTemplate insertSystemRole(SystemRoleRequest request, HttpServletRequest servletRequest) {
		SystemRoleDto obj = request.getData().getSystemRole();
		int inseartRows = CommonConstant.ZERO_VALUE;
		if (ObjectUtils.isEmpty(obj) || StringUtils.isBlank(obj.getRoleName())) {
			LogUtil.warn(MessageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.ROLE_INSERT_NOT_NULL));
			throw new NullPointRequestException(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					MessageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.ROLE_INSERT_NOT_NULL));
		}
		try {
			inseartRows = roleMapperImpl.insertSystemRoleSelected(obj);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					e.getCause().getMessage());
		}
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getFlexMessageLangUS("insert.row", String.valueOf(inseartRows)),
				null, null);
	}

	@Transactional
	@WriteLogToDB
	public ResponseTemplate updateSystemRole(SystemRoleRequest request, HttpServletRequest servletRequest) {
		int updateRows = CommonConstant.ZERO_VALUE;
		SystemRoleDto obj = request.getData().getSystemRole();
		obj.setUpdatedAt(DateUtil.getCurrentDayHourSecond());
		if (ObjectUtils.isEmpty(obj) || ObjectUtils.isEmpty(obj.getSystemRoleId())) {
			LogUtil.warn(MessageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.ROLE_UPDATE_NOT_NULL));
			throw new NullPointRequestException(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					MessageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.ROLE_UPDATE_NOT_NULL));
		}
		try {
			updateRows = roleMapperImpl.updateSystemRoleSelected(obj);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					e.getCause().getMessage());
		}
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getFlexMessageLangUS("update.row", String.valueOf(updateRows)), null,
				null);
	}

	@Transactional
	@WriteLogToDB
	public ResponseTemplate insertDocument(DocumentRequest request, HttpServletRequest servletRequest) {
		int inseartRows = CommonConstant.ZERO_VALUE;
		DocumentDto obj = request.getData().getDocument();
		if (ObjectUtils.isEmpty(obj) || StringUtils.isBlank(obj.getDocumentName())) {
			throw new NullPointRequestException(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					MessageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.DOCUMENT_INSERT_NOT_NULL));
		}
		try {
			inseartRows = documentMapperImpl.insertDocument(obj);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					e.getCause().getMessage());
		}
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getFlexMessageLangUS("insert.row", String.valueOf(inseartRows)),
				null, null);
	}

	@Transactional
	@WriteLogToDB
	public ResponseTemplate updateDocument(DocumentRequest request, HttpServletRequest servletRequest) {
		int updateRows = CommonConstant.ZERO_VALUE;
		DocumentDto obj = request.getData().getDocument();
		obj.setUpdatedAt(DateUtil.getCurrentDayHourSecond());
		if (ObjectUtils.isEmpty(obj) || ObjectUtils.isEmpty(obj.getDocumentId())) {
			LogUtil.warn(
					MessageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.DOCUMENT_UPDATE_NOT_NULL));
			throw new NullPointRequestException(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					MessageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.DOCUMENT_UPDATE_NOT_NULL));
		}
		try {
			updateRows = documentMapperImpl.updateDocument(obj);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					e.getCause().getMessage());
		}
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getFlexMessageLangUS("update.row", String.valueOf(updateRows)), null,
				null);
	}

	@Transactional
	@WriteLogToDB
	public ResponseTemplate insertPosition(PositionRequest request, HttpServletRequest servletRequest) {
		int insertRows = CommonConstant.ZERO_VALUE;
		PositionDto obj = request.getData().getPosition();
		if (ObjectUtils.isEmpty(obj) || StringUtils.isBlank(obj.getPositionName())) {
			LogUtil.warn(
					MessageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.POSITION_INSERT_NOT_NULL));
			throw new NullPointRequestException(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					MessageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.POSITION_INSERT_NOT_NULL));
		}
		try {
			insertRows = positionMapperImpl.insertPosition(obj);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					e.getCause().getMessage());
		}
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getFlexMessageLangUS("insert.row", String.valueOf(insertRows)), null,
				null);
	}

	@Transactional
	@WriteLogToDB
	public ResponseTemplate updatePosition(PositionRequest request, HttpServletRequest servletRequest) {
		int updateRows = CommonConstant.ZERO_VALUE;
		PositionDto obj = request.getData().getPosition();
		obj.setUpdatedAt(DateUtil.getCurrentDayHourSecond());
		if (ObjectUtils.isEmpty(obj) || ObjectUtils.isEmpty(obj.getPositionId())) {
			LogUtil.warn(
					MessageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.POSITION_UPDATE_NOT_NULL));
			throw new NullPointRequestException(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					MessageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.POSITION_UPDATE_NOT_NULL));
		}
		try {
			updateRows = positionMapperImpl.updatePosition(obj);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					e.getCause().getMessage());
		}
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getFlexMessageLangUS("update.row", String.valueOf(updateRows)), null,
				null);
	}

	@Transactional
	@WriteLogToDB
	public ResponseTemplate insertReason(ReasonRequest request, HttpServletRequest servletRequest) {
		int insertRows = CommonConstant.ZERO_VALUE;
		ReasonDto obj = request.getData().getReason();
		if (ObjectUtils.isEmpty(obj) || StringUtils.isBlank(obj.getReasonName())) {
			LogUtil.warn(MessageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.REASON_INSERT_NOT_NULL));
			throw new NullPointRequestException(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					MessageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.REASON_INSERT_NOT_NULL));
		}
		try {
			insertRows = reasonMapperImpl.insertReason(obj);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					e.getCause().getMessage());
		}
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getFlexMessageLangUS("insert.row", String.valueOf(insertRows)), null,
				null);
	}

	@Transactional
	@WriteLogToDB
	public ResponseTemplate updateReason(ReasonRequest request, HttpServletRequest servletRequest) {
		int updateRows = CommonConstant.ZERO_VALUE;
		ReasonDto obj = request.getData().getReason();
		obj.setUpdatedAt(DateUtil.getCurrentDayHourSecond());
		if (ObjectUtils.isEmpty(obj) || ObjectUtils.isEmpty(obj.getReasonId())) {
			LogUtil.warn(MessageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.REASON_UPDATE_NOT_NULL));
			throw new NullPointRequestException(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					MessageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.REASON_UPDATE_NOT_NULL));
		}
		try {
			updateRows = reasonMapperImpl.updateReason(obj);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					e.getCause().getMessage());
		}
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getFlexMessageLangUS("update.row", String.valueOf(updateRows)), null,
				null);
	}

	@Transactional
	@WriteLogToDB
	public ResponseTemplate insertRequestType(RequestTypeRequest request, HttpServletRequest servletRequest) {
		int insertRows = CommonConstant.ZERO_VALUE;
		RequestTypeDto obj = request.getData().getRequestType();
		if (ObjectUtils.isEmpty(obj) || StringUtils.isBlank(obj.getRequestTypeName())) {
			LogUtil.warn(MessageUtil.getFlexMessageLangUS("null.request.empty",
					CommonConstant.REQUEST_TYPE_INSERT_NOT_NULL));
			throw new NullPointRequestException(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					MessageUtil.getFlexMessageLangUS("null.request.empty",
							CommonConstant.REQUEST_TYPE_INSERT_NOT_NULL));
		}
		try {
			insertRows = requestTypeMapperImpl.insertRequestType(obj);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					e.getCause().getMessage());
		}
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getFlexMessageLangUS("insert.row", String.valueOf(insertRows)), null,
				null);
	}

	@Transactional
	@WriteLogToDB
	public ResponseTemplate updateRequestType(RequestTypeRequest request, HttpServletRequest servletRequest) {
		int updateRows = CommonConstant.ZERO_VALUE;
		RequestTypeDto obj = request.getData().getRequestType();
		obj.setUpdatedAt(DateUtil.getCurrentDayHourSecond());
		if (ObjectUtils.isEmpty(obj) || ObjectUtils.isEmpty(obj.getRequestTypeId())) {
			LogUtil.warn(MessageUtil.getFlexMessageLangUS("null.request.empty",
					CommonConstant.REQUEST_TYPE_UPDATE_NOT_NULL));
			throw new NullPointRequestException(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					MessageUtil.getFlexMessageLangUS("null.request.empty",
							CommonConstant.REQUEST_TYPE_UPDATE_NOT_NULL));
		}
		try {
			updateRows = requestTypeMapperImpl.updateRequestType(obj);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					e.getCause().getMessage());
		}
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getFlexMessageLangUS("update.row", String.valueOf(updateRows)), null,
				null);
	}

	@Transactional
	@WriteLogToDB
	public ResponseTemplate insertRoom(RoomRequest request, HttpServletRequest servletRequest) {
		int insertRows = CommonConstant.ZERO_VALUE;
		RoomDto obj = request.getData().getRoom();
		if (ObjectUtils.isEmpty(obj) || StringUtils.isBlank(obj.getRoomName())) {
			LogUtil.warn(MessageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.ROOM_INSERT_NOT_NULL));
			throw new NullPointRequestException(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					MessageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.ROOM_INSERT_NOT_NULL));
		}
		try {
			insertRows = roomMapperImpl.insertRoom(obj);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					e.getCause().getMessage());
		}
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getFlexMessageLangUS("insert.row", String.valueOf(insertRows)), null,
				null);
	}

	@Transactional
	@WriteLogToDB
	public ResponseTemplate updateRoom(RoomRequest request, HttpServletRequest servletRequest) {
		int updateRows = CommonConstant.ZERO_VALUE;
		RoomDto obj = request.getData().getRoom();
		obj.setUpdatedAt(DateUtil.getCurrentDayHourSecond());
		if (ObjectUtils.isEmpty(obj) || ObjectUtils.isEmpty(obj.getRoomId())) {
			LogUtil.warn(MessageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.ROOM_UPDATE_NOT_NULL));
			throw new NullPointRequestException(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					MessageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.ROOM_UPDATE_NOT_NULL));
		}
		try {
			updateRows = roomMapperImpl.updateRoom(obj);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					e.getCause().getMessage());
		}
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getFlexMessageLangUS("update.row", String.valueOf(updateRows)), null,
				null);
	}

	public ResponseTemplate findRoles() {
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("get.data.success"), null, roleMapperImpl.findRoles());
	}

	public ResponseTemplate findAllRoles() {
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("get.data.success"), null,
				roleMapperImpl.findAllRoles());
	}

	public ResponseTemplate findAllAccounts() {
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("get.data.success"), null,
				systemAccountMapperImpl.findAllAccount());
	}

	public ResponseTemplate findAllRooms() {
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("get.data.success"), null,
				roomMapperImpl.findAllRooms());
	}

	public ResponseTemplate findAllDocuments() {
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("get.data.success"), null,
				documentMapperImpl.findAllDocuments());
	}

	public ResponseTemplate findAvailbleAccounts() {
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("get.data.success"), null,
				systemAccountMapperImpl.findAvailbleAccounts());
	}

	public ResponseTemplate findAllRequestType() {
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("get.data.success"), null,
				requestTypeMapperImpl.findAllRequestType());
	}

	public ResponseTemplate findAllPositions() {
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("get.data.success"), null,
				positionMapperImpl.findAllPositions());
	}

	public ResponseTemplate findAllReason() {
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("get.data.success"), null,
				reasonMapperImpl.findAllReason());
	}

}
