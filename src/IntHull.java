import java.util.Stack;

class Point {
	public int x,y;
	
	public Point() {
		this.x = 0;
		this.y = 0;
	}
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public Point(Point p) {
		this.x = p.x;
		this.y = p.y;
	}	
	public Point plus(int x, int y) {
		return new Point(this.x + x, this.y + y);
	}
	public Point plus(Vector v) {
		return plus(v.x, v.y);
	}
	public Point minus(int x, int y) {
		return new Point(this.x - x, this.y - y);
	}
	public Point minus(Vector v) {
		return minus(v.x, v.y);
	}
	public Vector minus(Point p) {
		return new Vector(this.x - p.x, this.y - p.y);
	}
	public double distanceSquaredTo(Point p) {
		return Math.pow(p.x-x, 2) + Math.pow(p.y-y, 2);
	}
	public double distanceTo(Point p) {
		return Math.sqrt(distanceSquaredTo(p));
	}
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	public Boolean equals(Point p) {
		return x == p.x && y == p.y;
	}
}

class Center extends Point {
	public float x,y;
	
	public Center() {
		this.x = 0;
		this.y = 0;
	}
	public Center(float x, float y) {
		this.x = x;
		this.y = y;
	}
	public Center(Center p) {
		this.x = p.x;
		this.y = p.y;
	}	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}

class Vector {
	public int x,y;
	
	public Vector() {
		this.x = 0;
		this.y = 0;
	}
	public Vector(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public Vector(Vector v) {
		this.x = v.x;
		this.y = v.y;
	}
	public double getLength() {
		return Math.sqrt(x*x + y*y);
	}
	public Vector plus(int x, int y) {
		return new Vector(this.x + x, this.y + y);
	}
	public Vector plus(Vector v) {
		return plus(v.x, v.y);
	}
	public static Vector plus(Vector v1, Vector v2) {
		return v1.plus(v2);
	}
	public Vector minus(int x, int y) {
		return new Vector(this.x - x, this.y - y);
	}
	public Vector minus(Vector v) {
		return minus(v.x, v.y);
	}
	public static Vector minus(Vector v1, Vector v2) {
		return v1.minus(v2);
	}
	public Vector times(int s) {
		return new Vector(x*s, y*s);
	}	
	public double dot(Vector v) {
		return (double) (x*v.x + y*v.y);
	}
	public static double dot(Vector v1, Vector v2) {
		return v1.dot(v2);
	}
	public double cross(Vector v) {
		return (double) (x*v.y-y*v.x);
	}
	public static double cross(Vector v1, Vector v2) {
		return v1.cross(v2);
	}
	public Vector normalize() {
		Vector v = new Vector();
		double temp = (int)(x*x + y*y);
		if (temp != 0) {
			temp = 1 / Math.sqrt(temp);
			v.x = (int) (x * temp);
			v.y = (int) (y * temp);
		}
		return v;
	}
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}

class Ray {
	public Point p;
	public Vector v;
	
	public Ray() {
		p = new Point();
		v = new Vector();
	}
	public Ray(Point p, Vector v) {
		this.p = p;
		this.v = v;
	}
	public Boolean intersects(Line L) {
		if (parallelTo(L)) return false;
		Vector v1 = new Vector(p.x-L.q.x,p.y-L.q.y);
		Vector v2 = new Vector(L.p.x-L.q.x,L.p.y-L.q.y);
		Vector v3 = new Vector(-v.y,v.x);
		return v2.cross(v1)/v2.dot(v3) >= 0;
	}
	public Boolean parallelTo(Line L) {
		Vector v1 = new Vector(L.q.x-L.p.x, L.q.y-L.p.y);
		return v.cross(v1) == 0;
	}
	public String toString() {
		return p.toString() + "->" + v.toString();
	}
}

class Line {
	Point p;
	Point q;
	
	public Line() {
		p = new Point();
		q = new Point();
	}	
	public Line(Point p, Point q) {
		this.p = p;
		this.q = q;
	}
	public String toString() {
		return p.toString() + "<->" + q.toString();
	}
}

class Wedge {
	public Vector u;
	public Vector v;
	
	public Wedge() {
		u = new Vector();
		v = new Vector();
	}
	public Wedge(Vector u, Vector v) {
		this.u = u;
		this.v = v;
	}
	public String toString() {
		return u.toString() + "X" + v.toString();
	}
}

class Vertex {
	public Point p;
	public Vertex nextVertex;
	public Vertex prevVertex;
	public Line suppLine;
	
