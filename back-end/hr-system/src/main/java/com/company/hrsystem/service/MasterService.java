package com.company.hrsystem.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import com.company.hrsystem.mapper.SystemAccountMapper;
import com.company.hrsystem.mapper.SystemRoleMapper;
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
	private SystemRoleMapper roleMapper;

	@Autowired
	private DocumentMapper documentMapper;

	@Autowired
	private PositionMapper positionMapper;

	@Autowired
	private ReasonMapper reasonMapper;

	@Autowired
	private RequestTypeMapper requestTypeMapper;

	@Autowired
	private RoomMapper roomMapper;

	@Autowired
	private SystemAccountMapper systemAccountMapper;

	@Autowired
	private MessageUtil messageUtil;

	@Autowired
	private HistoryActionService historyActionService;

	@Transactional
	public ResponseTemplate insertSystemRole(SystemRoleRequest request, HttpServletRequest servletRequest) {
		SystemRoleDto obj = request.getData().getSystemRole();
		int inseartRows = CommonConstant.ZERO_VALUE;
		if (ObjectUtils.isEmpty(obj) || StringUtils.isBlank(obj.getRoleName())) {
			LogUtil.warn(messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.ROLE_INSERT_NOT_NULL));
			throw new NullPointRequestException(system, version,
					messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.ROLE_INSERT_NOT_NULL));
		}
		try {
			inseartRows = roleMapper.insertSystemRoleSelected(obj);
			// Save history action
			historyActionService.saveHistoryAction(obj, CommonConstant.ZERO_VALUE, CommonConstant.INSERT_ACTION,
					obj.getSystemRoleId(), CommonConstant.TABLE_SYSTEM_ROLE, servletRequest);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, version, e.getCause().getMessage());
		}
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getFlexMessageLangUS("insert.row", String.valueOf(inseartRows)), null, null);
	}

	@Transactional
	public ResponseTemplate updateSystemRole(SystemRoleRequest request, HttpServletRequest servletRequest) {
		int updateRows = CommonConstant.ZERO_VALUE;
		SystemRoleDto obj = request.getData().getSystemRole();
		obj.setUpdatedAt(DateUtil.getCurrentDayHourSecond());
		if (ObjectUtils.isEmpty(obj) || ObjectUtils.isEmpty(obj.getSystemRoleId())) {
			LogUtil.warn(messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.ROLE_UPDATE_NOT_NULL));
			throw new NullPointRequestException(system, version,
					messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.ROLE_UPDATE_NOT_NULL));
		}
		try {
			updateRows = roleMapper.updateSystemRoleSelected(obj);
			historyActionService.saveHistoryAction(obj, CommonConstant.ZERO_VALUE, CommonConstant.UPDATE_ACTION,
					obj.getSystemRoleId(), CommonConstant.TABLE_SYSTEM_ROLE, servletRequest);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, version, e.getCause().getMessage());
		}
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getFlexMessageLangUS("update.row", String.valueOf(updateRows)), null, null);
	}

	@Transactional
	public ResponseTemplate insertDocument(DocumentRequest request, HttpServletRequest servletRequest) {
		int inseartRows = CommonConstant.ZERO_VALUE;
		DocumentDto obj = request.getData().getDocument();
		if (ObjectUtils.isEmpty(obj) || StringUtils.isBlank(obj.getDocumentName())) {
			throw new NullPointRequestException(system, version,
					messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.DOCUMENT_INSERT_NOT_NULL));
		}
		try {
			inseartRows = documentMapper.insertDocument(obj);
			historyActionService.saveHistoryAction(obj, CommonConstant.ZERO_VALUE, CommonConstant.INSERT_ACTION,
					obj.getDocumentId(), CommonConstant.TABLE_DOCUMENT, servletRequest);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, version, e.getCause().getMessage());
		}
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getFlexMessageLangUS("insert.row", String.valueOf(inseartRows)), null, null);
	}

	@Transactional
	public ResponseTemplate updateDocument(DocumentRequest request, HttpServletRequest servletRequest) {
		int updateRows = CommonConstant.ZERO_VALUE;
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
			historyActionService.saveHistoryAction(obj, CommonConstant.ZERO_VALUE, CommonConstant.UPDATE_ACTION,
					obj.getDocumentId(), CommonConstant.TABLE_DOCUMENT, servletRequest);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, version, e.getCause().getMessage());
		}
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getFlexMessageLangUS("update.row", String.valueOf(updateRows)), null, null);
	}

	@Transactional
	public ResponseTemplate insertPosition(PositionRequest request, HttpServletRequest servletRequest) {
		int insertRows = CommonConstant.ZERO_VALUE;
		PositionDto obj = request.getData().getPosition();
		if (ObjectUtils.isEmpty(obj) || StringUtils.isBlank(obj.getPositionName())) {
			LogUtil.warn(
					messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.POSITION_INSERT_NOT_NULL));
			throw new NullPointRequestException(system, version,
					messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.POSITION_INSERT_NOT_NULL));
		}
		try {
			insertRows = positionMapper.insertPosition(obj);
			historyActionService.saveHistoryAction(obj, CommonConstant.ZERO_VALUE, CommonConstant.INSERT_ACTION,
					obj.getPositionId(), CommonConstant.TABLE_POSITION, servletRequest);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, version, e.getCause().getMessage());
		}
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getFlexMessageLangUS("insert.row", String.valueOf(insertRows)), null, null);
	}

	@Transactional
	public ResponseTemplate updatePosition(PositionRequest request, HttpServletRequest servletRequest) {
		int updateRows = CommonConstant.ZERO_VALUE;
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
			historyActionService.saveHistoryAction(obj, CommonConstant.ZERO_VALUE, CommonConstant.UPDATE_ACTION,
					obj.getPositionId(), CommonConstant.TABLE_POSITION, servletRequest);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, version, e.getCause().getMessage());
		}
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getFlexMessageLangUS("update.row", String.valueOf(updateRows)), null, null);
	}

	@Transactional
	public ResponseTemplate insertReason(ReasonRequest request, HttpServletRequest servletRequest) {
		int insertRows = CommonConstant.ZERO_VALUE;
		ReasonDto obj = request.getData().getReason();
		if (ObjectUtils.isEmpty(obj) || StringUtils.isBlank(obj.getReasonName())) {
			LogUtil.warn(messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.REASON_INSERT_NOT_NULL));
			throw new NullPointRequestException(system, version,
					messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.REASON_INSERT_NOT_NULL));
		}
		try {
			insertRows = reasonMapper.insertReason(obj);
			historyActionService.saveHistoryAction(obj, CommonConstant.ZERO_VALUE, CommonConstant.INSERT_ACTION,
					obj.getReasonId(), CommonConstant.TABLE_REASON, servletRequest);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, version, e.getCause().getMessage());
		}
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getFlexMessageLangUS("insert.row", String.valueOf(insertRows)), null, null);
	}

	@Transactional
	public ResponseTemplate updateReason(ReasonRequest request, HttpServletRequest servletRequest) {
		int updateRows = CommonConstant.ZERO_VALUE;
		ReasonDto obj = request.getData().getReason();
		obj.setUpdatedAt(DateUtil.getCurrentDayHourSecond());
		if (ObjectUtils.isEmpty(obj) || ObjectUtils.isEmpty(obj.getReasonId())) {
			LogUtil.warn(messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.REASON_UPDATE_NOT_NULL));
			throw new NullPointRequestException(system, version,
					messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.REASON_UPDATE_NOT_NULL));
		}
		try {
			updateRows = reasonMapper.updateReason(obj);
			historyActionService.saveHistoryAction(obj, CommonConstant.ZERO_VALUE, CommonConstant.UPDATE_ACTION,
					obj.getReasonId(), CommonConstant.TABLE_REASON, servletRequest);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, version, e.getCause().getMessage());
		}
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getFlexMessageLangUS("update.row", String.valueOf(updateRows)), null, null);
	}

	@Transactional
	public ResponseTemplate insertRequestType(RequestTypeRequest request, HttpServletRequest servletRequest) {
		int insertRows = CommonConstant.ZERO_VALUE;
		RequestTypeDto obj = request.getData().getRequestType();
		if (ObjectUtils.isEmpty(obj) || obj.getRequestTypeName().isBlank()) {
			LogUtil.warn(messageUtil.getFlexMessageLangUS("null.request.empty",
					CommonConstant.REQUEST_TYPE_INSERT_NOT_NULL));
			throw new NullPointRequestException(system, version, messageUtil.getFlexMessageLangUS("null.request.empty",
					CommonConstant.REQUEST_TYPE_INSERT_NOT_NULL));
		}
		try {
			insertRows = requestTypeMapper.insertRequestType(obj);
			historyActionService.saveHistoryAction(obj, CommonConstant.ZERO_VALUE, CommonConstant.INSERT_ACTION,
					obj.getRequestTypeId(), CommonConstant.TABLE_REQUEST_TYPE, servletRequest);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, version, e.getCause().getMessage());
		}
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getFlexMessageLangUS("insert.row", String.valueOf(insertRows)), null, null);
	}

	@Transactional
	public ResponseTemplate updateRequestType(RequestTypeRequest request, HttpServletRequest servletRequest) {
		int updateRows = CommonConstant.ZERO_VALUE;
		RequestTypeDto obj = request.getData().getRequestType();
		obj.setUpdatedAt(DateUtil.getCurrentDayHourSecond());
		if (ObjectUtils.isEmpty(obj) || ObjectUtils.isEmpty(obj.getRequestTypeId())) {
			LogUtil.warn(messageUtil.getFlexMessageLangUS("null.request.empty",
					CommonConstant.REQUEST_TYPE_UPDATE_NOT_NULL));
			throw new NullPointRequestException(system, version, messageUtil.getFlexMessageLangUS("null.request.empty",
					CommonConstant.REQUEST_TYPE_UPDATE_NOT_NULL));
		}
		try {
			updateRows = requestTypeMapper.updateRequestType(obj);
			historyActionService.saveHistoryAction(obj, CommonConstant.ZERO_VALUE, CommonConstant.UPDATE_ACTION,
					obj.getRequestTypeId(), CommonConstant.TABLE_REQUEST_TYPE, servletRequest);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, version, e.getCause().getMessage());
		}
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getFlexMessageLangUS("update.row", String.valueOf(updateRows)), null, null);
	}

	@Transactional
	public ResponseTemplate insertRoom(RoomRequest request, HttpServletRequest servletRequest) {
		int insertRows = CommonConstant.ZERO_VALUE;
		RoomDto obj = request.getData().getRoom();
		if (ObjectUtils.isEmpty(obj) || obj.getRoomName().isBlank()) {
			LogUtil.warn(messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.ROOM_INSERT_NOT_NULL));
			throw new NullPointRequestException(system, version,
					messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.ROOM_INSERT_NOT_NULL));
		}
		try {
			insertRows = roomMapper.insertRoom(obj);
			historyActionService.saveHistoryAction(obj, CommonConstant.ZERO_VALUE, CommonConstant.INSERT_ACTION,
					obj.getRoomId(), CommonConstant.TABLE_ROOM, servletRequest);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, version, e.getCause().getMessage());
		}
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getFlexMessageLangUS("insert.row", String.valueOf(insertRows)), null, null);
	}

	@Transactional
	public ResponseTemplate updateRoom(RoomRequest request, HttpServletRequest servletRequest) {
		int updateRows = CommonConstant.ZERO_VALUE;
		RoomDto obj = request.getData().getRoom();
		obj.setUpdatedAt(DateUtil.getCurrentDayHourSecond());
		if (ObjectUtils.isEmpty(obj) || ObjectUtils.isEmpty(obj.getRoomId())) {
			LogUtil.warn(messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.ROOM_UPDATE_NOT_NULL));
			throw new NullPointRequestException(system, version,
					messageUtil.getFlexMessageLangUS("null.request.empty", CommonConstant.ROOM_UPDATE_NOT_NULL));
		}
		try {
			updateRows = roomMapper.updateRoom(obj);
			historyActionService.saveHistoryAction(obj, CommonConstant.ZERO_VALUE, CommonConstant.UPDATE_ACTION,
					obj.getRoomId(), CommonConstant.TABLE_ROOM, servletRequest);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, version, e.getCause().getMessage());
		}
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getFlexMessageLangUS("update.row", String.valueOf(updateRows)), null, null);
	}

	public ResponseTemplate findRoles() {
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("get.data.success"), null, roleMapper.findRoles());
	}

	public ResponseTemplate findAllRoles() {
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("get.data.success"), null, roleMapper.findAllRoles());
	}

	public ResponseTemplate findAllAccounts() {
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("get.data.success"), null, systemAccountMapper.findAllAccount());
	}

	public ResponseTemplate findAllRooms() {
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("get.data.success"), null, roomMapper.findAllRooms());
	}

	public ResponseTemplate findAllDocuments() {
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("get.data.success"), null, documentMapper.findAllDocuments());
	}

	public ResponseTemplate findAvailbleAccounts() {
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("get.data.success"), null, systemAccountMapper.findAvailbleAccounts());
	}

	public ResponseTemplate findAllRequestType() {
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("get.data.success"), null, requestTypeMapper.findAllRequestType());
	}

	public ResponseTemplate findAllPositions() {
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("get.data.success"), null, positionMapper.findAllPositions());
	}

	public ResponseTemplate findAllReason() {
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("get.data.success"), null, reasonMapper.findAllReason());
	}

}
