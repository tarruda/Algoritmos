package br.ufpe.cin.algoritmos.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.regex.Pattern;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

class HtmlResult extends Result {

	private static Pattern mobileUserAgent = Pattern
			.compile(
					"^.*(iPhone|iPad|iTouch|iPod|BlackBerry|HTC|LG|MOT|Nokia|NOKIAN|PLAYSTATION|PSP|SAMSUNG|SonyEricsson|Mobile).*$",
					Pattern.CASE_INSENSITIVE);

	private static HashMap<String, STGroup> groups;
	private String templateName;
	private Object model;
	private boolean isMobileBrowser;
	private String userName;

	static {
		groups = new HashMap<String, STGroup>();
		try {
			String[] names = getPackageFileNames("views");
			for (String name : names)
				if (name.endsWith(".stg")) {
					STGroupFile g = new STGroupFile("views/" + name, "utf-8", '$', '$');
					groups.put(name.replace(".stg", ""), g);
				}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String[] getPackageFileNames(String packageName)
			throws IOException {
		ArrayList<String> list = new ArrayList<String>();
		Enumeration<URL> urls = Thread.currentThread().getContextClassLoader()
				.getResources(packageName);
		while (urls.hasMoreElements()) {
			URL url = urls.nextElement();
			String fName = url.getFile();
			File dir = new File(fName);
			for (File f : dir.listFiles()) {
				list.add(f.getName());
			}
		}
		return list.toArray(new String[] {});
	}

	public HtmlResult(String templateName, Object model, String userAgent,
			String userName) {
		this.isMobileBrowser = mobileUserAgent.matcher(userAgent).matches();
		this.templateName = templateName;
		this.model = model;
		this.userName = userName;
	}

	@Override
	public String getContentType() {
		return "text/html";
	}

	@Override
	public void render(PrintWriter writer) {
		if (!groups.containsKey(templateName))
			throw new RuntimeException("Template não encontrado.");
		STGroup g = groups.get(templateName);
		ST st = g.getInstanceOf("page");
		st.add("model", model);
		st.add("isMobile", isMobileBrowser);
		st.add("userName", userName);		
		String rendered = st.render();
		
		writer.println(rendered);
	}
}