	public Vertex() {
		p = new Point();
		nextVertex = null;
		prevVertex = null;
		suppLine = null;
	}	
	public Vertex(Point p) {
		this.p = p;
		nextVertex = null;
		prevVertex = null;
		suppLine = null;
	}
	public Vertex(Point p, Vertex nextVertex, Vertex prevVertex) {
		this.p = p;
		this.nextVertex = nextVertex;
		suppLine = new Line(prevVertex.p, nextVertex.p);
	}
	public String toString() {
		return p.toString();
	}
}

class Polygon {
	public Vertex start;
	public Vertex curVertex;
	public int count;
	
	public Polygon() {
		count = 0;
		start = null;
		curVertex = null;
	}
	public Polygon(Point p) {
		count = 0;
		start = null;
		add(p);
	}
	public Polygon(Point[] vertexList) {
		count = 0;
		start = null;
		for (Point p : vertexList) {
			add(p);
		}
		if (start != null) start.suppLine = new Line(start.prevVertex.p, start.p);
		curVertex = start;
	}
	public Vertex getStart() {
		return start;
	}
	public Vertex getCurVertex() {
		return curVertex;
	}
	public Line getCurLine() {
		return curVertex.suppLine;
	}
	public int getCount() {
		return count;
	}
	public boolean isEmpty() {
		return start == null;
	}
	public void add(Point p) {
		Vertex v = new Vertex(p);
		if (isEmpty()) {
			start = v;
			curVertex = start;
			v.nextVertex = v;
			v.prevVertex = v;
		}
		else {
			v.nextVertex = curVertex.nextVertex;
			v.prevVertex = curVertex;
			curVertex.nextVertex = v;
			v.nextVertex.prevVertex = v;
			curVertex = v;
		}
		curVertex.suppLine = new Line(curVertex.prevVertex.p, curVertex.p);
		count++;
	}
	public void remove() {
		if (isEmpty()) return;
		
		if (count == 1) curVertex = null;
		else {
			Vertex temp = curVertex;
			temp.prevVertex.nextVertex = temp.nextVertex;
			temp.nextVertex.prevVertex = temp.prevVertex;
			curVertex = curVertex.nextVertex;
			temp.nextVertex = temp.prevVertex = null;
			curVertex.suppLine = new Line(curVertex.prevVertex.p, curVertex.p);
		}
		count--;
	}
	public void advance() {
		curVertex = curVertex.nextVertex;
	}
	public Point generateZ() {
		double minY = Double.MAX_VALUE;
		double maxX = Double.MIN_VALUE;
		Vertex temp = start;
		if (!isEmpty()) {
			do {
				if (temp.p.x > maxX) maxX = temp.p.x;
				if (temp.p.y < minY) minY = temp.p.y;
				temp = temp.nextVertex;
			} while (temp != start);
		}
		return new Point((int)Math.floor(maxX), (int)Math.floor(minY));
	}
	public void print() {
		Vertex temp = start;
		if (!isEmpty()) {
			do {
				System.out.println(temp.p);
				temp = temp.nextVertex;
			} while (temp != start);
		}
	}
	public Boolean contains(Point z) {
		Vertex temp = start;
		if (!isEmpty()) {
			do {
				if (temp.p.equals(z)) return true;
				temp = temp.nextVertex;
			} while (temp != start);
		}
		return false;
	}
}

class Disk {
	public Center center;
	public float radius;
	public double radius2;
	
	public Disk() {
		center = new Center();
		radius = 1;
		radius2 = radius * radius;
	}
	public Disk(Center center, float radius) {
		this.center = center;
		this.radius = radius;
		radius2 = radius * radius;
	}
	public Point generateZ() {
		double maxX = center.x + radius;
		double minY = center.y - radius;
		return new Point((int)Math.floor(maxX), (int)Math.floor(minY));
	}
}

class ConvexPolyIntHull {
	private Polygon convexHull = new Polygon();
	
