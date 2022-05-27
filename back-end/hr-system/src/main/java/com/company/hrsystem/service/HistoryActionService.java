package com.company.hrsystem.service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.hrsystem.constants.CommonConstant;
import com.company.hrsystem.dto.HistoryActionDto;
import com.company.hrsystem.mapper.EmployeeMapper;
import com.company.hrsystem.mapper.HistoryActionMapper;
import com.company.hrsystem.utils.AuthenUtil;
import com.company.hrsystem.utils.ClientUtil;
import com.company.hrsystem.utils.ObjectUtil;
import com.company.hrsystem.utils.StringUtil;

@Service
public class HistoryActionService {

	@Autowired
	private ObjectUtil objectUtil;

	@Autowired
	private ClientUtil clientUtil;

	@Autowired
	private EmployeeMapper employeeMapper;

	@Autowired
	private AuthenUtil authenUtil;

	@Autowired
	private HistoryActionMapper historyActionMapper;
	
	/*
	 * @Parameter employeeId: The ID of person who changed data.
	 * If call function saveHistoryAction one time, put value equals to zero;
	 * If call function saveHistoryAction many time, try to find employeeId out side of this function,
	 * and put the real value to employeeId
	 */
	public void saveHistoryAction(Object dto, Integer employeeId, String action, Integer rowId, String tableName,
			HttpServletRequest request) {
		HistoryActionDto history = new HistoryActionDto();
		history.setComputerIp(clientUtil.getClientIpAddress(request));
		history.setActionType(action);
		if (employeeId == CommonConstant.ZERO_VALUE) {
			employeeId = employeeMapper.findEmployeeIdByAccountId(authenUtil.getAccountId());
		}
		history.setEmployeeId(employeeId);
		if (ObjectUtils.isNotEmpty(dto)) {
			history.setTargetRowId(rowId);
			history.setTargetTable(tableName);
			// Initialize Nested Classes start
			Set<HistoryActionDto.MutableObjValues> listObj = new HashSet<HistoryActionDto.MutableObjValues>();
			// Initialize Nested Classes end
			for (Map.Entry<String, Object> entry : objectUtil.objectToMap(dto).entrySet()) {
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