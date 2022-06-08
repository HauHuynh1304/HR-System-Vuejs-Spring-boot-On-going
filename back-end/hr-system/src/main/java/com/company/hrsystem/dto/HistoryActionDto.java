package com.company.hrsystem.dto;

import java.io.Serializable;
import java.util.Set;

import com.company.hrsystem.model.HistoryActionModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class HistoryActionDto extends HistoryActionModel {

	private static final long serialVersionUID = 1L;

	private Set<MutableObjValues> mutableObjValues;

	@Getter
	@Setter
	@NoArgsConstructor
	public class MutableObjValues implements Serializable {

		private static final long serialVersionUID = 1L;

		private String targetColumn;

		private String targetValue;

	}

}