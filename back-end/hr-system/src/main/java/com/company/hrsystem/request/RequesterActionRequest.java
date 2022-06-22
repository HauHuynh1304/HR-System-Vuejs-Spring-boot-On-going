package com.company.hrsystem.request;

import java.io.Serializable;

import com.company.hrsystem.dto.RequesterActionDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequesterActionRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private RequesterActionDto requesterAction;

	private RequesterActionRequest data;

}
