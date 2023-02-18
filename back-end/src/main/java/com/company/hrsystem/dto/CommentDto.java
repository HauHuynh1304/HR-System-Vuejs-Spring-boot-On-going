package com.company.hrsystem.dto;

import com.company.hrsystem.model.CommentModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class CommentDto extends CommentModel {

	private static final long serialVersionUID = -7760109978625598746L;

	private String requesterEmail;

	private String approverEmail;

	private String supervisorEmail;

}
