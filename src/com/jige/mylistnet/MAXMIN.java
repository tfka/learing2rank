package com.jige.mylistnet;

class MAXMIN
{
	private
		double mymax;
		double mymin;
	public void MAXMIN()
	{
		mymax = 0;
		mymin = 1;
	}
	public void set_mymax(double mymax)
	{
		this.mymax = mymax;
	}
	public void set_mymin(double mymin)
	{
		this.mymin = mymin;
	}
	public double get_mymax()
	{
		return this.mymax;
	}
	public double get_mymin()
	{
		return this.mymin;
	}
	
}
