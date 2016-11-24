package controler;

import java.io.Serializable;

/**
 * Point, holds Data for one message sended by a slave 
 * @author M.Geissbberger
 *
 */
public class Point implements Serializable{
	public float value;
	public long time;
	
	public Point(float value, long time) {
		this.time = time;
		this.value = value;
	}
}
