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
	private ArrayList<Float> yValues = new ArrayList<Float>();
	private ArrayList<Float> xValues = new ArrayList<Float>();
	
	private String title;
	
	public Graph(String title) {
		this.title = title;
	}
	
	public void addPoints(Point[] points){
		for(int i = 0; i < points.length; i++){
			this.points.add(points[i]);
			yValues.add(points[i].value);
			xValues.add((float) xValues.size());
		}
	}
	
	public void addPoint(Point point){
		this.points.add(point);
		yValues.add(point.value);
		xValues.add((float) xValues.size());
	}
	
	public ArrayList<Point> getPoints(){
		return points;
	}
	
	public Point getPoint(int point){
		if(point < points.size()){return points.get(point);}else{return new Point(-99, 99);}
	}
	
	public ArrayList<Float> getyValues() {
		return yValues;
	}

	public ArrayList<Float> getxValues() {
		return xValues;
	}

	public String getTitle(){
		return title;
	}
}
