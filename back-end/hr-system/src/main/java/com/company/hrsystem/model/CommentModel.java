package com.company.hrsystem.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public abstract class CommentModel implements Serializable {
	
	private static final long serialVersionUID = -8939611984208575528L;

	private Integer commentId;

	private Integer supervisorActionId;

	private Integer approverActionId;
	
	private Integer requesterActionId;

	private String commentDetail;

	private String createdAt;
	
}