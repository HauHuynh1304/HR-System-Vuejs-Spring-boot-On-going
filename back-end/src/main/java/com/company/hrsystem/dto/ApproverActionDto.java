package com.company.hrsystem.dto;

import java.sql.Timestamp;

import com.company.hrsystem.model.ApproverActionModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ApproverActionDto extends ApproverActionModel {

	private static final long serialVersionUID = 636214183400934854L;

	public ApproverActionDto(Integer approverActionId, String actionType, Timestamp updatedAt) {
		super(approverActionId, actionType, updatedAt);
	}

	private String approverEmail;

}
