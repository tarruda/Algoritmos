package br.ufpe.cin.algoritmos.controllers;

import java.util.ArrayList;

import br.ufpe.cin.algoritmos.models.Message;
import br.ufpe.cin.algoritmos.web.Result;

public class OutboxController extends ControllerBase {

	@Override
	public Result get() {
		ArrayList<Message> messages = new ArrayList<Message>();
		String userName = getUserName();

		for (Message message : Message.list)
			if (message.getFrom().equals(userName))
				messages.add(message);

		return html("outbox", messages);
	}
}
