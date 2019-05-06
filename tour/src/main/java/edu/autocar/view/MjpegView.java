package edu.autocar.view;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

// https://github.com/FriesW/java-mjpeg-streamer/blob/master/Java/src/com/github/friesw/mjpegstreamer/MjpegStreamer.java

// @Component("Mjpeg")
public class MjpegView extends AbstractView {
	String serverUrl;
	
	private static final String NL = "\r\n";
	private static final String BOUNDARY = "--boundary";
	private static final String HEAD = 
			BOUNDARY + NL + 
			"Content-Type: image/jpeg" + NL +
			"Content-Length: ";
	
	public MjpegView(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	
	@Override
	protected void renderMergedOutputModel(
			Map<String, Object> model,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setHeader("Cache-Control", "no-cache, private");
		response.setContentType("multipart/x-mixed-replace;boundary=" + BOUNDARY);

		os = response.getOutputStream();
		MjpegReceiver recv = new MjpegReceiver();
		
		recv.connect(serverUrl);
		recv.receive(this::sendFrame);
	}
	
	OutputStream os;
	public void sendFrame(byte[] frame) {
		try {
			os.write((HEAD + frame.length + NL + NL).getBytes());
			os.write(frame);
			os.flush();
			os.write((NL + NL).getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}
}
