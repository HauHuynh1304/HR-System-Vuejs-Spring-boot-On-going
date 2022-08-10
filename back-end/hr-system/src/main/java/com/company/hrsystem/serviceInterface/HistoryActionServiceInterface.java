package com.company.hrsystem.serviceInterface;

import javax.servlet.http.HttpServletRequest;

public interface HistoryActionServiceInterface {

	public void saveHistoryAction(Object dto, Integer employeeId, String action, Integer rowId, String tableName,
			HttpServletRequest request);

}
