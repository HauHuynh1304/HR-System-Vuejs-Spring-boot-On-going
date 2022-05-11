package com.company.hrsystem.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public abstract class CommentModel {

	private Integer supervisorActionId;

	private Integer approverActionId;

	private String commentDetail;

	private String createdAt;

}