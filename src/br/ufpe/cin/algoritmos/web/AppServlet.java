package br.ufpe.cin.algoritmos.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AppServlet extends HttpServlet {
	private static final long serialVersionUID = -2078658879853768152L;
	private static final String controllersPath = "br/ufpe/cin/algoritmos/controllers/";
	private static final String controllersPackage = "br.ufpe.cin.algoritmos.controllers.";
	private static final ClassLoader cl = new ControllerClassLoader();

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
				respondNotFound(req, resp);
			else {
				String method = req.getMethod();
				Method controllerMethod = getControllerMethod(method, req
						.getParameterMap(), controllerClass);
				if (controllerMethod == null)
					respondNotAllowed(req, resp);
				else {
					try {
						Object controller = createController(controllerClass);
						Result result = (Result) controllerMethod.invoke(
								controller, req.getParameterMap().values()
										.toArray());
						result.render(resp.getWriter());
					} catch (Exception ex) {
						printException(req, resp, ex);
					}
				}

			}
		} else
			respondNotFound(req, resp);
	}

	private Class<?> loadControllerClass(String name) {
		Class<?> ret = null;
		if (!mappedControllers.containsKey(name))
			synchronized (mappedControllers) {
				if (!mappedControllers.containsKey(name))
					try {
						ret = cl.loadClass(controllersPackage + name
								+ "Controller");
						mappedControllers.put(name, ret);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				else
					ret = mappedControllers.get(name);
			}
		else
			ret = mappedControllers.get(name);
		return ret;
	}

	private Method getControllerMethod(String method, Map parameterMap,
			Class<?> controllerClass) {
		Method ret = null;
		Set keySet = parameterMap.keySet();
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

	private void respondNotAllowed(HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html");
		resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		resp.getWriter().println("<h1>Not allowed.</h1>");
	}

	private void respondNotFound(HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html");
		resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		resp.getWriter().println("<h1>Not found.</h1>");
	}

	private static class ControllerClassLoader extends URLClassLoader {

		public ControllerClassLoader() {
			super(new URL[0], AppServlet.class.getClassLoader());
		}

		@Override
		protected Class<?> findClass(final String name)
				throws ClassNotFoundException {
			getParent();

			String filePath = name.replace('.', '/');
			filePath = filePath + ".class";
			BufferedInputStream classIs = null;

			File f = new File(filePath);
			if (f.exists())
				try {
					classIs = new BufferedInputStream(new FileInputStream(f));
				} catch (FileNotFoundException e1) {
				}
			if (classIs == null)
				classIs = new BufferedInputStream(ClassLoader
						.getSystemResourceAsStream(filePath));
			byte[] classBytes;
			try {
				classBytes = new byte[classIs.available()];
				classIs.read(classBytes);
				return defineClass(name, classBytes, 0, classBytes.length);
			} catch (IOException e) {
				return null;
			}
		}
	}

}
