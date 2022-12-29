package com.company.hrsystem.request;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IsEmailInDbRequest implements Serializable {
	
	private static final long serialVersionUID = 6143764875307812735L;

	private String email;
	 
	private IsEmailInDbRequest data;
	 
}
