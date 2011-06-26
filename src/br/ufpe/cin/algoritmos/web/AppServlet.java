package br.ufpe.cin.algoritmos.web;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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

		String uri = req.getRequestURI();
		Matcher matcher = uriPattern.matcher(uri);

		if (matcher.matches()) {
			String controllerName = matcher.group(1);
			Class<?> controllerClass = loadControllerClass(controllerName);
			if (controllerClass == null)
				resp.getWriter().println("Controller nao encontrado.");
			// resp.sendRedirect("/index");
			else {
				String method = req.getMethod();
				Method controllerMethod = getControllerMethod(method,
						req.getParameterMap(), controllerClass);
				if (controllerMethod == null)
					resp.getWriter().println("Método nao encontrado.");
				// resp.sendRedirect("/index");
				else {
					try {
						Controller controller = (Controller) createController(controllerClass);

						controller.setRequest(req);
						controller.setResponse(resp);

						Collection<?> values = req.getParameterMap().values();
						String[] paramValues = new String[values.size()];
						int idx = 0;
						for (Object o : values) {
							String[] oo = (String[]) o;
							paramValues[idx] = oo[0];
							idx++;
						}
						Result result = controller.beforeRequest();
						if (result == null)
							result = (Result) controllerMethod.invoke(
									controller, (Object[]) paramValues);

						if (result instanceof RedirectResult)
							resp.sendRedirect(((RedirectResult) result)
									.getUrl());
						else
							result.render(resp.getWriter());
					} catch (Exception ex) {
						printException(req, resp, ex);
					}
				}

			}
		} else
			resp.sendRedirect("/index");
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
							mappedControllers.put(keyName.toLowerCase(),
									Class.forName(fullClassName));
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

	private Method getControllerMethod(String method, Map<?, ?> parameterMap,
			Class<?> controllerClass) {
		Method ret = null;
		Set<?> keySet = parameterMap.keySet();
		int size = keySet.size();

		Class<?>[] paramTypes = new Class<?>[size];
		for (int i = 0; i < size; i++)
			paramTypes[i] = String.class;
		try {
			Method m = controllerClass.getMethod(method.toLowerCase(),
					paramTypes);
			if (m.getReturnType().equals(Result.class))
				ret = m;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
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
		ex.printStackTrace(resp.getWriter());
		resp.getWriter().println("</h3>");
	}

}
