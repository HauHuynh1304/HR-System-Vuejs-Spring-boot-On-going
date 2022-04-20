package com.company.hrsystem.request;

import java.io.Serializable;

import com.company.hrsystem.constants.JsonRequestProperty;
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
	
	private static final long serialVersionUID = 5926468583005150707L;
	
	private String username;
	
	private String password;
	
	@JsonProperty(JsonRequestProperty.JSON_REQUEST_PROPERTY)
	AuthenRequest data;
}
