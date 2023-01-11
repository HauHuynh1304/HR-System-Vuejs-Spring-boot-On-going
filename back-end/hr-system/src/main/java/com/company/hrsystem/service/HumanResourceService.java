package com.company.hrsystem.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.company.hrsystem.Exeption.GlobalException;
import com.company.hrsystem.annotations.WriteLogToDB;
import com.company.hrsystem.config.SystemProperties;
import com.company.hrsystem.dto.EmployeeDocumentDto;
import com.company.hrsystem.dto.EmployeeDto;
import com.company.hrsystem.dto.EmployeePositionDto;
import com.company.hrsystem.dto.PersonalInfoDto;
import com.company.hrsystem.mapper.Impl.DocumentMapperImpl;
import com.company.hrsystem.mapper.Impl.EmployeeDocumentMapperImpl;
import com.company.hrsystem.mapper.Impl.EmployeeMapperImpl;
import com.company.hrsystem.mapper.Impl.EmployeePositionMapperImpl;
import com.company.hrsystem.mapper.Impl.PersonnalInfoMapperImpl;
import com.company.hrsystem.mapper.Impl.PositionMapperImpl;
import com.company.hrsystem.mapper.Impl.RequestEmployeeMapperImpl;
import com.company.hrsystem.mapper.Impl.RoomMapperImpl;
import com.company.hrsystem.request.EmployeeRequest;
import com.company.hrsystem.request.FindListEmployeesRequest;
import com.company.hrsystem.request.FindListTicketRequest;
import com.company.hrsystem.response.FindEmployeeResponse;
import com.company.hrsystem.response.FindListEmployeesResponse;
import com.company.hrsystem.response.FindReportCaseSelectedResponse;
import com.company.hrsystem.response.ResponseTemplate;
import com.company.hrsystem.serviceInterface.HumanResourceServiceInterface;
import com.company.hrsystem.utils.DateUtil;
import com.company.hrsystem.utils.FileUtil;
import com.company.hrsystem.utils.LogUtil;
import com.company.hrsystem.utils.MessageUtil;
import com.company.hrsystem.utils.ObjectUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class HumanResourceService implements HumanResourceServiceInterface {

	@Autowired
	private EmployeeMapperImpl employeeMapperImpl;

	@Autowired
	private PersonnalInfoMapperImpl personnalInfoMapperImpl;

	@Autowired
	private EmployeeDocumentMapperImpl employeeDocumentMapperImpl;

	@Autowired
	private EmployeePositionMapperImpl employeePositionMapperImpl;

	@Autowired
	private PositionMapperImpl positionMapperImpl;

	@Autowired
	private DocumentMapperImpl documentMapperImpl;

	@Autowired
	private RoomMapperImpl roomMapperImpl;

	@Autowired
	private RequestEmployeeMapperImpl requestEmployeeMapperImpl;

	// TODO: add annotation sendEmail
	@Transactional
	@WriteLogToDB
	public ResponseTemplate insertEmployee(String jsonString, MultipartFile multipartFile,
			HttpServletRequest servletRequest) {
		Gson gson = new GsonBuilder().setDateFormat(DateUtil.DAY).create();
		EmployeeRequest request = gson.fromJson(jsonString, EmployeeRequest.class);
		String fileName = null;
		try {
			if (multipartFile != null) {
				fileName = FileUtil.generateFileName(multipartFile);
			}

			PersonalInfoDto personalInfo = request.getPersonalInfo();
			personalInfo.setPersonalImage(fileName);
			personnalInfoMapperImpl.insertPersonalInfo(personalInfo);
			
			EmployeeDto employeeDto = request.getEmployee();
			employeeMapperImpl.insertEmployee(personalInfo, employeeDto);
			
			List<EmployeeDocumentDto> listEmployeeDocument = request.getDocuments();
			if (ObjectUtils.isNotEmpty(listEmployeeDocument)) {
				employeeDocumentMapperImpl.insertEmployeeDocument(employeeDto, listEmployeeDocument);
			}

			List<EmployeePositionDto> listEmployeePosition = request.getPositions();
			employeePositionMapperImpl.insertEmployeePosition(employeeDto, listEmployeePosition);
			
			if (!StringUtils.isAllEmpty(fileName)) {
				try {
					FileUtil.saveFile(FileUtil.generateUploadDir(SystemProperties.PATH_SAVE_EMPLOYEE_IMAGE,
							personalInfo.getPersonalInfoId()), fileName, multipartFile);
				} catch (IOException e) {
					LogUtil.error(ExceptionUtils.getStackTrace(e));
					throw new GlobalException(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
							MessageUtil.getMessagelangUS("value.not.correct"));
				}
			}
			return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					HttpStatus.OK.value(), MessageUtil.getMessagelangUS("insert.employee.success"), null, null);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					MessageUtil.getMessagelangUS("value.not.correct"));
		}
	}

	@Transactional
	@WriteLogToDB
	public ResponseTemplate updateEmployee(String jsonString, MultipartFile multipartFile,
			HttpServletRequest servletRequest) {
		Gson gson = new GsonBuilder().setDateFormat(DateUtil.DAY).create();
		EmployeeRequest request = gson.fromJson(jsonString, EmployeeRequest.class);
		EmployeeDto employee = request.getEmployee();
		PersonalInfoDto personalInfo = request.getPersonalInfo();
		List<EmployeeDocumentDto> documents = request.getDocuments();
		List<EmployeePositionDto> positions = request.getPositions();
		Timestamp updatedAt = DateUtil.getCurrentDayHourSecond();

		try {
			/*
			 * In EmployeeRequest, parameter employeeId is always not null then it will call
			 * API update employee if only use ObjectUtil.isEmpty(employee) So, update
			 * employee's API will be executive if not null parameter greater than 1
			 */
			if (ObjectUtil.countNotNullParamater(employee) > 1) {
				employee.setUpdatedAt(updatedAt);
				employeeMapperImpl.updateEmployee(employee);
			}

			/*
			 * In personalInfo, parameter employeeId is always not null then it will call
			 * API update updatePersonalInfo if only use ObjectUtil.isEmpty(personalInfo)
			 * So, update updatePersonalInfo API will be executive if not null parameter
			 * greater than 1
			 */
			if (ObjectUtil.countNotNullParamater(personalInfo) > 1 || multipartFile != null) {
				personalInfo.setUpdatedAt(updatedAt);
				if (multipartFile != null) {
					String fileName = FileUtil.generateFileName(multipartFile);
					personalInfo.setPersonalImage(fileName);
					try {
						FileUtil.saveFile(FileUtil.generateUploadDir(SystemProperties.PATH_SAVE_EMPLOYEE_IMAGE,
								personalInfo.getPersonalInfoId()), fileName, multipartFile);
					} catch (Exception e) {
						LogUtil.error(ExceptionUtils.getStackTrace(e));
						throw new GlobalException(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
								MessageUtil.getMessagelangUS("value.not.correct"));
					}
				}
				personnalInfoMapperImpl.updatePersonalInfo(personalInfo);
			}

			/*
			 * In documents, parameter employeeDocumentId is always not null then it will
			 * call API update updateEmployeeDocument if only use
			 * ObjectUtil.isEmpty(personalInfo) So, update updateEmployeeDocument API will
			 * be executive if not null parameter greater than 1
			 */
			if (ObjectUtils.isNotEmpty(documents)) {
				List<EmployeeDocumentDto> oldDocuments = new ArrayList<EmployeeDocumentDto>();
				List<EmployeeDocumentDto> newDocuments = new ArrayList<EmployeeDocumentDto>();
				documents.stream().forEach(e -> {
					if (ObjectUtils.isNotEmpty(e.getEmployeeDocumentId()) && ObjectUtil.countNotNullParamater(e) > 1) {
						oldDocuments.add(e);
					} else if (ObjectUtils.isEmpty(e.getEmployeeDocumentId())
							&& ObjectUtil.countNotNullParamater(e) > 1) {
						newDocuments.add(e);
					}
				});
				if (ObjectUtils.isNotEmpty(oldDocuments)) {
					employeeDocumentMapperImpl.updateEmployeeDocument(oldDocuments, updatedAt);
				}
				if (ObjectUtils.isNotEmpty(newDocuments)) {
					employeeDocumentMapperImpl.insertEmployeeDocument(employee, newDocuments);
				}
			}

			/*
			 * In positions, parameter employeePositionId is always not null then it will
			 * call API update updateEmployeePosition if only use
			 * ObjectUtil.isEmpty(personalInfo) So, update updateEmployeePosition API will
			 * be executive if not null parameter greater than 1
			 */
			if (ObjectUtils.isNotEmpty(positions)) {
				List<EmployeePositionDto> oldPositions = new ArrayList<EmployeePositionDto>();
				List<EmployeePositionDto> newPositions = new ArrayList<EmployeePositionDto>();
				positions.stream().forEach(e -> {
					if (ObjectUtils.isNotEmpty(e.getEmployeePositionId()) && ObjectUtil.countNotNullParamater(e) > 1) {
						oldPositions.add(e);
					} else if (ObjectUtils.isEmpty(e.getEmployeePositionId())
							&& ObjectUtil.countNotNullParamater(e) > 1) {
						newPositions.add(e);
					}
				});
				if (ObjectUtils.isNotEmpty(oldPositions)) {
					employeePositionMapperImpl.updateEmployeePosition(oldPositions, updatedAt);
					
				}
				if (ObjectUtils.isNotEmpty(newPositions)) {
					employeePositionMapperImpl.insertEmployeePosition(employee, newPositions);
				}
			}

			return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					HttpStatus.OK.value(), MessageUtil.getMessagelangUS("udpate.employee.success"), null, null);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					MessageUtil.getMessagelangUS("value.not.correct"));
		}
	}

	public ResponseTemplate findListEmployees(FindListEmployeesRequest request) {
		List<FindListEmployeesResponse> listEmployees = employeeMapperImpl.findListEmployees(request.getData());
		if (ObjectUtils.isEmpty(listEmployees)) {
			return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
					HttpStatus.OK.value(),
					MessageUtil.getFlexMessageLangUS("get.data", String.valueOf(listEmployees.size())), null, null);
		}
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(),
				MessageUtil.getFlexMessageLangUS("get.data", String.valueOf(listEmployees.size())), null,
				listEmployees);
	}

	public ResponseTemplate findEmployeeById(Integer id) {
		FindEmployeeResponse info = employeeMapperImpl.findEmployeeById(id);
		info.getPersonalInfo().setPersonalImage(FileUtil.getUrlImg(SystemProperties.PATH_SAVE_EMPLOYEE_IMAGE,
				info.getPersonalInfo().getPersonalInfoId(), info.getPersonalInfo().getPersonalImage()));
		info.setDocuments(employeeDocumentMapperImpl.findEmployeeDocumentsByEmployeeId(id));
		info.setPositions(employeePositionMapperImpl.findEmployeePositionsByEmployeeId(id));
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("get.data.success"), null, info);
	}

	public ResponseTemplate findPositions() {
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("get.data.success"), null,
				positionMapperImpl.findPositions());
	}

	public ResponseTemplate findDocuments() {
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("get.data.success"), null,
				documentMapperImpl.findDocuments());
	}

	public ResponseTemplate findRooms() {
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getMessagelangUS("get.data.success"), null, roomMapperImpl.findRooms());
	}

	public ResponseTemplate findReportCaseSelected(FindListTicketRequest request) {
		List<FindReportCaseSelectedResponse> listObj = requestEmployeeMapperImpl.findReportCaseSelected(request.getData());
		return new ResponseTemplate(SystemProperties.SYSTEM_NAME, SystemProperties.SYSTEM_VERSION,
				HttpStatus.OK.value(), MessageUtil.getFlexMessageLangUS("get.data", String.valueOf(listObj.size())),
				null, listObj);
	}

}
