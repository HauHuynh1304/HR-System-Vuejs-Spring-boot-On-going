package com.company.hrsystem.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class DocumentModel {

	private Integer documentId;

	private String documentName;

	private Boolean deletedFlag;

	private String createdAt;

	private String updatedAt;
}