package com.company.hrsystem.service.interfaces;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.company.hrsystem.request.FindListEmployeesRequest;
import com.company.hrsystem.request.FindListTicketRequest;
import com.company.hrsystem.response.ResponseTemplate;

public interface IHumanResourceService {

	public ResponseTemplate insertEmployee(String jsonString, MultipartFile multipartFile,
			HttpServletRequest servletRequest);

	public ResponseTemplate updateEmployee(String jsonString, MultipartFile multipartFile,
			HttpServletRequest servletRequest);

	public ResponseTemplate findListEmployees(FindListEmployeesRequest request);

	public ResponseTemplate findEmployeeById(Integer id);

	public ResponseTemplate findPositions();

	public ResponseTemplate findDocuments();

	public ResponseTemplate findRooms();

	public ResponseTemplate findReportCaseSelected(FindListTicketRequest request);

}
