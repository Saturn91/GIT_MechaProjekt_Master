package controler;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * One graph holds different points, e.g. all data measured by a slave over time
 * @author M.Geissbberger
 *
 */
public class Graph implements Serializable{
	private ArrayList<Point> points = new ArrayList<Point>();
	private String title;
	
	public Graph(String title) {
		this.title = title;
	}
	
	public void addPoints(Point[] points){
		for(int i = 0; i < points.length; i++){
			this.points.add(points[i]);
		}
	}
	
	public void addPoint(Point point){
		this.points.add(point);
	}
	
	public ArrayList<Point> getPoints(){
		return points;
	}
	
	public Point getPoint(int point){
		if(point < points.size()){return points.get(point);}else{return new Point(-99, 99);}
	}
	
	public String getTitle(){
		return title;
	}
}
