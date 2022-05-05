package com.company.hrsystem.service;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.company.hrsystem.Exeption.GlobalException;
import com.company.hrsystem.dto.PersonalInfoDto;
import com.company.hrsystem.mapper.EmployeeDocumentMapper;
import com.company.hrsystem.mapper.EmployeeMapper;
import com.company.hrsystem.mapper.EmployeePositionMapper;
import com.company.hrsystem.mapper.PersonnalInfoMapper;
import com.company.hrsystem.request.EmployeeRequest;
import com.company.hrsystem.response.ResponseTemplate;
import com.company.hrsystem.utils.LogUtil;
import com.company.hrsystem.utils.MessageUtil;

@Service
public class HumanResourceService {

	@Value("${system.name}")
	private String system;

	@Value("${system.version}")
	private String version;

	@Autowired
	EmployeeMapper employeeMapper;

	@Autowired
	PersonnalInfoMapper personnalInfoMapper;

	@Autowired
	EmployeeDocumentMapper employeeDocumentMapper;

	@Autowired
	EmployeePositionMapper employeePositionMapper;

	@Autowired
	MessageUtil messageUtil;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public ResponseTemplate insertEmployee(EmployeeRequest request) {
		try {
			PersonalInfoDto personalInfo = request.getData().getPersonalInfo();
			personnalInfoMapper.insertPersonalInfo(personalInfo);
			employeeMapper.insertEmployee(request.getData());
			employeeDocumentMapper.insertEmployeeDocument(request.getData(), request.getData().getDocumentIds());
			employeePositionMapper.insertEmployeePosition(request.getData(), request.getData().getPositions());
			return new ResponseTemplate(system, version, HttpStatus.OK.value(),
					messageUtil.getMessagelangUS("insert.employee.success"), null, null);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, version, e.getCause().getMessage());
		}
	}
}
