package com.company.hrsystem.config;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemProperties {

	// JWT info
	public static String JWT_SECRET;
	public static String JWT_ACCESS_VALID_TIME;
	public static String JWT_REFRESH_VALID_TIME;
	public static String JWT_START;
	public static String JWT_ATTRIBUTE;
	public static String JWT_PAYLOAD_ID;
	public static String JWT_PAYLOAD_ROLES;
	public static String JWT_PAYLOAD_MAX_VALID_TIME;

	// Token Cache info
	public static String TOKEN_STORE;
	public static String TOKEN_AUTHORIZATION;

	// System info
	public static String SYSTEM_NAME;
	public static String SYSTEM_VERSION;
	public static Locale SYSTEM_LANG_US;

	// System Authorization info
	public static String SYSTEM_ROOT_ROOM;
	public static String SYSTEM_ROOT_ADMIN_EMAIL;
	public static String SYSTEM_ROOT_ADMIN_PASSWORD;
	public static String SYSTEM_ROLE_ROOT_ADMIN;
	public static String SYSTEM_ROLE_ADMIN;
	public static String SYSTEM_ROLE_MANAGER;
	public static String SYSTEM_ROLE_SUPERVISOR;
	public static String SYSTEM_ROLE_HR;
	public static String SYSTEM_ROLE_EMPLOYEE;

	// Folder path info
	public static String PATH_SAVE_EMPLOYEE_IMAGE;

	// start setting data
	@Value("${jwt.secret}")
	private void setJWTSecret(final String jwtSecret) {
		JWT_SECRET = jwtSecret;
	}

	@Value("${jwt.accessValid}")
	private void setJWTAccessValidTime(final String jwtAccessValidTime) {
		JWT_ACCESS_VALID_TIME = jwtAccessValidTime;
	}

	@Value("${jwt.refreshValid}")
	private void setJWTRefrestValidTime(final String jwtRefrestValidTime) {
		JWT_REFRESH_VALID_TIME = jwtRefrestValidTime;
	}

	@Value("${jwt.start}")
	private void setJWTStart(final String jwtStart) {
		JWT_START = jwtStart;
	}

	@Value("${jwt.attribute}")
	private void setJWTAttribute(final String jwtAttribute) {
		JWT_ATTRIBUTE = jwtAttribute;
	}

	@Value("${jwt.payload.id}")
	private void setJWTPayloadId(final String jwtPayloadId) {
		JWT_PAYLOAD_ID = jwtPayloadId;
	}

	@Value("${jwt.payload.roles}")
	private void setJWTPayloadRoles(final String jwtPayloadRoles) {
		JWT_PAYLOAD_ROLES = jwtPayloadRoles;
	}

	@Value("${jwt.payload.maxValidTime}")
	private void setJWTPayloadMaxValidTime(final String jwtPayloadMaxValidTime) {
		JWT_PAYLOAD_MAX_VALID_TIME = jwtPayloadMaxValidTime;
	}

	@Value("${token.store}")
	private void setTokenStore(final String tokenStore) {
		TOKEN_STORE = tokenStore;
	}

	@Value("${token.authorization}")
	private void setTokenAuthorization(final String tokenAuthorization) {
		TOKEN_AUTHORIZATION = tokenAuthorization;
	}

	@Value("${system.name}")
	private void setSystemName(final String systemName) {
		SYSTEM_NAME = systemName;
	}

	@Value("${system.version}")
	private void setSystemVersion(final String systemVersion) {
		SYSTEM_VERSION = systemVersion;
	}

	@Value("${system.lang.en}")
	private void setSystemLangUs(final Locale systemLangUs) {
		SYSTEM_LANG_US = systemLangUs;
	}

	@Value("${root.room}")
	private void setRootRoom(final String rootRoom) {
		SYSTEM_ROOT_ROOM = rootRoom;
	}

	@Value("${email}")
	private void setRootAdminEmail(final String rootAdminEmail) {
		SYSTEM_ROOT_ADMIN_EMAIL = rootAdminEmail;
	}

	@Value("${password}")
	private void setRootAdminPassword(final String rootAdminPassword) {
		SYSTEM_ROOT_ADMIN_PASSWORD = rootAdminPassword;
	}

	@Value("${root.admin}")
	private void setRoleRootAdmin(final String roleRootAdmin) {
		SYSTEM_ROLE_ROOT_ADMIN = roleRootAdmin;
	}

	@Value("${admin}")
	private void setRoleAdmin(final String roleAdmin) {
		SYSTEM_ROLE_ADMIN = roleAdmin;
	}

	@Value("${manager}")
	private void setRoleManager(final String roleManager) {
		SYSTEM_ROLE_MANAGER = roleManager;
	}

	@Value("${supervisor}")
	private void setRoleSuppervisor(final String roleSuppervisor) {
		SYSTEM_ROLE_SUPERVISOR = roleSuppervisor;
	}

	@Value("${hr}")
	private void setRoleHR(final String roleHR) {
		SYSTEM_ROLE_HR = roleHR;
	}

	@Value("${employee}")
	private void setRoleEmployee(final String roleEmployee) {
		SYSTEM_ROLE_EMPLOYEE = roleEmployee;
	}

	@Value("${upload.employee.img.dir}")
	private void setPathSaveEmployeeImage(final String pathSaveEmployeeImage) {
		PATH_SAVE_EMPLOYEE_IMAGE = pathSaveEmployeeImage;
	}

}
