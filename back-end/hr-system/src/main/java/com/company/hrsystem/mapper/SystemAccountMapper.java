package com.company.hrsystem.mapper;

import com.company.hrsystem.model.SystemAccountModel;
import com.company.hrsystem.dto.AuthenAccountDto;
import com.company.hrsystem.dto.SystemAccountDto;
import com.company.hrsystem.model.SystemAccountExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SystemAccountMapper {

	// generated by MyBatis Generator
	long countByExample(SystemAccountExample example);

	// generated by MyBatis Generator
	int deleteByExample(SystemAccountExample example);

	// generated by MyBatis Generator
	int deleteByPrimaryKey(Integer systemAccountId);

	// generated by MyBatis Generator
	int insert(SystemAccountModel row);

	// generated by MyBatis Generator
	int insertSelective(SystemAccountModel row);

	// generated by MyBatis Generator
	List<SystemAccountModel> selectByExample(SystemAccountExample example);

	// generated by MyBatis Generator
	SystemAccountModel selectByPrimaryKey(Integer systemAccountId);

	// generated by MyBatis Generator
	int updateByExampleSelective(@Param("row") SystemAccountModel row, @Param("example") SystemAccountExample example);

	// generated by MyBatis Generator
	int updateByExample(@Param("row") SystemAccountModel row, @Param("example") SystemAccountExample example);

	// generated by MyBatis Generator
	int updateByEmailSelective(SystemAccountDto row);

	// generated by MyBatis Generator
	int updateByPrimaryKey(SystemAccountModel row);

	List<SystemAccountModel> selectAll();

	SystemAccountModel findSystemAccountIdByEmail(String email);
	
	AuthenAccountDto findAuthenAccountByEmail(String email);

}