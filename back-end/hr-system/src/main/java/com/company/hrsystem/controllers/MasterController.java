package com.company.hrsystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.hrsystem.constants.ApiUrlConstant;
import com.company.hrsystem.request.DocumentRequest;
import com.company.hrsystem.request.PositionRequest;
import com.company.hrsystem.request.ReasonRequest;
import com.company.hrsystem.request.RequestTypeRequest;
import com.company.hrsystem.request.RoomRequest;
import com.company.hrsystem.request.SystemRoleRequest;
import com.company.hrsystem.service.MasterService;

@RestController
@RequestMapping(ApiUrlConstant.ROOT_API)
public class MasterController {

	@Autowired
	MasterService masterService;

	@PostMapping(ApiUrlConstant.MASTER_INSERT_ROLE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> insertSystemRole(@RequestBody SystemRoleRequest request) {
		return ResponseEntity.ok(masterService.insertSystemRole(request));
	}

	@PostMapping(ApiUrlConstant.MASTER_UPDATE_ROLE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> updateSystemRole(@RequestBody SystemRoleRequest request) {
		return ResponseEntity.ok(masterService.updateSystemRole(request));
	}

	@PostMapping(ApiUrlConstant.MASTER_INSERT_DOCUMENT)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> insertDocument(@RequestBody DocumentRequest request) {
		return ResponseEntity.ok(masterService.insertDocument(request));
	}

	@PostMapping(ApiUrlConstant.MASTER_UPDATE_DOCUMENT)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> updateDocument(@RequestBody DocumentRequest request) {
		return ResponseEntity.ok(masterService.updateDocument(request));
	}

	@PostMapping(ApiUrlConstant.MASTER_INSERT_POSITION)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> insertPosition(@RequestBody PositionRequest request) {
		return ResponseEntity.ok(masterService.insertPosition(request));
	}

	@PostMapping(ApiUrlConstant.MASTER_UPDATE_POSITION)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> updatePosition(@RequestBody PositionRequest request) {
		return ResponseEntity.ok(masterService.updatePosition(request));
	}

	@PostMapping(ApiUrlConstant.MASTER_INSERT_REASON)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> insertReason(@RequestBody ReasonRequest request) {
		return ResponseEntity.ok(masterService.insertReason(request));
	}

	@PostMapping(ApiUrlConstant.MASTER_UPDATE_REASON)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> updateReason(@RequestBody ReasonRequest request) {
		return ResponseEntity.ok(masterService.updateReason(request));
	}

	@PostMapping(ApiUrlConstant.MASTER_INSERT_REQUEST_TYPE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> insertRequestType(@RequestBody RequestTypeRequest request) {
		return ResponseEntity.ok(masterService.insertRequestType(request));
	}

	@PostMapping(ApiUrlConstant.MASTER_UPDATE_REQUEST_TYPE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> updateRequestType(@RequestBody RequestTypeRequest request) {
		return ResponseEntity.ok(masterService.updateRequestType(request));
	}

	@PostMapping(ApiUrlConstant.MASTER_INSERT_ROOM)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> insertRoom(@RequestBody RoomRequest request) {
		return ResponseEntity.ok(masterService.insertRoom(request));
	}

	@PostMapping(ApiUrlConstant.MASTER_UPDATE_ROOM)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> updateRoom(@RequestBody RoomRequest request) {
		return ResponseEntity.ok(masterService.updateRoom(request));
	}

}
