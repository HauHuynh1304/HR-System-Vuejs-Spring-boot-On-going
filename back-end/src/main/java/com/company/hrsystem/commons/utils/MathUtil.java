package com.company.hrsystem.commons.utils;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class MathUtil {

	public static Long calculateFromString(String value) {
		ExpressionParser parser = new SpelExpressionParser();
		return parser.parseExpression(value).getValue(Long.class);
	}

}
