package com.jige.mylistnet;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class Module 
{
	public double[] weights;
	
	
	public static Module getModule(double[] weights) 
	{
		Module m = new Module();
		m.weights = new double[weights.length];
		for(int i = 0 ; i < m.weights.length; i++ )
		{
			m.weights[i] = weights[i];
		}
		return m;
	}
	public boolean write(File f) throws IOException
	{
		DataOutputStream writer = new DataOutputStream(new FileOutputStream(f));
		writer.writeInt(weights.length);
		for( int i = 0 ; i < weights.length; i++)
		{
			writer.writeDouble(weights[i]);
		}
		writer.close();
		return true;
	}
	public boolean write(String f) throws IOException
	{
		FileOutputStream fs = new FileOutputStream(new File(f));
		PrintStream p = new PrintStream(fs);
		p.print(weights.length + "\n");
		for(int i = 0 ; i < weights.length; i++)
		{
			p.print(weights[i] + " ");
		}
		p.close();
		return true;
	}
}
