package com.ticlivetest.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.*;

@Slf4j
public class StringListConverter implements AttributeConverter<List<String>, String> {

    private static final ObjectMapper mapper = new ObjectMapper();


    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
//        log.info("컨버터 내부에서 db데이터 형식으로 변환 시도, 입력된 List<String> attribute : .."+attribute+"..END");
        try {
            if(attribute.isEmpty()){
                return null;
            }
            return mapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            log.debug("StringListConverter.convertToDatabaseColumn exception occur attribute: {}", attribute.toString());
            throw new RuntimeException();
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
//        log.info("컨버터 내부에서 java 데이터 형식으로 변환 시도, 입력된 String dbData : .."+dbData+"..END");
        try {
            if(dbData == null || dbData.equals("")){
                return new ArrayList<>();
            }
            return mapper.readValue(dbData, List.class);
        } catch (IOException e) {
            log.debug("StringListConverter.convertToEntityAttribute exception occur dbData: {}", dbData);
            throw new RuntimeException();
        }
    }
}

