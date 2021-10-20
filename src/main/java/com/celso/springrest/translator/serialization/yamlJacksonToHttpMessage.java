package com.celso.springrest.translator.serialization;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

public final class yamlJacksonToHttpMessage extends AbstractJackson2HttpMessageConverter {

    public yamlJacksonToHttpMessage() {
        super(new YAMLMapper(), MediaType.parseMediaType("application/x-yaml"));
    }
}