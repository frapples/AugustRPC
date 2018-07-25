package io.github.frapples.augustrpc.protocol;

import com.google.gson.Gson;
import io.github.frapples.augustrpc.protocol.exception.SerializeParseException;
import java.nio.charset.StandardCharsets;
import jdk.nashorn.internal.ir.annotations.Immutable;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/25
 */
public class JsonProtocol implements ProtocolInterface {

    private Gson gson = new Gson();

    @Immutable
    static private class Pack {

        private String fullQualifiedClassName;
        private String objectJson;

        public Pack(String fullQualifiedClassName, String objectJson) {
            this.fullQualifiedClassName = fullQualifiedClassName;
            this.objectJson = objectJson;
        }

        public String getFullQualifiedClassName() {
            return fullQualifiedClassName;
        }

        public Object getObjectJson() {
            return objectJson;
        }
    }

    @Override
    public byte[] serialize(Object object) {
        Pack pack = new Pack(object.getClass().getName(), gson.toJson(object));
        String json = gson.toJson(pack);
        return json.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public Object deserialize(byte[] bytes) {
        String json = new String(bytes, StandardCharsets.UTF_8);
        Pack pack = gson.fromJson(json, Pack.class);
        Class<?> clazz;
        try {
            clazz = Class.forName(pack.getFullQualifiedClassName());
        } catch (ClassNotFoundException e) {
            throw new SerializeParseException(e.getMessage());
        }

        return gson.fromJson(pack.objectJson, clazz);
    }
}
