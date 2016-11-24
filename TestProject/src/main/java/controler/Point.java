package controler;

/**
 * Point, holds Data for one message sended by a slave 
 * @author M.Geissbberger
 *
 */
public class Point {
	public float value;
	public long time;
	
	public Point(float value, long time) {
		this.time = time;
		this.value = value;
	}
}
