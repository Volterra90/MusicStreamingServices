package com.musicstreaming.streaming.service;

import com.musicstreaming.streaming.exceptions.MailException;

public interface MailService {
	
	public void sendMail(String subject, String message, String... to)
		throws MailException;

}
