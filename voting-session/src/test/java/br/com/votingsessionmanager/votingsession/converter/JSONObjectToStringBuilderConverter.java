package br.com.votingsessionmanager.votingsession.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public abstract class JSONObjectToStringBuilderConverter {

	public abstract String buildJSONString() throws JsonProcessingException;

	public String convert(Object object) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String stringObject = mapper.writeValueAsString(object);
		return stringObject;
	}

	public <T> Object parse(String stringObject, Class<T> data) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		mapper.registerModule(new JavaTimeModule());
		T objectMapped = mapper.readValue(stringObject, data);
		return objectMapped;
	}

}
