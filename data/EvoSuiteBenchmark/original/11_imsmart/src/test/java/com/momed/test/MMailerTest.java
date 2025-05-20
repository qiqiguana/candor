package com.momed.test;

import com.momed.misc.MMailer;

public class MMailerTest
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		MMailer mailer = new MMailer();
		mailer.setSmtpHost("207.15.48.16");
		mailer.setFromAddress("anant.gowerdhan@momed.com");
		mailer.setToAddress("anant.gowerdhan@momed.com");
		mailer.setSubject("Report");
		mailer.setBody("");
		mailer
				.setAttachment("C:/Documents and Settings/gowerdh/My Documents/Infocrossing/Migration/sample_data/test.csv");
		mailer.sendMail();

	}

}
