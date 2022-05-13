package com.company.hrsystem.request;

import java.io.Serializable;

import com.company.hrsystem.dto.CommentDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private CommentDto comment;

	private CommentRequest data;
	
}
