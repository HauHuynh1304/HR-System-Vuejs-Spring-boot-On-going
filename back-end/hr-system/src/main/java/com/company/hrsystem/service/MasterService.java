package com.company.hrsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
import com.company.hrsystem.utils.MessageUtil;

@Service
public class MasterService {

	@Value("${system.name}")
	private String sytem;

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

	@Transactional(propagation = Propagation.REQUIRED)
	public ResponseTemplate insertSystemRole(SystemRoleRequest request) {
		roleMapper.insertSystemRoleSelected(request.getData());
		return new ResponseTemplate(sytem, verion, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("insert.role.success"), null, null);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ResponseTemplate updateSystemRole(SystemRoleRequest request) {
		request.getData().setUpdatedAt(DateUtil.getCurrentDayHourSecond());
		roleMapper.updateSystemRoleSelected(request.getData());
		return new ResponseTemplate(sytem, verion, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("udpate.role.success"), null, null);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ResponseTemplate insertDocument(DocumentRequest request) {
		documentMapper.insertDocument(request.getData());
		return new ResponseTemplate(sytem, verion, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("insert.document.success"), null, null);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ResponseTemplate updateDocument(DocumentRequest request) {
		request.getData().setUpdatedAt(DateUtil.getCurrentDayHourSecond());
		documentMapper.updateDocument(request.getData());
		return new ResponseTemplate(sytem, verion, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("udpate.document.success"), null, null);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ResponseTemplate insertPosition(PositionRequest request) {
		positionMapper.insertPosition(request.getData());
		return new ResponseTemplate(sytem, verion, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("insert.position.success"), null, null);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ResponseTemplate updatePosition(PositionRequest request) {
		request.getData().setUpdatedAt(DateUtil.getCurrentDayHourSecond());
		positionMapper.updatePosition(request.getData());
		return new ResponseTemplate(sytem, verion, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("udpate.position.success"), null, null);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ResponseTemplate insertReason(ReasonRequest request) {
		reasonMapper.insertReason(request.getData());
		return new ResponseTemplate(sytem, verion, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("insert.reason.success"), null, null);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ResponseTemplate updateReason(ReasonRequest request) {
		request.getData().setUpdatedAt(DateUtil.getCurrentDayHourSecond());
		reasonMapper.updateReason(request.getData());
		return new ResponseTemplate(sytem, verion, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("udpate.reason.success"), null, null);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ResponseTemplate insertRequestType(RequestTypeRequest request) {
		requestTypeMapper.insertRequestType(request.getData());
		return new ResponseTemplate(sytem, verion, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("insert.request.type.success"), null, null);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ResponseTemplate updateRequestType(RequestTypeRequest request) {
		request.getData().setUpdatedAt(DateUtil.getCurrentDayHourSecond());
		requestTypeMapper.insertRequestType(request.getData());
		return new ResponseTemplate(sytem, verion, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("udpate.request.type.success"), null, null);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ResponseTemplate insertRoom(RoomRequest request) {
		roomMapper.insertRoom(request.getData());
		return new ResponseTemplate(sytem, verion, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("insert.room.success"), null, null);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ResponseTemplate updateRoom(RoomRequest request) {
		request.getData().setUpdatedAt(DateUtil.getCurrentDayHourSecond());
		roomMapper.insertRoom(request.getData());
		return new ResponseTemplate(sytem, verion, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("udpate.room.success"), null, null);
	}

}
