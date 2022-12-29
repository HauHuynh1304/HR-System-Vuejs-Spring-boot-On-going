package com.company.hrsystem.request;

import java.io.Serializable;

import com.company.hrsystem.dto.PositionDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PositionRequest implements Serializable {

	private static final long serialVersionUID = 6340011871228083215L;

	private PositionDto position;

	private PositionRequest data;

}
