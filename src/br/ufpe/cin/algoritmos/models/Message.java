package br.ufpe.cin.algoritmos.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {
	private static final long serialVersionUID = 2283416107806714909L;

	public static final ArrayList<Message> list = new ArrayList<Message>();

	private String text;
	private String from;
	private String to;
	private String title;

	public Message(String text, String from, String to, String title) {
		this.text = text;
		this.from = from;
		this.to = to;
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getFrom() {
		return from;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getTo() {
		return to;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
