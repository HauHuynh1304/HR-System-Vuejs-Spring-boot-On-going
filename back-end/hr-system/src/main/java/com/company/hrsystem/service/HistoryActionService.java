package com.company.hrsystem.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.company.hrsystem.annotations.WriteLogToDB;
import com.company.hrsystem.serviceInterface.HistoryActionServiceInterface;

@Service
public class HistoryActionService implements HistoryActionServiceInterface {
	
	/**
	 * @param dto
	 * @param employeeId: The ID of person who changed data.
	 * If call function saveHistoryAction one time, put value equals to zero;
	 * If call function saveHistoryAction many time, put value equals to employeeId.
	 * @param action
	 * @param rowId
	 * @param tableName
	 * @param request
	 */
	@WriteLogToDB
	public void saveHistoryAction(Object dto, Integer employeeId, String action, Integer rowId, String tableName,
			HttpServletRequest request) {
	}

}