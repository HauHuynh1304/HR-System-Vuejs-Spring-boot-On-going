package com.company.hrsystem.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class ReasonModel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer reasonId;

	private String reasonName;

	private Boolean deletedFlag;

	private String createdAt;

	private Timestamp updatedAt;

}