package com.company.hrsystem.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.company.hrsystem.dto.AuthenAccountDto;
import com.company.hrsystem.dto.SystemAccountDto;
import com.company.hrsystem.model.SystemAccountModel;

@Mapper
public interface SystemAccountMapper {

	int insertSelective(SystemAccountModel row);

	int updateByEmailSelective(SystemAccountDto row);

	List<SystemAccountDto> findAllAccount();

	int findSystemAccountIdByEmail(String email);

	AuthenAccountDto findAuthenAccountByEmail(String email);

	AuthenAccountDto findAuthenAccountById(Integer id);

	List<AuthenAccountDto> findAccounts();

	int updateAccount(SystemAccountDto account);

	Boolean isEmailInDb(String email);

	List<SystemAccountDto> findAvailbleAccounts();

	List<SystemAccountDto> findAccountByRole(String role);

	String findSystemEmailByEmployeeId(Integer employeeId);

}