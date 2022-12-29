package com.company.hrsystem.request;

import java.io.Serializable;

import com.company.hrsystem.dto.ReasonDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReasonRequest implements Serializable {

	private static final long serialVersionUID = -4027950558565338990L;

	private ReasonDto reason;

	private ReasonRequest data;

}
