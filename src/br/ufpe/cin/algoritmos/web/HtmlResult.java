package br.ufpe.cin.algoritmos.web;

import java.io.PrintWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;
import org.apache.velocity.runtime.RuntimeSingleton;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;

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
		VelocityContext context = new VelocityContext();
		context.put("model", model);
		Template template = null;
		template = Velocity.getTemplate("index.vm");
		template.merge(context, writer);
	}
}
