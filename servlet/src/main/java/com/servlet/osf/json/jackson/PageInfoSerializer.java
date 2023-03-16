package com.servlet.osf.json.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.servlet.osf.entity.esb.PageInfo;
import com.servlet.osf.entity.esb.RespPageInfo;

import java.io.IOException;


@Deprecated
public class PageInfoSerializer extends StdSerializer<PageInfo> {
    public PageInfoSerializer() {
        super(PageInfo.class);
    }

    @Override
    public void serialize(PageInfo pageInfo, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        RespPageInfo respPageInfo = (RespPageInfo) pageInfo;
        gen.writeStringField("CURR_PAGE_NUM", respPageInfo.getCURR_PAGE_NUM());
        gen.writeStringField("PER_PAGE_NUM", respPageInfo.getPER_PAGE_NUM());
        gen.writeStringField("TOTAL_PAGE_NUM", respPageInfo.getTOTAL_PAGE_NUM());
        gen.writeStringField("TOTAL_NUM", respPageInfo.getTOTAL_NUM());
        gen.writeEndObject();
    }
}
