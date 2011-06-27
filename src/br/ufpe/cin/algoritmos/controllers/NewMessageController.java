package br.ufpe.cin.algoritmos.controllers;

import br.ufpe.cin.algoritmos.models.Message;
import br.ufpe.cin.algoritmos.web.Result;

public class NewMessageController extends ControllerBase {

	@Override
	public Result get() {
		return html("new");
	}

	@Override
	public Result post() {
		String from = getUserName();
		String to = request.getParameter("toUserName");
		String title = request.getParameter("title");
		String text = request.getParameter("text");		
		Message.list.add(new Message(text, from, to, title));		
		return redirect("/inbox");
	}
}
