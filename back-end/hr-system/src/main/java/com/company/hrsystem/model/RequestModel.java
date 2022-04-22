package com.company.hrsystem.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
abstract class RequestModel {

	private Integer requestId;

	private Integer requestTypeId;

	private Integer reasonId;

}