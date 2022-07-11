package com.company.hrsystem.response;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindReportCaseSelectedResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String requester;
	
	private List<ReportInfo> reportInfo;
	
	@Getter
	@Setter
	@NoArgsConstructor
	public static class ReportInfo implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		private String requestType;

		private String reason;
		
		private String startDate;

		private String endDate;

		private String approver;

		private Double duration;
		
	}

}
