package com.company.hrsystem.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.company.hrsystem.config.SystemProperties;
import com.company.hrsystem.dto.EmployeeDto;
import com.company.hrsystem.dto.PersonalInfoDto;
import com.company.hrsystem.dto.RoomDto;
import com.company.hrsystem.dto.SystemAccountDto;
import com.company.hrsystem.dto.SystemRoleDto;
import com.company.hrsystem.mapper.EmployeeMapper;
import com.company.hrsystem.mapper.PersonnalInfoMapper;
import com.company.hrsystem.mapper.RoomMapper;
import com.company.hrsystem.mapper.SystemAccountMapper;
import com.company.hrsystem.mapper.SystemAccountRoleMapper;
import com.company.hrsystem.mapper.SystemRoleMapper;
import com.company.hrsystem.utils.LogUtil;

@Service
public class OnStartProject implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private SystemAccountMapper systemAccountMapper;

	@Autowired
	private SystemRoleMapper systemRoleMapper;

	@Autowired
	private SystemAccountRoleMapper systemAccountRoleMapper;

	@Autowired
	private RoomMapper roomMapper;

	@Autowired
	private EmployeeMapper employeeMapper;

	@Autowired
	private PersonnalInfoMapper personnalInfoMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void onApplicationEvent(ContextRefreshedEvent event) {
		/*
		 * Create direction for saving upload profile image
		 */
		Path saveToPath = Paths.get(SystemProperties.PATH_SAVE_EMPLOYEE_IMAGE);
		if (!Files.exists(saveToPath)) {
			try {
				Files.createDirectory(saveToPath);
			} catch (IOException e) {
				LogUtil.error("can't create direction on start project");
				LogUtil.error(ExceptionUtils.getStackTrace(e));
			}
		}

		createdRole();

		createdRoom();

		createPersonalInfo();

		createdRootAccount();
	}

	/*
	 * Created root account
	 */
	public void createdRootAccount() {
		SystemAccountDto dto = new SystemAccountDto(1, SystemProperties.SYSTEM_ROOT_ADMIN_EMAIL,
				passwordEncoder.encode(SystemProperties.SYSTEM_ROOT_ADMIN_PASSWORD), false, null);
		Boolean isEmailInDb = systemAccountMapper.isEmailInDb(SystemProperties.SYSTEM_ROOT_ADMIN_EMAIL);
		if (!isEmailInDb) {
			systemAccountMapper.insertSelective(dto);
			Integer[] roleIds = { 1 };
			systemAccountRoleMapper.insertAccountRole(dto, roleIds);
			long millis = System.currentTimeMillis();
			EmployeeDto employee = new EmployeeDto(1, "IT001", 1, 1, new Date(millis));
			PersonalInfoDto personalInfoDto = new PersonalInfoDto();
			personalInfoDto.setPersonalInfoId(1);
			employeeMapper.insertEmployee(personalInfoDto, employee);
		}
	}

	/*
	 * Created Role
	 */
	public void createdRole() {
		List<SystemRoleDto> dtos = systemRoleMapper.findAllRoles();
		SystemRoleDto rootAmin = new SystemRoleDto(1, SystemProperties.SYSTEM_ROLE_ROOT_ADMIN);
		SystemRoleDto admin = new SystemRoleDto(2, SystemProperties.SYSTEM_ROLE_ADMIN);
		SystemRoleDto employee = new SystemRoleDto(3, SystemProperties.SYSTEM_ROLE_EMPLOYEE);
		SystemRoleDto supervisor = new SystemRoleDto(4, SystemProperties.SYSTEM_ROLE_SUPERVISOR);
		SystemRoleDto manager = new SystemRoleDto(5, SystemProperties.SYSTEM_ROLE_MANAGER);
		SystemRoleDto hr = new SystemRoleDto(6, SystemProperties.SYSTEM_ROLE_HR);
		if (CollectionUtils.isEmpty(dtos)) {
			List<SystemRoleDto> insertDtos = new ArrayList<SystemRoleDto>();
			insertDtos.add(rootAmin);
			insertDtos.add(admin);
			insertDtos.add(employee);
			insertDtos.add(supervisor);
			insertDtos.add(manager);
			insertDtos.add(hr);
			systemRoleMapper.insertListSystemRole(insertDtos);
		} else {
			if (!isExistRole(dtos, SystemProperties.SYSTEM_ROLE_ROOT_ADMIN)) {
				systemRoleMapper.insertSystemRoleSelected(rootAmin);
			}
			if (!isExistRole(dtos, SystemProperties.SYSTEM_ROLE_ADMIN)) {
				systemRoleMapper.insertSystemRoleSelected(admin);
			}
			if (!isExistRole(dtos, SystemProperties.SYSTEM_ROLE_EMPLOYEE)) {
				systemRoleMapper.insertSystemRoleSelected(employee);
			}
			if (!isExistRole(dtos, SystemProperties.SYSTEM_ROLE_SUPERVISOR)) {
				systemRoleMapper.insertSystemRoleSelected(supervisor);
			}
			if (!isExistRole(dtos, SystemProperties.SYSTEM_ROLE_MANAGER)) {
				systemRoleMapper.insertSystemRoleSelected(manager);
			}
			if (!isExistRole(dtos, SystemProperties.SYSTEM_ROLE_HR)) {
				systemRoleMapper.insertSystemRoleSelected(hr);
			}
		}

	}

	public void createdRoom() {
		List<RoomDto> dtos = roomMapper.findAllRooms();
		if (!isExistRoom(dtos, SystemProperties.SYSTEM_ROOT_ROOM)) {
			RoomDto dto = new RoomDto(1, SystemProperties.SYSTEM_ROOT_ROOM);
			roomMapper.insertRoom(dto);
		}

	}

	public void createPersonalInfo() {
		if (!personnalInfoMapper.isExistPersonInfo(SystemProperties.SYSTEM_ROOT_ADMIN_EMAIL)) {
			long millis = System.currentTimeMillis();
			PersonalInfoDto dto = new PersonalInfoDto(1, "N/A", new Date(millis), "N/A", "N/A", "N/A", "N/A",
					SystemProperties.SYSTEM_ROOT_ADMIN_EMAIL);
			personnalInfoMapper.insertPersonalInfo(dto);
		}
	}

	public Boolean isExistRole(List<SystemRoleDto> dtos, String role) {
		for (SystemRoleDto systemRoleDto : dtos) {
			if (role.equals(systemRoleDto.getRoleName())) {
				return true;
			}
		}
		return false;
	}

	public Boolean isExistRoom(List<RoomDto> dtos, String room) {
		for (RoomDto romDto : dtos) {
			if (room.equals(romDto.getRoomName())) {
				return true;
			}
		}
		return false;
	}

}
