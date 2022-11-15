package com.cth.lhmbe.type;

import java.util.EnumSet;
import java.util.NoSuchElementException;
import java.util.Objects;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class RoleEnumConvertor implements AttributeConverter<RoleEnum,String> {

	@Override
	public String convertToDatabaseColumn(RoleEnum attribute) {
		return attribute.getValue();
	}

	@Override
	public RoleEnum convertToEntityAttribute(String dbData) {
		return EnumSet
			.allOf(RoleEnum.class)
			.stream()
			.filter(roleEnum -> Objects.equals(dbData, roleEnum.getValue()))
			.findAny()
			.orElseThrow(NoSuchElementException::new);
	}
}
