package io.github.frapples.augustrpc.protocol;

import com.google.gson.Gson;
import io.github.frapples.augustrpc.protocol.exception.SerializeParseException;
import io.github.frapples.augustrpc.transport.consumer.model.Request;
import io.github.frapples.augustrpc.transport.consumer.model.Response;
import java.nio.charset.StandardCharsets;
import net.jcip.annotations.Immutable;

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

    private class RequestPack {

        public String serviceFullyQualifiedName;
        public String methodName;
        public String[] methodArgumentTypeFullyQualifiedNames;
        public byte[][] arguments;

        public RequestPack() {
        }

        RequestPack(Request request) {
            this.serviceFullyQualifiedName = request.getServiceFullyQualifiedName();
            this.methodName = request.getMethodName();
            this.methodArgumentTypeFullyQualifiedNames = request.getMethodArgumentTypeFullyQualifiedNames();

            Object[] args = request.getArguments();
            this.arguments = new byte[args.length][];
            for (int i = 0; i < args.length; i++) {
                this.arguments[i] = JsonProtocol.this.serialize(args[i]);
            }
        }

        Request toRequest() throws SerializeParseException {
            Object[] args = new Object[this.arguments.length];
            for (int i = 0; i < this.arguments.length; i++) {
                args[i] = JsonProtocol.this.deserialize(this.arguments[i]);
            }

            return new Request(
                this.serviceFullyQualifiedName,
                this.methodName, this.methodArgumentTypeFullyQualifiedNames,
                args);
        }
    }

    @Override
    public byte[] serialize(Object object) {
        Pack pack = new Pack(object.getClass().getName(), gson.toJson(object));
        String json = gson.toJson(pack);
        return json.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializeParseException {
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
        return requestPack.toRequest();
    }

    @Override
    public byte[] packResponse(Response response) {
        // TODO
        return new byte[0];
    }

    @Override
    public Response unpackResponse(byte[] bytes) {
        // TODO
        return null;
    }
}
