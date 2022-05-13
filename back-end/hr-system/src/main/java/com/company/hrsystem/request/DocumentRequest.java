package com.company.hrsystem.request;

import java.io.Serializable;

import com.company.hrsystem.dto.DocumentDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class DocumentRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private DocumentDto document;
	
	private DocumentRequest data;

}
