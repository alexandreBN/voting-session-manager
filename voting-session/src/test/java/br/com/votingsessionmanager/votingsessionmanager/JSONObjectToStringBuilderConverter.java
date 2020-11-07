package br.com.votingsessionmanager.votingsessionmanager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class JSONObjectToStringBuilderConverter {

	public abstract String buildJSONString() throws JsonProcessingException;

	public String convert(Object object) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String stringObject = mapper.writeValueAsString(object);
		return stringObject;
	}

	public <T> Object parse(String stringObject, Class<T> data) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		T objectMapped = mapper.readValue(stringObject, data);
		return objectMapped;
	}

}
