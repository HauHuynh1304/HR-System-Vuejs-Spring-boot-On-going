package com.company.hrsystem.aspect;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.hrsystem.constants.CommonConstant;
import com.company.hrsystem.dto.HistoryActionDto;
import com.company.hrsystem.mapper.EmployeeMapper;
import com.company.hrsystem.mapper.HistoryActionMapper;
import com.company.hrsystem.utils.AuthenUtil;
import com.company.hrsystem.utils.ClientUtil;
import com.company.hrsystem.utils.ObjectUtil;
import com.company.hrsystem.utils.StringUtil;

@Aspect
@Component
public class WriteLogToDBAspect {

	@Autowired
	private EmployeeMapper employeeMapper;

	@Autowired
	private HistoryActionMapper historyActionMapper;

	@Pointcut(value = "@annotation(com.company.hrsystem.annotations.WriteLogToDB)")
	public void annotationPointCutDefinition() {
	}

	@AfterReturning("annotationPointCutDefinition() && args(dto, employeeId, action, rowId, tableName, request)")
	public void doWrite(Object dto, Integer employeeId, String action, Integer rowId, String tableName,
			HttpServletRequest request) {
		HistoryActionDto history = new HistoryActionDto();
		history.setComputerIp(ClientUtil.getClientIpAddress(request));
		history.setActionType(action);
		if (employeeId == CommonConstant.ZERO_VALUE) {
			employeeId = employeeMapper.findEmployeeIdByAccountId(AuthenUtil.getAccountId());
		}
		history.setEmployeeId(employeeId);
		if (ObjectUtils.isNotEmpty(dto)) {
			history.setTargetRowId(rowId);
			history.setTargetTable(tableName);
			// Initialize Nested Classes start
			Set<HistoryActionDto.MutableObjValues> listObj = new HashSet<HistoryActionDto.MutableObjValues>();
			// Initialize Nested Classes end
			for (Map.Entry<String, Object> entry : ObjectUtil.objectToMap(dto).entrySet()) {
				if (ObjectUtils.isNotEmpty(entry.getValue())) {
					HistoryActionDto.MutableObjValues obj = history.new MutableObjValues();
					obj.setTargetColumn(StringUtil.addUnderscoreAtEachUppercases(entry.getKey()));
					obj.setTargetValue(entry.getValue().toString());
					listObj.add(obj);
				}
			}
			history.setMutableObjValues(listObj);
		}
		historyActionMapper.insertHistoryAction(history);
	}

}
