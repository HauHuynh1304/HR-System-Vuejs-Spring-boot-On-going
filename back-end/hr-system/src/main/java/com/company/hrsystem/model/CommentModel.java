package com.company.hrsystem.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public abstract class CommentModel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer supervisorActionId;

	private Integer approverActionId;

	private String commentDetail;

	private String createdAt;
	
}