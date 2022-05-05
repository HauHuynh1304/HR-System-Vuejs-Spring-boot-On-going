package com.company.hrsystem.utils;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Service;

@Service
public class MathUtil {

	public Long calculateFromString(String value) {
		ExpressionParser parser = new SpelExpressionParser();
		return parser.parseExpression(value).getValue(Long.class);
	}

}
