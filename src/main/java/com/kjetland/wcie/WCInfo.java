package com.kjetland.wcie;

/**
 * Created by IntelliJ IDEA.
 * User: mortenkjetland
 * Date: 1/24/11
 * Time: 11:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class WCInfo {

    public enum WebContainer {GLASSFISH_v2, JETTY_6_or_7}

    private final WebContainer webContainer;
    private final String contextPath;
    private final int port;


    public WCInfo(WebContainer webContainer, String contextPath, int port) {
        this.webContainer = webContainer;
        this.contextPath = contextPath;
        this.port = port;
    }

    public WebContainer getWebContainer() {
        return webContainer;
    }

    public String getContextPath() {
        return contextPath;
    }

    public int getPort() {
        return port;
    }
}
