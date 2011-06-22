package br.ufpe.cin.algoritmos.web;

import java.io.File;

import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.ContextHandlerCollection;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.DefaultServlet;
import org.mortbay.jetty.servlet.ServletHolder;

public class WebStart {

	public static void main(String[] args) throws Exception {
		ContextHandlerCollection contexts = new ContextHandlerCollection();

		// static content
		Context staticContext = new Context(contexts, "/content",
				Context.SESSIONS);
		File content = new File("content");
		if (content.exists())
			staticContext.setResourceBase(content.getAbsolutePath());
		ServletHolder staticHolder = new ServletHolder(new DefaultServlet());
		staticHolder.setInitParameter("dirAllowed", "true");
		staticContext.addServlet(staticHolder, "/");

		// dynamic content
		Context appContext = new Context(contexts, "/", Context.SESSIONS);
		ServletHolder appHolder = new ServletHolder(new AppServlet());
		appContext.addServlet(appHolder, "/");

		contexts.setHandlers(new Handler[] { staticContext, appContext });

		Server server = new Server(8080);
		server.addHandler(contexts);
		server.start();
	}
}