	public ConvexPolyIntHull(Point[] vertexList) {
		Stack<Wedge> S = (Stack<Wedge>) IntHull.S.clone();
		
		Polygon inputPoly = new Polygon(vertexList);
		Point z = inputPoly.generateZ();
		
		if (inputPoly.getCount() >= 3) convexHull = ProcessWedge(S, z, inputPoly);
	}
	private double ccw(Point p, Point q, Point r) {
		return (q.x*r.y-r.x*q.y) - p.x*(r.y-q.y) + p.y*(r.x-q.x);
	}	
	private double ccw(Point p , Point q, Vector v) {
		return (q.x*v.y-v.x*q.y) -p.x*(v.y-0) + p.y*(v.x-0);
	}
	private double ccw(Point p , Vector v, Point r) {
		return (v.x*r.y-r.x*v.y) -p.x*(0-v.y) + p.y*(0-v.x);
	}
	private double ccw(Line L, Point r) {
		return ccw(L.p, L.q, r);
	}
	private Point lastOnOrBefore(Point z, Vector v, Line L) {
		int a;
		double temp = ccw(L.p, L.q, v);
		if (temp == 0) a = 1;
		else a = (int) Math.floor(-ccw(L.p, L.q, z) / temp);
		return new Point(z.x+v.x*a,z.y+v.y*a);
	}
	private Polygon ProcessWedge(Stack<Wedge> S, Point z, Polygon P) {
		Polygon convexHull = new Polygon();
		Point p = P.getCurVertex().p;
		Line L = new Line(p, new Point(p.x,p.y+1));
		Boolean discard = true;
		while (!S.isEmpty()) {
			Wedge w = S.pop();
			if (w.equals(IntHull.III)) {
				discard = false;
			}
			Ray zv = new Ray(z,w.v);
			//System.out.println("zv:" + zv + " L: " + L + " p: " + p);
			if (zv.parallelTo(L) || zv.intersects(L)) {
				while (ccw(z, w.v, p) < 0) {
					P.advance();
					p = P.getCurVertex().p;
					L = P.getCurLine();
					if (ccw(L, z) < 0) return null;
				}
				z = lastOnOrBefore(z, w.v, L);
				if (!convexHull.contains(z) && !discard) {
					convexHull.add(z);
				}
			}
			else {
				S.push(new Wedge(w.u.plus(w.v), w.v));
				S.push(new Wedge(w.u, w.u.plus(w.v)));
			}
		}
		return convexHull;
	}
	public void print() {
		if (convexHull != null) convexHull.print();
	}
}

class DiskIntHull {
	private Polygon convexHull = new Polygon();
	public long duration;
	public int caseA;
	public int caseB;
	public int caseC;
	public int caseD;
	
	public DiskIntHull(Center center, float radius) {
		Stack<Wedge> S = (Stack<Wedge>) IntHull.S.clone();
		
		Disk inputDisk = new Disk(center, radius);
		Point z = inputDisk.generateZ();
		duration = 0;
		
		caseA = 0;
		caseB = 0;
		caseC = 0;
		caseD = 0;
		
		if (radius > 0) convexHull = ProcessWedge(S, z, inputDisk);
	}
	private int lastOnOrBefore(Point z, Vector v, Center center, float radius) {
		double A = (double)v.x*(double)v.x + (double)v.y*(double)v.y; // v.dot(v);
		double B = ((double)z.x-(double)center.x)*(double)v.x + ((double)z.y-(double)center.y)*(double)v.y; // z.minus(center).dot(v);
		double C = ((double)z.x-(double)center.x)*((double)z.x-(double)center.x) + ((double)z.y-(double)center.y)*((double)z.y-(double)center.y) - (double)radius * (double)radius;  // z.minus(center).dot(z.minus(center)) - radius * radius;
		
		double disc = B*B - C*A;
		if (disc < 0) return -1;
		else if (disc == 0) return 0;
		else return (-C==(B + Math.sqrt(disc))) ? 1 : (int) Math.floor(-C/(B + Math.sqrt(disc)));
	}
	private int lastOnOrOutside(Point z, Vector v, Center center, float radius) {
		double A = (double)v.x*(double)v.x + (double)v.y*(double)v.y; // v.dot(v);
		double B = ((double)z.x-(double)center.x)*(double)v.x + ((double)z.y-(double)center.y)*(double)v.y; // z.minus(center).dot(v);
		double C = ((double)z.x-(double)center.x)*((double)z.x-(double)center.x) + ((double)z.y-(double)center.y)*((double)z.y-(double)center.y) - (double)radius * (double)radius;  // z.minus(center).dot(z.minus(center)) - radius * radius;
		
		double disc = B*B - C*A;
		if (disc < 0) return -1;
		else return (int) Math.ceil((-B - Math.sqrt(disc)) / A) - 1;
	}
	private Polygon ProcessWedge(Stack<Wedge> S, Point z, Disk D) {
		Polygon convexHull = new Polygon();
		
		Wedge w = S.pop();
		Vector u = w.u;
		Vector v = w.v;
		
		Center c = D.center;
		float r = D.radius;
		long startTime = System.nanoTime();
		while (!S.isEmpty()) {
//			long temptime = System.nanoTime() - startTime;
			
			Point z1 = z.plus(u).plus(v);
			int alpha = lastOnOrBefore(z1, u, c, r);
//			System.out.println("alpha=" + alpha);
			
			// Case D
			if (alpha >= 0) {
//				if (temptime/1000000000.0 > 5) System.out.println("case D");
				S.push(new Wedge(u, v));
				v = v.plus(u.times(alpha+1));
//				System.out.println("v="+v);
				caseD++;
				
			}
			else {
				alpha = lastOnOrOutside(z.plus(u), v, c, r);
//				System.out.println("alpha=" + alpha);
				// Case C
				if (alpha > 0) {
//					if (temptime/1000000000.0 > 5) System.out.println("case C");
					u = u.plus(v.times(alpha));
//					System.out.println("u=" + u);
					caseC++;
				}/*
				else if (alpha == 0) {
					alpha = lastOnOrBefore(z, u, c, r);
					if (alpha > 0) {
						z = z.plus(u.times(alpha));
						convexHull.add(z);
					}
					w = S.pop();
					u = w.u;
					v = w.v;	
				}*/
				else {
					alpha = lastOnOrBefore(z, v, c, r);
//					System.out.println("alpha=" + alpha);
					if (alpha > 0) {
						z = z.plus(v.times(alpha));
//						if (temptime/1000000000.0 > 5) System.out.println("case B");
						convexHull.add(z);
						caseB++;
					}
					else { 
//						if (temptime/1000000000.0 > 5) System.out.println("case A");
						caseA++;
					}
					w = S.pop();
					u = w.u;
					v = w.v;
//					System.out.println("u=" + u + ";v=" + v);
				}
			}
		}
		long endTime = System.nanoTime();
		duration = (endTime - startTime);
		return convexHull;
	}
	public int getCount() {
		return convexHull.getCount();
	}
	public void print() {
		if (convexHull != null) convexHull.print();
	}
}

public class IntHull {
	private static final Vector posX = new Vector(1,0);
	private static final Vector negX = new Vector(-1,0);
	private static final Vector posY = new Vector(0,1);
	private static final Vector negY = new Vector(0,-1);
	public static final Wedge I = new Wedge(posX, posY);
	public static final Wedge II = new Wedge(posY, negX);
	public static final Wedge III = new Wedge(negX, negY);
	public static final Wedge IV = new Wedge(negY, posX);
	public static final Stack<Wedge> S = new Stack<Wedge>();
	
