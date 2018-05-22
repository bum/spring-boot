package org.eclipse.jetty.demo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class TimeSocket implements Runnable {
	private TimeZone timezone;
	private Session session;

	@OnWebSocketConnect
	public void onOpen(Session session) {
		this.session = session;
		this.timezone = TimeZone.getTimeZone("UTC");
		new Thread(this).start();
	}

	@OnWebSocketClose
	public void onClose(int closeCode, String closeReasonPhrase) {
		this.session = null;
	}

	@Override
	public void run() {
		while (this.session != null) {
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
				dateFormat.setTimeZone(timezone);

				String timestamp = dateFormat.format(new Date());
				this.session.getRemote().sendString(timestamp);
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException | IOException e) {
				e.printStackTrace();
			}
		}
	}
}
