package ru.otus.l121;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 * mysql> CREATE USER 'tully'@'localhost' IDENTIFIED BY 'tully';
 * mysql> GRANT ALL PRIVILEGES ON * . * TO 'tully'@'localhost';
 * mysql> select user, host from mysql.user;
 * mysql> create database db_example;
 * mysql> SET GLOBAL time_zone = '+3:00';
 */


/**
 * Make executable WAR file by command: mvn package
 */

/**
 *
 * REST Client open http://localhost:8081/rest-client/
 *
 */


public class Main {

    public static void main(String[] args) throws Exception {

        FilterHolder filterHolder = new FilterHolder(CrossOriginFilter.class);
        filterHolder.setInitParameter("allowedOrigins", "*");
        filterHolder.setInitParameter("allowedMethods", "GET, POST");

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.addFilter(filterHolder, "/*", null);

        Server jettyServer = new Server(8081);
        jettyServer.setHandler(context);


        ServletHolder jerseyServlet = context.addServlet(
                ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.classnames",
                RESTServicePoint.class.getCanonicalName());
        jerseyServlet.setInitParameter(
                "javax.ws.rs.Application",
                MyResourceConfig.class.getCanonicalName());

        // add special pathspec of "/home/" content mapped to the homePath
        ServletHolder holderHome = new ServletHolder("static-home", DefaultServlet.class);
        holderHome.setInitParameter("resourceBase", "src/main/resources/rest-client/");
        holderHome.setInitParameter("dirAllowed","true");
        holderHome.setInitParameter("pathInfoOnly","true");
        context.addServlet(holderHome,"/rest-client/*");

        // Lastly, the default servlet for root content (always needed, to satisfy servlet spec)
        // It is important that this is last.
        ServletHolder holderPwd = new ServletHolder("default", DefaultServlet.class);
        holderPwd.setInitParameter("dirAllowed","true");
        context.addServlet(holderPwd,"/");

        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            jettyServer.destroy();
        }
    }
}
