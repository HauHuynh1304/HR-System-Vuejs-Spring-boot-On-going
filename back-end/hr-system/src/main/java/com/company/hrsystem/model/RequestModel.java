package com.company.hrsystem.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class RequestModel implements Serializable {

	private static final long serialVersionUID = -6112900843546014570L;

	private Integer requestId;

	private Integer requestTypeId;

	private Integer reasonId;

}