package com.company.hrsystem.serviceInterface;

import javax.servlet.http.HttpServletRequest;

import com.company.hrsystem.request.DocumentRequest;
import com.company.hrsystem.request.PositionRequest;
import com.company.hrsystem.request.ReasonRequest;
import com.company.hrsystem.request.RequestTypeRequest;
import com.company.hrsystem.request.RoomRequest;
import com.company.hrsystem.request.SystemRoleRequest;
import com.company.hrsystem.response.ResponseTemplate;

public interface MasterServiceInterface {

	public ResponseTemplate insertSystemRole(SystemRoleRequest request, HttpServletRequest servletRequest);

	public ResponseTemplate updateSystemRole(SystemRoleRequest request, HttpServletRequest servletRequest);

	public ResponseTemplate insertDocument(DocumentRequest request, HttpServletRequest servletRequest);

	public ResponseTemplate updateDocument(DocumentRequest request, HttpServletRequest servletRequest);

	public ResponseTemplate insertPosition(PositionRequest request, HttpServletRequest servletRequest);

	public ResponseTemplate updatePosition(PositionRequest request, HttpServletRequest servletRequest);

	public ResponseTemplate insertReason(ReasonRequest request, HttpServletRequest servletRequest);

	public ResponseTemplate updateReason(ReasonRequest request, HttpServletRequest servletRequest);

	public ResponseTemplate insertRequestType(RequestTypeRequest request, HttpServletRequest servletRequest);

	public ResponseTemplate updateRequestType(RequestTypeRequest request, HttpServletRequest servletRequest);

	public ResponseTemplate insertRoom(RoomRequest request, HttpServletRequest servletRequest);

	public ResponseTemplate updateRoom(RoomRequest request, HttpServletRequest servletRequest);

	public ResponseTemplate findRoles();

	public ResponseTemplate findAllRoles();

	public ResponseTemplate findAllAccounts();

	public ResponseTemplate findAllRooms();

	public ResponseTemplate findAllDocuments();

	public ResponseTemplate findAvailbleAccounts();

	public ResponseTemplate findAllRequestType();

	public ResponseTemplate findAllPositions();

	public ResponseTemplate findAllReason();

}
