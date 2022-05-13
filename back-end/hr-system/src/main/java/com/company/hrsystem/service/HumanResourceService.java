package com.company.hrsystem.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.company.hrsystem.Exeption.GlobalException;
import com.company.hrsystem.dto.EmployeeDocumentDto;
import com.company.hrsystem.dto.EmployeeDto;
import com.company.hrsystem.dto.EmployeePositionDto;
import com.company.hrsystem.dto.PersonalInfoDto;
import com.company.hrsystem.mapper.EmployeeDocumentMapper;
import com.company.hrsystem.mapper.EmployeeMapper;
import com.company.hrsystem.mapper.EmployeePositionMapper;
import com.company.hrsystem.mapper.PersonnalInfoMapper;
import com.company.hrsystem.request.EmployeeRequest;
import com.company.hrsystem.request.FindListEmployeesRequest;
import com.company.hrsystem.response.FindEmployeeResponse;
import com.company.hrsystem.response.FindListEmployeesResponse;
import com.company.hrsystem.response.ResponseTemplate;
import com.company.hrsystem.utils.DateUtil;
import com.company.hrsystem.utils.LogUtil;
import com.company.hrsystem.utils.MessageUtil;
import com.company.hrsystem.utils.ObjectUtil;

@Service
public class HumanResourceService {

	@Value("${system.name}")
	private String system;

	@Value("${system.version}")
	private String version;

	@Autowired
	private EmployeeMapper employeeMapper;

	@Autowired
	private PersonnalInfoMapper personnalInfoMapper;

	@Autowired
	private EmployeeDocumentMapper employeeDocumentMapper;

	@Autowired
	private EmployeePositionMapper employeePositionMapper;

	@Autowired
	private MessageUtil messageUtil;
	
	@Autowired
	private ObjectUtil objectUtil;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public ResponseTemplate insertEmployee(EmployeeRequest request) {
		try {
			PersonalInfoDto personalInfo = request.getData().getPersonalInfo();
			personnalInfoMapper.insertPersonalInfo(personalInfo);
			employeeMapper.insertEmployee(personalInfo, request.getData().getEmployee());
			employeeDocumentMapper.insertEmployeeDocument(request.getData().getEmployee(),
					request.getData().getDocuments());
			employeePositionMapper.insertEmployeePosition(request.getData().getEmployee(),
					request.getData().getPositions());
			return new ResponseTemplate(system, version, HttpStatus.OK.value(),
					messageUtil.getMessagelangUS("insert.employee.success"), null, null);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, version, messageUtil.getMessagelangUS("value.not.correct"));
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public ResponseTemplate updateEmployee(EmployeeRequest request) {
		EmployeeDto employee = request.getData().getEmployee();
		PersonalInfoDto personalInfo = request.getData().getPersonalInfo();
		List<EmployeeDocumentDto> documents = request.getData().getDocuments();
		List<EmployeePositionDto> positions = request.getData().getPositions();
		String updatedAt = DateUtil.getCurrentDayHourSecond();
		Map<String, Object> map = objectUtil.objectToMap(employee);
		int countNotNull = 0;
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if (ObjectUtils.isNotEmpty(entry.getValue())) {
				countNotNull++;
			}
		}
		try {
			/*
			 * In EmployeeRequest, parameter employeeId is always not null then it will call
			 * API update employee if only use ObjectUtil.isEmpty(employee)
			 * So, update employee's API will be executive if not null parameter greater than 1
			 */
			if (countNotNull > 1) {
				employee.setUpdatedAt(updatedAt);
				employeeMapper.updateEmployee(employee);
			}

			if (ObjectUtils.isNotEmpty(personalInfo)) {
				personalInfo.setUpdatedAt(updatedAt);
				personnalInfoMapper.updatePersonalInfo(personalInfo);
			}

			if (ObjectUtils.isNotEmpty(documents)) {
				List<EmployeeDocumentDto> oldDocuments = new ArrayList<EmployeeDocumentDto>();
				List<EmployeeDocumentDto> newDocuments = new ArrayList<EmployeeDocumentDto>();
				documents.stream().forEach(e -> {
					if (ObjectUtils.isNotEmpty(e.getEmployeeDocumentId())) {
						oldDocuments.add(e);
					} else {
						newDocuments.add(e);
					}
				});
				if (ObjectUtils.isNotEmpty(oldDocuments)) {
					employeeDocumentMapper.updateEmployeeDocument(oldDocuments, updatedAt);
				}
				if (ObjectUtils.isNotEmpty(newDocuments)) {
					employeeDocumentMapper.insertEmployeeDocument(employee, newDocuments);
				}
			}

			if (ObjectUtils.isNotEmpty(positions)) {
				List<EmployeePositionDto> oldPositions = new ArrayList<EmployeePositionDto>();
				List<EmployeePositionDto> newPositions = new ArrayList<EmployeePositionDto>();
				positions.stream().forEach(e -> {
					if (ObjectUtils.isNotEmpty(e.getEmployeePositionId())) {
						oldPositions.add(e);
					} else {
						newPositions.add(e);
					}
				});
				if (ObjectUtils.isNotEmpty(oldPositions)) {
					employeePositionMapper.updateEmployeePosition(oldPositions, updatedAt);
				}
				if (ObjectUtils.isNotEmpty(newPositions)) {
					employeePositionMapper.insertEmployeePosition(employee, newPositions);
				}
			}

			return new ResponseTemplate(system, version, HttpStatus.OK.value(),
					messageUtil.getMessagelangUS("udpate.employee.success"), null, null);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, version, messageUtil.getMessagelangUS("value.not.correct"));
		}
	}

	public ResponseTemplate findListEmployees(FindListEmployeesRequest request) {
		List<FindListEmployeesResponse> listEmployees = employeeMapper.findListEmployees(request.getData());
		if (ObjectUtils.isEmpty(listEmployees)) {
			return new ResponseTemplate(system, version, HttpStatus.OK.value(),
					messageUtil.getFlexMessageLangUS("get.data", String.valueOf(listEmployees.size())), null, null);
		}
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getFlexMessageLangUS("get.data", String.valueOf(listEmployees.size())), null,
				listEmployees);
	}

	public ResponseTemplate findEmployeeById(String id) {
		FindEmployeeResponse info = employeeMapper.findEmployeeById(id);
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("get.data.success"), null, info);
	}

}
