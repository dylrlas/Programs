//Dylan Lassard
//5-1-21
//CS 371-Software Development
//P4: Testing file name Circle1Test.java test that runs with JUnit testing tool

package edu.nmsu.cs.circles;

/***
 * Example JUnit testing class for Circle1 (and Circle)
 *
 * - must have your classpath set to include the JUnit jarfiles - to run the test do: java
 * org.junit.runner.JUnitCore Circle1Test - note that the commented out main is another way to run
 * tests - note that normally you would not have print statements in a JUnit testing class; they are
 * here just so you see what is happening. You should not have them in your test cases.
 ***/

import org.junit.*;

public class Circle1Test
{
	// Data you need for each test case
	private Circle1 circle1a;
	private Circle1 circle1b; //second circle added to data segment
	private Circle1 circle1c; //third circle added to data segment

	//
	// Stuff you want to do before each test case
	//
	@Before
	public void setup()
	{
		System.out.println("\nTest starting...");
		circle1a = new Circle1(1, 2, 3);
		circle1b = new Circle1(2, 3, 12); //test values for second circle
		circle1c = new Circle1(8, 9, 9); //test values for third circle
	}

	//
	// Stuff you want to do after each test case
	//
	@After
	public void teardown()
	{
		System.out.println("\nTest finished.");
	}

	//
	// Test a simple positive move
	//
	@Test
	public void simpleMove()
	{
		Point p;
		System.out.println("Running test simpleMove.");
		p = circle1a.moveBy(1, 1);
		Assert.assertTrue(p.x == 2 && p.y == 3);
	}

	//
	// Test a simple negative move
	//
	@Test
	public void simpleMoveNeg()
	{
		Point p;
		System.out.println("Running test simpleMoveNeg.");
		p = circle1a.moveBy(-1, -1);
		Assert.assertTrue(p.x == 0 && p.y == 1);
	}
	
	//
	//Test a stationary move
	//
	@Test
	public void simpleMoveZero()
	{
		Point p;
		System.out.println("Running test simpleMoveZero.");
		p = circle1a.moveBy(0, 0);
		Assert.assertTrue(p.x == 1 && p.y == 2);
	}
	
	//Test positive scaling
	@Test
	public void positiveScaling () {
		
		double radius;
		System.out.println("Running test positiveScaling");
		radius = circle1a.scale(2.0);
		Assert.assertTrue(radius == 6.0);
	}
	
	//Test negative scaling
	@Test
	public void negativeScaling () {
		
		double radius;
		System.out.println("Running test negativeScaling");
		radius = circle1a.scale(-0.5);
		Assert.assertTrue(radius == -1.5);
	}
	
	//Test stationary scaling
	@Test
	public void zeroScaling () {
		
		double radius;
		System.out.println("Running test zeroScaling");
		radius = circle1a.scale(0);
		Assert.assertTrue(radius == 0);
	}
	
	//Simple intersecting check to be proven false
	@Test
	public void intersectsTrue () {
		System.out.println("Running test intersectsIsTrue");
		Assert.assertTrue(circle1a.intersects(circle1b));
	}
	
	//Simple intersecting check to be proven false
	
	@Test
	public void intersectsFalse () {
		System.out.println("Running test intersectsIsFalse");
		Assert.assertFalse(circle1a.intersects(circle1c));
	}

	/***
	 * NOT USED public static void main(String args[]) { try { org.junit.runner.JUnitCore.runClasses(
	 * java.lang.Class.forName("Circle1Test")); } catch (Exception e) { System.out.println("Exception:
	 * " + e); } }
	 ***/

} //end Circle1Test class
