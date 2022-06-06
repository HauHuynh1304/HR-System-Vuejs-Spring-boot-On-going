package com.company.hrsystem.controllers;

import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.company.hrsystem.constants.ApiUrlConstant;
import com.company.hrsystem.request.EmployeeRequest;
import com.company.hrsystem.request.FindListEmployeesRequest;
import com.company.hrsystem.service.HumanResourceService;

@RestController
@CrossOrigin
@RequestMapping(ApiUrlConstant.ROOT_API)
public class HumanResourceController {

	@Autowired
	HumanResourceService humanResourceService;

	@PostMapping(value = ApiUrlConstant.HUMAN_RESOURCE_INSERT_EMPLOYEE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_HR')")
	public ResponseEntity<?> insertEmployee(@RequestPart("formEmployee") EmployeeRequest request,
			@RequestPart(name = "image", required = false) MultipartFile multipartFile,
			HttpServletRequest servletRequest) {
		return ResponseEntity.ok(humanResourceService.insertEmployee(request, multipartFile, servletRequest));
	}

	@PostMapping(ApiUrlConstant.HUMAN_RESOURCE_SEARCH_ALL_EMPLOYEES)
	@PreAuthorize("hasRole('ROLE_HR')")
	public ResponseEntity<?> findListEmployees(@RequestBody FindListEmployeesRequest request) {
		return ResponseEntity.ok(humanResourceService.findListEmployees(request));
	}

	@GetMapping(ApiUrlConstant.HUMAN_RESOURCE_SEARCH_EMPLOYEE)
	@PreAuthorize("hasRole('ROLE_HR')")
	public ResponseEntity<?> findEmployeeById(@PathVariable(required = true) Integer id) {
		return ResponseEntity.ok(humanResourceService.findEmployeeById(id));
	}

	@PostMapping(value = ApiUrlConstant.HUMAN_RESOURCE_UPDATE_EMPLOYEE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_HR')")
	public ResponseEntity<?> updateEmployee(@RequestPart("formEmployee") EmployeeRequest request,
			@RequestPart(name = "image", required = false) MultipartFile multipartFile,
			HttpServletRequest servletRequest) {
		return ResponseEntity.ok(humanResourceService.updateEmployee(request, multipartFile, servletRequest));
	}

}
