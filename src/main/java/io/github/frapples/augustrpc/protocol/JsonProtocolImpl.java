package io.github.frapples.augustrpc.protocol;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.frapples.augustrpc.protocol.exception.SerializeParseException;
import io.github.frapples.augustrpc.transport.model.CallId;
import io.github.frapples.augustrpc.transport.model.Request;
import io.github.frapples.augustrpc.transport.model.Response;
import io.github.frapples.augustrpc.utils.StringUtils;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/25
 */
public class JsonProtocolImpl implements ProtocolInterface {

    private final Gson gson = new Gson();
    private final JsonParser jsonParser = new JsonParser();

    static private class ObjectPackField {

        static final String CLASS_NAME_FIELD_KEY = "fullQualifiedClassName";
        static final String OBJECT_FILED_KEY = "object";
    }

    private class RequestPack {

        CallId callId;
        public String[] arguments;

        public RequestPack() {
        }

        RequestPack(Request request) {
            this.callId = request.getCallId();
            Object[] args = request.getArguments();
            this.arguments = new String[args.length];
            for (int i = 0; i < args.length; i++) {
                this.arguments[i] = JsonProtocolImpl.this.serialize(args[i]);
            }
        }

        Request toRequest(JsonProtocolImpl that) throws SerializeParseException {
            Object[] args = new Object[this.arguments.length];
            for (int i = 0; i < this.arguments.length; i++) {
                args[i] = that.deserialize(this.arguments[i]);
            }

            return new Request(this.callId, args);
        }
    }

    private class ResponsePack {

        public String returnResult;


        public ResponsePack(Response response) {
            this.returnResult = JsonProtocolImpl.this.serialize(response.getReturnResult());
        }

        public Response toResponse(JsonProtocolImpl that) throws SerializeParseException {
            return new Response(that.deserialize(this.returnResult));
        }

    }

    @Override
    public byte[] packRequest(Request request) {
        RequestPack requestPack = new RequestPack(request);
        String json = gson.toJson(requestPack);
        return json.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public Request unpackRequest(byte[] bytes) throws SerializeParseException {
        String json = new String(bytes, StandardCharsets.UTF_8);
        RequestPack requestPack = gson.fromJson(json, RequestPack.class);
        return requestPack.toRequest(this);
    }

    @Override
    public byte[] packResponse(Response response) {
        ResponsePack responsePack = new ResponsePack(response);
        String json = gson.toJson(responsePack);
        return json.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public Response unpackResponse(byte[] bytes) throws SerializeParseException {
        String json = new String(bytes, StandardCharsets.UTF_8);
        ResponsePack responsePack = gson.fromJson(json, ResponsePack.class);
        return responsePack.toResponse(this);
    }

    private String serialize(Object object) {
        Map<String, Object> pack = new HashMap<>();
        if (object != null) {
            pack.put(ObjectPackField.CLASS_NAME_FIELD_KEY, object.getClass().getName());
        } else {
            pack.put(ObjectPackField.CLASS_NAME_FIELD_KEY, "");
        }
        pack.put(ObjectPackField.OBJECT_FILED_KEY, object);
        return gson.toJson(pack);
    }

    private Object deserialize(String json) throws SerializeParseException {
        JsonObject jsonObject = (JsonObject) jsonParser.parse(json);
        String fullQualifiedClassName = jsonObject.get(ObjectPackField.CLASS_NAME_FIELD_KEY).getAsString();
        if (StringUtils.isEmpty(fullQualifiedClassName)) {
            return null;
        }

        Class<?> clazz;
        try {
            clazz = Class.forName(fullQualifiedClassName);
        } catch (ClassNotFoundException e) {
            throw new SerializeParseException(e.getMessage());
        }

        return gson.fromJson(jsonObject.get(ObjectPackField.OBJECT_FILED_KEY), clazz);
    }

}
