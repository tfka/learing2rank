package com.jige.mylistnet;

import java.util.ArrayList;
import java.util.List;

public class Sample 
{
	private int qid;
	
	private List<double[]> featrues = new ArrayList<double[]>();
	
	private List<Integer> reses = new ArrayList<Integer>();
	
	public Sample()
	{
		
	}
	public void setQid(int qid)
	{
		this.qid = qid;
	}
	public int getQid()
	{
		return qid;
	}
	public void addFeatrues(double[] featrue)
	{
		featrues.add(featrue);
	}
	public List<double[]> getFeatrues()
	{
		return featrues;
	}
	public void addReses(int res)
	{
		Integer tmp = Integer.valueOf(res);
		reses.add(tmp);
	}
	public List<Integer> getReses()
	{
		return reses;
	}
}

