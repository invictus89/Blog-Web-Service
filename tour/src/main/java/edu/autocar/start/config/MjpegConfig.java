package edu.autocar.start.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import edu.autocar.dao.SensorDao;
import edu.autocar.view.MjpegView;

// @Configuration
public class MjpegConfig {
	final static String IPCAM_SERVER = "http://70.12.242.12:4747/video";
	@Autowired
	SensorDao dao;
	
	@Bean("mjpeg")
	public MjpegView mjpegView() {
		return new MjpegView(IPCAM_SERVER);
	}
}