	private static void initializeStack() {
//		S.push(IV);
//		S.push(III);
		S.push(II);
		S.push(I);
		S.push(IV);
		S.push(III);
		S.push(II);
		S.push(I);
	}

	public static void main(String[] args) {
		initializeStack();
		
		Boolean testDisk = true;
		
		int trials = 100000;
		
		Point[] centers = new Point[trials];
		float[] durations = new float[trials];
		int[] pointsOnHull = new int[trials];
		int[] caseACount = new int[trials];
		int[] caseBCount = new int[trials];
		int[] caseCCount = new int[trials];
		int[] caseDCount = new int[trials];
		
		if (!testDisk) {
			// Convex Polygon Input
			Point[] vertexList = {
//					new Point(2.3,-1.5),
//			        new Point(1.7,0.7),
//					new Point(-0.7,-0.3),
//			        new Point(-0.7,-1.5),
//			        new Point(0,-2.8)
					};
			
			ConvexPolyIntHull H1 = new ConvexPolyIntHull(vertexList);
			H1.print();
		}
		else {
			for (int i = 0; i < trials; i++) {
				// Disk Input
				Center center = new Center((float)Math.random(), (float)Math.random());
//				Center center = new Center(0,0);
				float radius = 1000000;
			
				DiskIntHull H2 = new DiskIntHull(center, radius);
//				H2.print();
				System.out.println("trial: " + i + " " + center);
				durations[i] = (float) (H2.duration/1000000.0);
				pointsOnHull[i] = H2.getCount();
				caseACount[i] = H2.caseA;
				caseBCount[i] = H2.caseB;
				caseCCount[i] = H2.caseC;
				caseDCount[i] = H2.caseD;
				centers[i] = center;
//				System.out.println("Center: " + center + "\nRadius: " + radius + "\nPoints on Hull: " + H2.getCount() + "\nDuration: " + H2.duration/1000000000.0 + "s");
			}
			System.out.println("\n================\nCenters\n================");
			for (int i = 0; i < trials; i++) {
				System.out.println(centers[i]);
			}
			System.out.println("\n================\nDurations (ms)\n================");
			for (int i = 0; i < trials; i++) {
				System.out.println(durations[i]);
			}
			System.out.println("\n================\nPoints On Hull\n================");
			for (int i = 0; i < trials; i++) {
				System.out.println(pointsOnHull[i]);
			}
			System.out.println("\n================\nCase A Counts\n================");
			for (int i = 0; i < trials; i++) {
				System.out.println(caseACount[i]);
			}
			System.out.println("\n================\nCase B Counts\n================");
			for (int i = 0; i < trials; i++) {
				System.out.println(caseBCount[i]);
			}
			System.out.println("\n================\nCase C Counts\n================");
			for (int i = 0; i < trials; i++) {
				System.out.println(caseCCount[i]);
			}
			System.out.println("\n================\nCase D Counts\n================");
			for (int i = 0; i < trials; i++) {
				System.out.println(caseDCount[i]);
			}
		}
	}
}