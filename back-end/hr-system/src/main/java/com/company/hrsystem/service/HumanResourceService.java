package com.company.hrsystem.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.company.hrsystem.Exeption.GlobalException;
import com.company.hrsystem.constants.CommonConstant;
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
import com.company.hrsystem.utils.AuthenUtil;
import com.company.hrsystem.utils.DateUtil;
import com.company.hrsystem.utils.FileUtil;
import com.company.hrsystem.utils.LogUtil;
import com.company.hrsystem.utils.MessageUtil;
import com.company.hrsystem.utils.ObjectUtil;
import com.google.gson.Gson;

@Service
public class HumanResourceService {

	@Value("${system.name}")
	private String system;

	@Value("${system.version}")
	private String version;

	@Value("${upload.employee.img.dir}")
	private String uploadEmployeeImgDir;

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

	@Autowired
	private FileUtil fileUtil;

	@Autowired
	private AuthenUtil authenUtil;

	@Autowired
	private HistoryActionService historyActionService;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public ResponseTemplate insertEmployee(String jsonString, MultipartFile multipartFile,
			HttpServletRequest servletRequest) {
		Gson gson = new Gson();
		EmployeeRequest request = gson.fromJson(jsonString, EmployeeRequest.class);
		String fileName = null;
		int inserterId = employeeMapper.findEmployeeIdByAccountId(authenUtil.getAccountId());
		try {
			if (multipartFile != null) {
				fileName = fileUtil.generateFileName(multipartFile);
			}

			PersonalInfoDto personalInfo = request.getPersonalInfo();
			personalInfo.setPersonalImage(fileName);
			personnalInfoMapper.insertPersonalInfo(personalInfo);
			historyActionService.saveHistoryAction(personalInfo, inserterId, CommonConstant.INSERT_ACTION,
					personalInfo.getPersonalInfoId(), CommonConstant.TABLE_PERSONAL, servletRequest);

			EmployeeDto employeeDto = request.getEmployee();
			employeeMapper.insertEmployee(personalInfo, employeeDto);
			historyActionService.saveHistoryAction(employeeDto, inserterId, CommonConstant.INSERT_ACTION,
					employeeDto.getEmployeeId(), CommonConstant.TABLE_EMPLOYEE, servletRequest);

			List<EmployeeDocumentDto> listEmployeeDocument = request.getDocuments();
			if (ObjectUtils.isNotEmpty(listEmployeeDocument)) {
				employeeDocumentMapper.insertEmployeeDocument(employeeDto, listEmployeeDocument);
				saveHistoryLastInsertDocuments(inserterId, employeeDto.getEmployeeId(), servletRequest);
			}

			List<EmployeePositionDto> listEmployeePosition = request.getPositions();
			employeePositionMapper.insertEmployeePosition(employeeDto, listEmployeePosition);
			saveHistoryLastInsertPositons(inserterId, employeeDto.getEmployeeId(), servletRequest);

			if (!StringUtils.isAllEmpty(fileName)) {
				try {
					fileUtil.saveFile(
							fileUtil.generateUploadDir(uploadEmployeeImgDir, personalInfo.getPersonalInfoId()),
							fileName, multipartFile);
				} catch (IOException e) {
					LogUtil.error(ExceptionUtils.getStackTrace(e));
					throw new GlobalException(system, version, messageUtil.getMessagelangUS("value.not.correct"));
				}
			}
			return new ResponseTemplate(system, version, HttpStatus.OK.value(),
					messageUtil.getMessagelangUS("insert.employee.success"), null, null);
		} catch (Exception e) {
			LogUtil.error(ExceptionUtils.getStackTrace(e));
			throw new GlobalException(system, version, messageUtil.getMessagelangUS("value.not.correct"));
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public ResponseTemplate updateEmployee(String jsonString, MultipartFile multipartFile,
			HttpServletRequest servletRequest) {
		Gson gson = new Gson();
		EmployeeRequest request = gson.fromJson(jsonString, EmployeeRequest.class);
		EmployeeDto employee = request.getEmployee();
		PersonalInfoDto personalInfo = request.getPersonalInfo();
		List<EmployeeDocumentDto> documents = request.getDocuments();
		List<EmployeePositionDto> positions = request.getPositions();
		String updatedAt = DateUtil.getCurrentDayHourSecond();
		int inserterId = employeeMapper.findEmployeeIdByAccountId(authenUtil.getAccountId());

		try {
			/*
			 * In EmployeeRequest, parameter employeeId is always not null then it will call
			 * API update employee if only use ObjectUtil.isEmpty(employee) So, update
			 * employee's API will be executive if not null parameter greater than 1
			 */
			if (objectUtil.countNotNullParamater(employee) > 1) {
				employee.setUpdatedAt(updatedAt);
				employeeMapper.updateEmployee(employee);
				historyActionService.saveHistoryAction(employee, inserterId, CommonConstant.UPDATE_ACTION,
						employee.getEmployeeId(), CommonConstant.TABLE_EMPLOYEE, servletRequest);
			}

			/*
			 * In personalInfo, parameter employeeId is always not null then it will call
			 * API update updatePersonalInfo if only use ObjectUtil.isEmpty(personalInfo)
			 * So, update updatePersonalInfo API will be executive if not null parameter
			 * greater than 1
			 */
			if (objectUtil.countNotNullParamater(personalInfo) > 1 || multipartFile != null) {
				personalInfo.setUpdatedAt(updatedAt);
				if (multipartFile != null) {
					String fileName = fileUtil.generateFileName(multipartFile);
					personalInfo.setPersonalImage(fileName);
					try {
						fileUtil.saveFile(
								fileUtil.generateUploadDir(uploadEmployeeImgDir, personalInfo.getPersonalInfoId()),
								fileName, multipartFile);
					} catch (Exception e) {
						LogUtil.error(ExceptionUtils.getStackTrace(e));
						throw new GlobalException(system, version, messageUtil.getMessagelangUS("value.not.correct"));
					}
				}
				personnalInfoMapper.updatePersonalInfo(personalInfo);
				historyActionService.saveHistoryAction(personalInfo, inserterId, CommonConstant.UPDATE_ACTION,
						personalInfo.getPersonalInfoId(), CommonConstant.TABLE_PERSONAL, servletRequest);
			}
			
			/*
			 * In documents, parameter employeeDocumentId is always not null then it will call
			 * API update updateEmployeeDocument if only use ObjectUtil.isEmpty(personalInfo)
			 * So, update updateEmployeeDocument API will be executive if not null parameter
			 * greater than 1
			 */
			if (ObjectUtils.isNotEmpty(documents)) {
				List<EmployeeDocumentDto> oldDocuments = new ArrayList<EmployeeDocumentDto>();
				List<EmployeeDocumentDto> newDocuments = new ArrayList<EmployeeDocumentDto>();
				documents.stream().forEach(e -> {
					if (ObjectUtils.isNotEmpty(e.getEmployeeDocumentId()) && objectUtil.countNotNullParamater(e) > 1) {
						oldDocuments.add(e);
					} else if (ObjectUtils.isEmpty(e.getEmployeeDocumentId())
							&& objectUtil.countNotNullParamater(e) > 1) {
						newDocuments.add(e);
					}
				});
				if (ObjectUtils.isNotEmpty(oldDocuments)) {
					employeeDocumentMapper.updateEmployeeDocument(oldDocuments, updatedAt);
					for (EmployeeDocumentDto obj : oldDocuments) {
						historyActionService.saveHistoryAction(obj, inserterId, CommonConstant.UPDATE_ACTION,
								obj.getEmployeeDocumentId(), CommonConstant.TABLE_EMPLOYEE_DOCUMENT, servletRequest);
					}
				}
				if (ObjectUtils.isNotEmpty(newDocuments)) {
					employeeDocumentMapper.insertEmployeeDocument(employee, newDocuments);
					saveHistoryLastInsertDocuments(inserterId, employee.getEmployeeId(), servletRequest);
				}
			}
			
			/*
			 * In positions, parameter employeePositionId is always not null then it will call
			 * API update updateEmployeePosition if only use ObjectUtil.isEmpty(personalInfo)
			 * So, update updateEmployeePosition API will be executive if not null parameter
			 * greater than 1
			 */
			if (ObjectUtils.isNotEmpty(positions)) {
				List<EmployeePositionDto> oldPositions = new ArrayList<EmployeePositionDto>();
				List<EmployeePositionDto> newPositions = new ArrayList<EmployeePositionDto>();
				positions.stream().forEach(e -> {
					if (ObjectUtils.isNotEmpty(e.getEmployeePositionId()) && objectUtil.countNotNullParamater(e) > 1) {
						oldPositions.add(e);
					} else if (ObjectUtils.isEmpty(e.getEmployeePositionId())
							&& objectUtil.countNotNullParamater(e) > 1) {
						newPositions.add(e);
					}
				});
				if (ObjectUtils.isNotEmpty(oldPositions)) {
					employeePositionMapper.updateEmployeePosition(oldPositions, updatedAt);
					for (EmployeePositionDto obj : oldPositions) {
						historyActionService.saveHistoryAction(obj, inserterId, CommonConstant.UPDATE_ACTION,
								obj.getEmployeePositionId(), CommonConstant.TABLE_EMPLOYEE_POSITION, servletRequest);
					}
				}
				if (ObjectUtils.isNotEmpty(newPositions)) {
					employeePositionMapper.insertEmployeePosition(employee, newPositions);
					saveHistoryLastInsertPositons(inserterId, employee.getEmployeeId(), servletRequest);
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

	public ResponseTemplate findEmployeeById(Integer id) {
		FindEmployeeResponse info = employeeMapper.findEmployeeById(id);
		info.getPersonalInfo().setPersonalImage(fileUtil.getUrlImg(uploadEmployeeImgDir,
				info.getPersonalInfo().getPersonalInfoId(), info.getPersonalInfo().getPersonalImage()));
		info.setDocuments(employeeDocumentMapper.findEmployeeDocumentsByEmployeeId(id));
		info.setPositions(employeePositionMapper.findEmployeePositionsByEmployeeId(id));
		return new ResponseTemplate(system, version, HttpStatus.OK.value(),
				messageUtil.getMessagelangUS("get.data.success"), null, info);
	}

	public void saveHistoryLastInsertPositons(Integer inserterId, Integer employeeId,
			HttpServletRequest servletRequest) {
		Set<EmployeePositionDto> positionDtos = employeePositionMapper.findLastInsertListEmployeePosition(employeeId);
		for (EmployeePositionDto obj : positionDtos) {
			historyActionService.saveHistoryAction(obj, inserterId, CommonConstant.INSERT_ACTION,
					obj.getEmployeePositionId(), CommonConstant.TABLE_EMPLOYEE_POSITION, servletRequest);
		}
	}

	public void saveHistoryLastInsertDocuments(Integer inserterId, Integer employeeId,
			HttpServletRequest servletRequest) {
		Set<EmployeeDocumentDto> documentDtos = employeeDocumentMapper.findLastInsertListEmployeeDocument(employeeId);
		for (EmployeeDocumentDto obj : documentDtos) {
			historyActionService.saveHistoryAction(obj, inserterId, CommonConstant.INSERT_ACTION,
					obj.getEmployeeDocumentId(), CommonConstant.TABLE_EMPLOYEE_DOCUMENT, servletRequest);
		}
	}

}
