package edu.autocar.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TagHelper {
	public static String newBadge(Date date) {
		String tag ="";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String today = sdf.format(new Date());
		String testDay = sdf.format(date);
		if(today.equals(testDay)) {
			tag = "<span class=\"badge badge-danger\"><i class=\"fas fa-tag\"></i> New</span>";
		}
		
		return tag;
	}

}
