package edu.autocar.domain;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SensorCriteria {
	private String owner;
	private String place;
	private SensorType sensorType;
	private Date from;
	private Date to;
}
