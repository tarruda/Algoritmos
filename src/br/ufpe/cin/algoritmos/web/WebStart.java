package br.ufpe.cin.algoritmos.web;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;

public class WebStart {

	public static void main(String[] args) throws Exception {
		Server server = new Server(8080);
		Context root = new Context(server, "/", Context.SESSIONS);
		root.addServlet(new ServletHolder(new AppServlet()), "/*");
		server.addHandler(root);
		server.start();
	}
}
