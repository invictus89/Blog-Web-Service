package edu.autocar.dao;

import java.util.List;

import edu.autocar.domain.Sensor;
import edu.autocar.domain.SensorCriteria;

public interface SensorDao {
	
	int insert(Sensor sensor) throws Exception;
	
	List<Sensor> getSensors(SensorCriteria sc) throws Exception;
	
}
