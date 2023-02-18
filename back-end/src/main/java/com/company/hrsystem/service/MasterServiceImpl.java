package com.company.hrsystem.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.company.hrsystem.annotations.WriteLogToDB;
import com.company.hrsystem.commons.configs.SystemProperties;
import com.company.hrsystem.commons.constants.CommonConstant;
import com.company.hrsystem.commons.exceptions.GlobalException;
import com.company.hrsystem.commons.exceptions.NullPointRequestException;
import com.company.hrsystem.commons.utils.DateUtil;
import com.company.hrsystem.commons.utils.LogUtil;
import com.company.hrsystem.commons.utils.MessageUtil;
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
import com.company.hrsystem.response.ResponseData;
import com.company.hrsystem.service.interfaces.IMasterService;

@Service
public class MasterServiceImpl implements IMasterService {

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
	public ResponseData insertSystemRole(SystemRoleRequest request, HttpServletRequest servletRequest) {
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
		return new ResponseData(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getFlexMessageLangUS("insert.row", String.valueOf(inseartRows)),
				null, null);
	}

	@Transactional
	@WriteLogToDB
	public ResponseData updateSystemRole(SystemRoleRequest request, HttpServletRequest servletRequest) {
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
		return new ResponseData(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getFlexMessageLangUS("update.row", String.valueOf(updateRows)), null,
				null);
	}

	@Transactional
	@WriteLogToDB
	public ResponseData insertDocument(DocumentRequest request, HttpServletRequest servletRequest) {
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
		return new ResponseData(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getFlexMessageLangUS("insert.row", String.valueOf(inseartRows)),
				null, null);
	}

	@Transactional
	@WriteLogToDB
	public ResponseData updateDocument(DocumentRequest request, HttpServletRequest servletRequest) {
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
		return new ResponseData(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getFlexMessageLangUS("update.row", String.valueOf(updateRows)), null,
				null);
	}

	@Transactional
	@WriteLogToDB
	public ResponseData insertPosition(PositionRequest request, HttpServletRequest servletRequest) {
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
		return new ResponseData(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getFlexMessageLangUS("insert.row", String.valueOf(insertRows)), null,
				null);
	}

	@Transactional
	@WriteLogToDB
	public ResponseData updatePosition(PositionRequest request, HttpServletRequest servletRequest) {
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
		return new ResponseData(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getFlexMessageLangUS("update.row", String.valueOf(updateRows)), null,
				null);
	}

	@Transactional
	@WriteLogToDB
	public ResponseData insertReason(ReasonRequest request, HttpServletRequest servletRequest) {
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
		return new ResponseData(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getFlexMessageLangUS("insert.row", String.valueOf(insertRows)), null,
				null);
	}

	@Transactional
	@WriteLogToDB
	public ResponseData updateReason(ReasonRequest request, HttpServletRequest servletRequest) {
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
		return new ResponseData(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getFlexMessageLangUS("update.row", String.valueOf(updateRows)), null,
				null);
	}

	@Transactional
	@WriteLogToDB
	public ResponseData insertRequestType(RequestTypeRequest request, HttpServletRequest servletRequest) {
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
		return new ResponseData(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getFlexMessageLangUS("insert.row", String.valueOf(insertRows)), null,
				null);
	}

	@Transactional
	@WriteLogToDB
	public ResponseData updateRequestType(RequestTypeRequest request, HttpServletRequest servletRequest) {
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
		return new ResponseData(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getFlexMessageLangUS("update.row", String.valueOf(updateRows)), null,
				null);
	}

	@Transactional
	@WriteLogToDB
	public ResponseData insertRoom(RoomRequest request, HttpServletRequest servletRequest) {
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
		return new ResponseData(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getFlexMessageLangUS("insert.row", String.valueOf(insertRows)), null,
				null);
	}

	@Transactional
	@WriteLogToDB
	public ResponseData updateRoom(RoomRequest request, HttpServletRequest servletRequest) {
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
		return new ResponseData(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getFlexMessageLangUS("update.row", String.valueOf(updateRows)), null,
				null);
	}

	public ResponseData findRoles() {
		return new ResponseData(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("get.data.success"), null, roleMapperImpl.findRoles());
	}

	public ResponseData findAllRoles() {
		return new ResponseData(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("get.data.success"), null,
				roleMapperImpl.findAllRoles());
	}

	public ResponseData findAllAccounts() {
		return new ResponseData(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("get.data.success"), null,
				systemAccountMapperImpl.findAllAccount());
	}

	public ResponseData findAllRooms() {
		return new ResponseData(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("get.data.success"), null,
				roomMapperImpl.findAllRooms());
	}

	public ResponseData findAllDocuments() {
		return new ResponseData(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("get.data.success"), null,
				documentMapperImpl.findAllDocuments());
	}

	public ResponseData findAvailbleAccounts() {
		return new ResponseData(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("get.data.success"), null,
				systemAccountMapperImpl.findAvailbleAccounts());
	}

	public ResponseData findAllRequestType() {
		return new ResponseData(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("get.data.success"), null,
				requestTypeMapperImpl.findAllRequestType());
	}

	public ResponseData findAllPositions() {
		return new ResponseData(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("get.data.success"), null,
				positionMapperImpl.findAllPositions());
	}

	public ResponseData findAllReason() {
		return new ResponseData(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("get.data.success"), null,
				reasonMapperImpl.findAllReason());
	}

}
