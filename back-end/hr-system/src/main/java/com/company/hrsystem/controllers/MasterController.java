package com.company.hrsystem.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin
@RequestMapping(ApiUrlConstant.ROOT_API)
public class MasterController {

	@Autowired
	MasterService masterService;

	@PostMapping(ApiUrlConstant.MASTER_INSERT_ROLE)
	@PreAuthorize("hasRole('ROLE_ROOT_ADMIN')")
	public ResponseEntity<?> insertSystemRole(@RequestBody SystemRoleRequest request,
			HttpServletRequest servletRequest) {
		return ResponseEntity.ok(masterService.insertSystemRole(request, servletRequest));
	}

	@PostMapping(ApiUrlConstant.MASTER_UPDATE_ROLE)
	@PreAuthorize("hasRole('ROLE_ROOT_ADMIN')")
	public ResponseEntity<?> updateSystemRole(@RequestBody SystemRoleRequest request,
			HttpServletRequest servletRequest) {
		return ResponseEntity.ok(masterService.updateSystemRole(request, servletRequest));
	}

	@PostMapping(ApiUrlConstant.MASTER_INSERT_DOCUMENT)
	@PreAuthorize("hasRole('ROLE_ROOT_ADMIN')")
	public ResponseEntity<?> insertDocument(@RequestBody DocumentRequest request, HttpServletRequest servletRequest) {
		return ResponseEntity.ok(masterService.insertDocument(request, servletRequest));
	}

	@PostMapping(ApiUrlConstant.MASTER_UPDATE_DOCUMENT)
	@PreAuthorize("hasRole('ROLE_ROOT_ADMIN')")
	public ResponseEntity<?> updateDocument(@RequestBody DocumentRequest request, HttpServletRequest servletRequest) {
		return ResponseEntity.ok(masterService.updateDocument(request, servletRequest));
	}

	@PostMapping(ApiUrlConstant.MASTER_INSERT_POSITION)
	@PreAuthorize("hasRole('ROLE_ROOT_ADMIN')")
	public ResponseEntity<?> insertPosition(@RequestBody PositionRequest request, HttpServletRequest servletRequest) {
		return ResponseEntity.ok(masterService.insertPosition(request, servletRequest));
	}

	@PostMapping(ApiUrlConstant.MASTER_UPDATE_POSITION)
	@PreAuthorize("hasRole('ROLE_ROOT_ADMIN')")
	public ResponseEntity<?> updatePosition(@RequestBody PositionRequest request, HttpServletRequest servletRequest) {
		return ResponseEntity.ok(masterService.updatePosition(request, servletRequest));
	}

	@PostMapping(ApiUrlConstant.MASTER_INSERT_REASON)
	@PreAuthorize("hasRole('ROLE_ROOT_ADMIN')")
	public ResponseEntity<?> insertReason(@RequestBody ReasonRequest request, HttpServletRequest servletRequest) {
		return ResponseEntity.ok(masterService.insertReason(request, servletRequest));
	}

	@PostMapping(ApiUrlConstant.MASTER_UPDATE_REASON)
	@PreAuthorize("hasRole('ROLE_ROOT_ADMIN')")
	public ResponseEntity<?> updateReason(@RequestBody ReasonRequest request, HttpServletRequest servletRequest) {
		return ResponseEntity.ok(masterService.updateReason(request, servletRequest));
	}

	@PostMapping(ApiUrlConstant.MASTER_INSERT_REQUEST_TYPE)
	@PreAuthorize("hasRole('ROLE_ROOT_ADMIN')")
	public ResponseEntity<?> insertRequestType(@RequestBody RequestTypeRequest request,
			HttpServletRequest servletRequest) {
		return ResponseEntity.ok(masterService.insertRequestType(request, servletRequest));
	}

