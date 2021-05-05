//Dylan Lassard
//5-1-21
//CS 371-Software Development
//P4: Testing file name Circle2.java test that runs with JUnit testing tool

package edu.nmsu.cs.circles;

public class Circle2 extends Circle
{

	public Circle2(double x, double y, double radius)
	{
		super(x, y, radius); //fixed error where the y was in front of the x, so now it is "super(x, y, radius)"
	}

	public boolean intersects(Circle other)
	{
		double d;
		d = Math.sqrt(Math.pow(center.x - other.center.x, 2) +
				Math.pow(center.y - other.center.y, 2));
		if (d < radius)
			return true;
		else
			return false;
	}

} //end Circle2 class
