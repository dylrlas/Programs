//Dylan Lassard
//5-1-21
//CS 371-Software Development
//P4: Testing file name Circle1.java test that runs with JUnit testing tool

package edu.nmsu.cs.circles;

public class Circle1 extends Circle
{

	public Circle1(double x, double y, double radius)
	{
		super(x, y, radius);
	}

	public boolean intersects(Circle other)
	{
		if (Math.abs(center.x - other.center.x) < radius && Math.abs(center.y - other.center.y) < radius)
			return true;
		return false;
	}

} //end Circle1 class
