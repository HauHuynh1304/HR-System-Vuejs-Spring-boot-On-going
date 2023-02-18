package com.company.hrsystem.service.interfaces;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.company.hrsystem.request.FindListEmployeesRequest;
import com.company.hrsystem.request.FindListTicketRequest;
import com.company.hrsystem.response.ResponseData;

public interface IHumanResourceService {

	public ResponseData insertEmployee(String jsonString, MultipartFile multipartFile,
			HttpServletRequest servletRequest);

	public ResponseData updateEmployee(String jsonString, MultipartFile multipartFile,
			HttpServletRequest servletRequest);

	public ResponseData findListEmployees(FindListEmployeesRequest request);

	public ResponseData findEmployeeById(Integer id);

	public ResponseData findPositions();

	public ResponseData findDocuments();

	public ResponseData findRooms();

	public ResponseData findReportCaseSelected(FindListTicketRequest request);

}
