package com.example;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 *
 * This class launches the web application in an embedded Jetty container.
 * This is the entry point to your application. The Java command that is used for
 * launching should fire this main method.
 *
 */
public class Main {

    public static void main(String[] args) throws Exception {

        WebAppContext root = new WebAppContext();
        root.setContextPath("/");
        root.setResourceBase("src/main/webapp/");        
        root.setDescriptor(root.getResourceBase() + "/WEB-INF/web.xml");
        root.setParentLoaderPriority(true);

        Server server = new Server(Integer.valueOf(StringUtils.defaultIfEmpty(System.getenv("PORT"), "8080")));
        server.setSendServerVersion(false);
        server.setSendDateHeader(false);
        server.setHandler(root);
        server.start();
        server.join();
    }

}
