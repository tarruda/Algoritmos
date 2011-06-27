package br.ufpe.cin.algoritmos.controllers;

import java.io.IOException;

import br.ufpe.cin.algoritmos.db.FileDatabase;
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
		//Message.list.add(new Message(text, from, to, title));		
		try {
			FileDatabase.getInstance().insert(new Message(text, from, to, title));
		} catch (Exception e) {
			try {
				e.printStackTrace(response.getWriter());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return redirect("/inbox");
	}
}
