package com.company.hrsystem.request;

import java.io.Serializable;

import com.company.hrsystem.commons.constants.JsonRequestProperty;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenRequest implements Serializable{
	
	private static final long serialVersionUID = 2684018965748360199L;

	private String username;
	
	private String password;
	
	@JsonProperty(JsonRequestProperty.JSON_REQUEST_PROPERTY)
	AuthenRequest data;
}
