package com.company.hrsystem.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class EmailModel implements Serializable {

	private static final long serialVersionUID = 4942655983925821277L;

	private String from;
	
	private String to;
	
	private String subject;
	
	private String[] cc;
	
	private String[] bcc;
	
	private String receiverName;
	
	private String senderName;
	
	private String requestType;
	
	private String emailTemplate;
	
}
