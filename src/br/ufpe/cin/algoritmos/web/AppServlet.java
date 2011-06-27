package br.ufpe.cin.algoritmos.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class AppServlet extends HttpServlet {
	private static final String controllersPath = "br/ufpe/cin/algoritmos/controllers/";
	private static final String controllersPackage = "br.ufpe.cin.algoritmos.controllers.";

	private Pattern uriPattern;
	private Map<String, Class<?>> mappedControllers;

	public AppServlet() {
		// matches valid java identifiers
		uriPattern = Pattern.compile("/([a-zA-Z_$][a-zA-Z\\d_$]*)/?",
				Pattern.CASE_INSENSITIVE);
		mappedControllers = new HashMap<String, Class<?>>();
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("Receiving request from : " + req.getRemoteAddr()
				+ ". User agent : " + req.getHeader("User-Agent"));

		String uri = req.getRequestURI();
		Matcher matcher = uriPattern.matcher(uri);

		if (matcher.matches()) {
			String controllerName = matcher.group(1);
			Class<?> controllerClass = loadControllerClass(controllerName);
			if (controllerClass == null)
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		
			else {
				try {
					Controller controller = (Controller) createController(controllerClass);

					controller.setRequest(req);
					controller.setResponse(resp);

					Result result = controller.beforeRequest();
					if (result == null)
						if (req.getMethod().equals("GET"))
							result = controller.get();
						else if (req.getMethod().equals("POST"))
							result = controller.post();
					if (result != null)
						if (result instanceof RedirectResult)
							resp.sendRedirect(((RedirectResult) result)
									.getUrl());
						else {
							resp.setContentType(result.getContentType());
							resp.setCharacterEncoding(result.getCharset());
							result.render(resp.getWriter());
						}
					else
						resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
				} catch (Exception ex) {
					printException(req, resp, ex);
				}
			}

		} else
			resp.sendRedirect("/inbox");
	}

	private Class<?> loadControllerClass(String name) {
		String key = name.toLowerCase();
		Class<?> ret = null;
		if (!mappedControllers.containsKey(key))
			synchronized (mappedControllers) {
				if (!mappedControllers.containsKey(key))
					try {
						File controllerDir = new File(controllersPath);
						String[] files = controllerDir.list();
						for (String file : files) {
							String fullClassName = controllersPackage + file;
							fullClassName = fullClassName.replaceAll(".class",
									"");
							String keyName = file.replaceAll(
									"Controller.class", "");
							mappedControllers.put(keyName.toLowerCase(), Class
									.forName(fullClassName));
						}
						ret = mappedControllers.get(key);

					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				else
					ret = mappedControllers.get(key);
			}
		else
			ret = mappedControllers.get(key);
		return ret;
	}

	private Object createController(Class<?> controllerClass) {
		Object ret = null;
		try {
			ret = controllerClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return ret;
	}

	private void printException(HttpServletRequest req,
			HttpServletResponse resp, Exception ex) throws IOException {
		resp.setContentType("text/html");
		resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

		resp.getWriter().println("<h3>");
		resp.getWriter().println(ex.getMessage());
		resp.getWriter().println("</h3>");
		resp.getWriter().println("<h4>");
		ex.printStackTrace(resp.getWriter());
		resp.getWriter().println("</h4>");
	}

}
