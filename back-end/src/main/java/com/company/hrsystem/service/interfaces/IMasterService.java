package com.company.hrsystem.service.interfaces;

import javax.servlet.http.HttpServletRequest;

import com.company.hrsystem.request.DocumentRequest;
import com.company.hrsystem.request.PositionRequest;
import com.company.hrsystem.request.ReasonRequest;
import com.company.hrsystem.request.RequestTypeRequest;
import com.company.hrsystem.request.RoomRequest;
import com.company.hrsystem.request.SystemRoleRequest;
import com.company.hrsystem.response.ResponseData;

public interface IMasterService {

	public ResponseData insertSystemRole(SystemRoleRequest request, HttpServletRequest servletRequest);

	public ResponseData updateSystemRole(SystemRoleRequest request, HttpServletRequest servletRequest);

	public ResponseData insertDocument(DocumentRequest request, HttpServletRequest servletRequest);

	public ResponseData updateDocument(DocumentRequest request, HttpServletRequest servletRequest);

	public ResponseData insertPosition(PositionRequest request, HttpServletRequest servletRequest);

	public ResponseData updatePosition(PositionRequest request, HttpServletRequest servletRequest);

	public ResponseData insertReason(ReasonRequest request, HttpServletRequest servletRequest);

	public ResponseData updateReason(ReasonRequest request, HttpServletRequest servletRequest);

	public ResponseData insertRequestType(RequestTypeRequest request, HttpServletRequest servletRequest);

	public ResponseData updateRequestType(RequestTypeRequest request, HttpServletRequest servletRequest);

	public ResponseData insertRoom(RoomRequest request, HttpServletRequest servletRequest);

	public ResponseData updateRoom(RoomRequest request, HttpServletRequest servletRequest);

	public ResponseData findRoles();

	public ResponseData findAllRoles();

	public ResponseData findAllAccounts();

	public ResponseData findAllRooms();

	public ResponseData findAllDocuments();

	public ResponseData findAvailbleAccounts();

	public ResponseData findAllRequestType();

	public ResponseData findAllPositions();

	public ResponseData findAllReason();

}
