package edu.autocar.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ThumbUtil {
	public static byte[] thumb(byte [] data) throws Exception {
		return thumb(data, 100, 100);
	}
	
	public static byte[] thumb(byte [] data, int width, int height) 
										throws Exception {
		try(
			ByteArrayInputStream in = new ByteArrayInputStream(data);	
			ByteArrayOutputStream out = new ByteArrayOutputStream();
		){
			Thumbnails.of(in)
					.size(width,height)
					.crop(Positions.CENTER)
					.toOutputStream(out);
			return out.toByteArray();
		}
	}
}
