package com.company.hrsystem.model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.company.hrsystem.commons.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class DocumentModel implements Serializable {

	private static final long serialVersionUID = 2636604370909336102L;

	private Integer documentId;

	private String documentName;

	private Boolean deletedFlag;

	private String createdAt;

	@JsonFormat(timezone=DateUtil.TIME_ZONE)
	private Timestamp updatedAt;

}