package com.servlet.osf.json.jackson;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.servlet.osf.entity.esb.PageInfo;
import com.servlet.osf.entity.esb.ReqPageInfo;

import java.io.IOException;

public class PageInfoDeserializer extends StdDeserializer<PageInfo> {

    public PageInfoDeserializer() {
        super(PageInfo.class);
    }

    @Override
    public PageInfo deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        ReqPageInfo pageInfo = new ReqPageInfo();
        JsonNode node = p.getCodec().readTree(p);
        pageInfo.setPAGE_UP_DOWN(node.get("PAGE_UP_DOWN").asText());
        pageInfo.setCURR_PAGE_NUM(node.get("CURR_PAGE_NUM").asText());
        pageInfo.setPER_PAGE_NUM(node.get("PER_PAGE_NUM").asText());

        return pageInfo;
    }
}
