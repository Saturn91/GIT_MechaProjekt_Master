package controler;

/**
 * Point, holds Data for one message sended by a slave 
 * @author M.Geissbberger
 *
 */
public class Point {
	public int value;
	public long time;
	
	public Point(int value, long time) {
		this.time = time;
		this.value = value;
	}
}
