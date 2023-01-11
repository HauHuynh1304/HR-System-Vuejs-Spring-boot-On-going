package com.company.hrsystem.mapper.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.company.hrsystem.annotations.MapperImpl;
import com.company.hrsystem.dto.AuthenAccountDto;
import com.company.hrsystem.dto.SystemAccountDto;
import com.company.hrsystem.mapper.SystemAccountMapper;
import com.company.hrsystem.model.SystemAccountModel;

@MapperImpl
public class SystemAccountMapperImpl {

	@Autowired
	private SystemAccountMapper systemAccountMapper;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public int insertSelective(SystemAccountModel row) {
		return systemAccountMapper.insertSelective(row);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public int updateByEmailSelective(SystemAccountDto row) {
		return systemAccountMapper.updateByEmailSelective(row);
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, isolation = Isolation.READ_COMMITTED)
	public List<SystemAccountDto> findAllAccount() {
		return systemAccountMapper.findAllAccount();
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, isolation = Isolation.READ_COMMITTED)
	public int findSystemAccountIdByEmail(String email) {
		return systemAccountMapper.findSystemAccountIdByEmail(email);
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, isolation = Isolation.READ_COMMITTED)
	public AuthenAccountDto findAuthenAccountByEmail(String email) {
		return systemAccountMapper.findAuthenAccountByEmail(email);
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, isolation = Isolation.READ_COMMITTED)
	public AuthenAccountDto findAuthenAccountById(Integer id) {
		return systemAccountMapper.findAuthenAccountById(id);
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, isolation = Isolation.READ_COMMITTED)
	public List<AuthenAccountDto> findAccounts() {
		return systemAccountMapper.findAccounts();
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public int updateAccount(SystemAccountDto account) {
		return systemAccountMapper.updateAccount(account);
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, isolation = Isolation.READ_COMMITTED)
	public Boolean isEmailInDb(String email) {
		return systemAccountMapper.isEmailInDb(email);
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, isolation = Isolation.READ_COMMITTED)
	public List<SystemAccountDto> findAvailbleAccounts() {
		return systemAccountMapper.findAvailbleAccounts();
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, isolation = Isolation.READ_COMMITTED)
	public List<SystemAccountDto> findAccountByRole(String role) {
		return systemAccountMapper.findAccountByRole(role);
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, isolation = Isolation.READ_COMMITTED)
	public String findSystemEmailByEmployeeId(Integer employeeId) {
		return systemAccountMapper.findSystemEmailByEmployeeId(employeeId);
	}

}
