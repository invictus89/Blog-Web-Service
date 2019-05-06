package edu.autocar.domain;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Sensor {
	private int sensorId;
	private String owner;
	private String place;
	private SensorType sensorType;
	private float value;	
	private Date regDate;

}
