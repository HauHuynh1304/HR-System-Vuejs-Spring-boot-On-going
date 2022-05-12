package com.company.hrsystem.request;

import com.company.hrsystem.model.CommentModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentRequest extends CommentModel {

	private static final long serialVersionUID = 1L;

	private CommentRequest data;

	public CommentRequest(Integer supervisorActionId, Integer approverActionId, String commentDetail) {
		super(supervisorActionId, approverActionId, commentDetail);
	}

}
