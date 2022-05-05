package com.company.hrsystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.hrsystem.constants.ApiUrlConstant;
import com.company.hrsystem.request.EmployeeRequest;
import com.company.hrsystem.service.HumanResourceService;

@RestController
@RequestMapping(ApiUrlConstant.ROOT_API)
public class HumanResourceController {

	@Autowired
	HumanResourceService humanResourceService;

	@PostMapping(ApiUrlConstant.HUMAN_RESOURCE_INSERT_EMPLOYEE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> insertEmployee(@RequestBody EmployeeRequest request) {
		return ResponseEntity.ok(humanResourceService.insertEmployee(request));
	}

}