	@PostMapping(ApiUrlConstant.MASTER_UPDATE_REQUEST_TYPE)
	@PreAuthorize("hasRole('ROLE_ROOT_ADMIN')")
	public ResponseEntity<?> updateRequestType(@RequestBody RequestTypeRequest request,
			HttpServletRequest servletRequest) {
		return ResponseEntity.ok(masterService.updateRequestType(request, servletRequest));
	}

	@PostMapping(ApiUrlConstant.MASTER_INSERT_ROOM)
	@PreAuthorize("hasRole('ROLE_ROOT_ADMIN')")
	public ResponseEntity<?> insertRoom(@RequestBody RoomRequest request, HttpServletRequest servletRequest) {
		return ResponseEntity.ok(masterService.insertRoom(request, servletRequest));
	}

	@PostMapping(ApiUrlConstant.MASTER_UPDATE_ROOM)
	@PreAuthorize("hasRole('ROLE_ROOT_ADMIN')")
	public ResponseEntity<?> updateRoom(@RequestBody RoomRequest request, HttpServletRequest servletRequest) {
		return ResponseEntity.ok(masterService.updateRoom(request, servletRequest));
	}

	@PostMapping(ApiUrlConstant.MASTER_FIND_ROLES)
	@PreAuthorize("hasAnyRole('ROLE_ROOT_ADMIN', 'ROLE_ADMIN')")
	public ResponseEntity<?> findRoles() {
		return ResponseEntity.ok(masterService.findRoles());
	}

	@PostMapping(ApiUrlConstant.MASTER_FIND_ALL_ROLES)
	@PreAuthorize("hasAnyRole('ROLE_ROOT_ADMIN')")
	public ResponseEntity<?> findAllRoles() {
		return ResponseEntity.ok(masterService.findAllRoles());
	}

	@PostMapping(ApiUrlConstant.MASTER_FIND_ALL_ACCOUNTS)
	@PreAuthorize("hasAnyRole('ROLE_ROOT_ADMIN', 'ROLE_ADMIN', 'ROLE_HR')")
	public ResponseEntity<?> findAllAccounts() {
		return ResponseEntity.ok(masterService.findAllAccounts());
	}

	@PostMapping(ApiUrlConstant.MASTER_FIND_ALL_ROOM)
	@PreAuthorize("hasRole('ROLE_ROOT_ADMIN')")
	public ResponseEntity<?> findAllRooms() {
		return ResponseEntity.ok(masterService.findAllRooms());
	}

	@PostMapping(ApiUrlConstant.MASTER_FIND_ALL_DOCUMENTS)
	@PreAuthorize("hasRole('ROLE_ROOT_ADMIN')")
	public ResponseEntity<?> findAllDocuments() {
		return ResponseEntity.ok(masterService.findAllDocuments());
	}

	@PostMapping(ApiUrlConstant.MASTER_FIND_AVAILABLE_ACCOUNTS)
	@PreAuthorize("hasAnyRole('ROLE_ROOT_ADMIN', 'ROLE_ADMIN', 'ROLE_HR')")
	public ResponseEntity<?> findAvailbleAccounts() {
		return ResponseEntity.ok(masterService.findAvailbleAccounts());
	}

	@PostMapping(ApiUrlConstant.MASTER_FIND_ALL_REQUEST_TYPE)
	@PreAuthorize("hasRole('ROLE_ROOT_ADMIN')")
	public ResponseEntity<?> findAllRequestType() {
		return ResponseEntity.ok(masterService.findAllRequestType());
	}

	@PostMapping(ApiUrlConstant.MASTER_FIND_ALL_POSITIONS)
	@PreAuthorize("hasRole('ROLE_ROOT_ADMIN')")
	public ResponseEntity<?> findAllPositions() {
		return ResponseEntity.ok(masterService.findAllPositions());
	}

	@PostMapping(ApiUrlConstant.MASTER_FIND_ALL_REASON)
	@PreAuthorize("hasRole('ROLE_ROOT_ADMIN')")
	public ResponseEntity<?> findAllReason() {
		return ResponseEntity.ok(masterService.findAllReason());
	}

}