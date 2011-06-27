package br.ufpe.cin.algoritmos.controllers;

import java.io.IOException;
import java.util.List;

import br.ufpe.cin.algoritmos.db.FileDatabase;
import br.ufpe.cin.algoritmos.models.Message;
import br.ufpe.cin.algoritmos.web.Result;

public class InboxController extends ControllerBase {

	@Override
	public Result get() {
		String userName = getUserName();
		List<Message> messages = null;
		try {
			messages = FileDatabase.getInstance().messagesTo(userName);
		} catch (Exception e) {
			try {
				e.printStackTrace(response.getWriter());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}		
		return html("inbox", messages);
	}
}
