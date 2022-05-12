package com.company.hrsystem.request;

import java.io.Serializable;

import com.company.hrsystem.model.SupervisorActionModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SupervisorActionRequest extends SupervisorActionModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private SupervisorActionRequest data;

}
