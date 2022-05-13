package com.company.hrsystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@PostMapping(ApiUrlConstant.HUMAN_RESOURCE_INSERT_EMPLOYEE)
	@PreAuthorize("hasRole('ROLE_HR')")
	public ResponseEntity<?> insertEmployee(@RequestBody EmployeeRequest request) {
		return ResponseEntity.ok(humanResourceService.insertEmployee(request));
	}

	@PostMapping(ApiUrlConstant.HUMAN_RESOURCE_SEARCH_ALL_EMPLOYEES)
	@PreAuthorize("hasRole('ROLE_HR')")
	public ResponseEntity<?> findListEmployees(@RequestBody FindListEmployeesRequest request) {
		return ResponseEntity.ok(humanResourceService.findListEmployees(request));
	}
	
	@GetMapping(ApiUrlConstant.HUMAN_RESOURCE_SEARCH_EMPLOYEE)
	@PreAuthorize("hasRole('ROLE_HR')")
	public ResponseEntity<?> findEmployeeById(@PathVariable(required = true) String id) {
		return ResponseEntity.ok(humanResourceService.findEmployeeById(id));
	}
	
	@PostMapping(ApiUrlConstant.HUMAN_RESOURCE_UPDATE_EMPLOYEE)
	@PreAuthorize("hasRole('ROLE_HR')")
	public ResponseEntity<?> updateEmployee(@RequestBody EmployeeRequest request) {
		return ResponseEntity.ok(humanResourceService.updateEmployee(request));
	}

}
