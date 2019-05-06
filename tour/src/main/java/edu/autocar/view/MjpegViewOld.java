package edu.autocar.view;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
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
public class MjpegViewOld extends AbstractView {
	private static final String NL = "\r\n";
	private static final String BOUNDARY = "--boundary";
	private static final String HEAD = 
			BOUNDARY + NL + 
			"Content-Type: image/jpeg" + NL +
			"Content-Length: ";
	
	String[] images = {
			"Chrysanthemum.jpg",
			"Desert.jpg",
			"Hydrangeas.jpg",
			"Jellyfish.jpg"
	};	
	int ix = 0;
	
	List<byte[]> imageByteList = new ArrayList<byte[]>(0);
	
	public MjpegViewOld() {
		for(String name : images) {
        	try {
        		Path path = Paths.get("c:/temp", name);
        		BufferedImage originalImage = ImageIO.read(path.toFile());
            	ByteArrayOutputStream baos = new ByteArrayOutputStream();
            	ImageIO.write( originalImage, "jpg", baos );
            	baos.flush();
            	imageByteList.add(baos.toByteArray());
            	baos.close();
        	} catch (Exception ex) {
            	System.err.println("There was a problem loading the images.");
            }
        }
		
	}
	
	@Override
	protected void renderMergedOutputModel(
			Map<String, Object> model,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setHeader("Cache-Control", "no-cache, private");
		response.setContentType("multipart/x-mixed-replace;boundary=" + BOUNDARY);

		OutputStream os = response.getOutputStream();
		
		while (true) {			
			int size = imageByteList.get(ix).length;
			os.write((HEAD + size + NL + NL).getBytes());
			os.write(imageByteList.get(ix));
			os.flush();
			os.write((NL + NL).getBytes());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ix = (ix +1) % images.length;
		}
	}
}
