package edu.autocar.view;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Consumer;


public class MjpegReceiver{

	private InputStream httpIn;
	private ByteArrayOutputStream jpgOut;
	
	// 웹 캡 접속
	public void connect(String strUrl) throws Exception {
		URL url = new URL(strUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		httpIn = new BufferedInputStream(conn.getInputStream(), 8192);
		
	}
	
	// 한 프레임 수신 
	public byte[] getFrame() throws Exception{
		int prev = 0;
		int cur = 0;
		while ((cur = httpIn.read()) >= 0) {
			// 새로운 프레임 시작
			if (prev == 0xFF && cur == 0xD8) {
				jpgOut = new ByteArrayOutputStream(8192);
				jpgOut.write((byte) prev);
			}
			// 수신 데이터 데이터 수신
			if(jpgOut!=null)
				jpgOut.write((byte) cur);
			// 한 프레임 수신 완료
			if (prev == 0xFF && cur == 0xD9) {
				byte[] data = jpgOut.toByteArray();
				jpgOut.close();
				return data;
			}
			prev = cur;
		}
		return null;
	}
	
	// 이미지 수신 
	public void receive(Consumer<byte[]> consumer) throws Exception{
		while(true) {
			byte[] frame = getFrame();
			if(frame!=null)
				consumer.accept(frame);
		}
	}	

}
