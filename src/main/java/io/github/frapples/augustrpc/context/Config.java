package io.github.frapples.augustrpc.context;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/24
 */
public class Config {

    private String iocBridgeImplClassName;
    private String requestSenderImplClassName;

    public String getIocBridgeImplClassName() {
        return iocBridgeImplClassName;
    }

    public void setIocBridgeImplClassName(String iocBridgeImplClassName) {
        this.iocBridgeImplClassName = iocBridgeImplClassName;
    }

    public String getRequestSenderImplClassName() {
        return requestSenderImplClassName;
    }

    public void setRequestSenderImplClassName(String requestSenderImplClassName) {
        this.requestSenderImplClassName = requestSenderImplClassName;
    }
}
