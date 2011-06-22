package br.ufpe.cin.algoritmos.web;

import java.io.PrintWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

class HtmlResult extends Result {

	private String templateName;
	private Object model;

	public HtmlResult(String templateName, Object model) {
		this.templateName = templateName;
		this.model = model;
	}

	@Override
	public String getContentType() {
		return "text/html";
	}

	@Override
	public void render(PrintWriter writer) {
		Velocity.init();
		VelocityContext context = new VelocityContext();
		context.put("model", model);
		Template template = Velocity.getTemplate("templates/" + templateName
				+ ".vm", "UTF-8");
		template.merge(context, writer);
	}
}
