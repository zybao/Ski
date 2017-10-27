package com.oab.skyi.common.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 *
 * @author zhiyong.bao
 * @date 2017/10/27
 */

public class FloatTypeAdapter extends TypeAdapter<Number> {
    @Override
    public Number read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return 0f;
        }
        return (float) in.nextDouble();
    }
    @Override
    public void write(JsonWriter out, Number value) throws IOException {
        out.value(value);
    }
}
