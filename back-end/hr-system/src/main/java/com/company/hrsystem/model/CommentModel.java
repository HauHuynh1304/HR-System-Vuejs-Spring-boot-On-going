package com.company.hrsystem.model;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
abstract class CommentModel {

	private Integer supervisorActionId;

	private Integer approverActionId;

	private String commentDetail;

	private Date createdAt;

}