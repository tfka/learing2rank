package com.jige.mylistnet;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
public class ReadData 
{
	int featrue_num;
	double[] featrue;
	List<Sample> Samples = new ArrayList<Sample>();
	String nomalize_train;
	String nomalize_test;
	int cur = 0;
	
	public ReadData(int featrue_num,String nomalize_train,String nomalize_test)
	{
		this.featrue_num = featrue_num;
		featrue = new double[this.featrue_num];
		this.nomalize_train = nomalize_train;
		this.nomalize_test = nomalize_test;
	}
	public boolean Read() throws IOException
	{
		Sample sample = new Sample();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(nomalize_train),"UTF-8"));
		String line = br.readLine();
		if (line == null) return false;
		else
		{
			String[] parts = line.trim().split(" ");
			sample.setQid(Integer.parseInt(parts[1]));
			for(int i = 3; i < parts.length - 1; i++)
			{
				featrue[i - 3] = Integer.parseInt(parts[i]);
			}
			sample.addFeatrues(featrue);
			sample.addReses(Integer.parseInt(parts[parts.length - 1]));
		}
		while((line = br.readLine()) != null )
		{
			String parts[] = line.trim().split(" ");
			int tmp_qid = Integer.parseInt(parts[1]);
			
			if( tmp_qid != sample.getQid() )//如果查询id改变，需要得到一个新的sample
			{
				Samples.add(sample);
				sample = new Sample();
				sample.setQid(tmp_qid);
			}
			for(int i = 3; i < parts.length - 1; i++)
			{
				featrue[i - 3] = Integer.parseInt(parts[i]);
			}
			sample.addFeatrues(featrue);
			sample.addReses(Integer.parseInt(parts[parts.length - 1]));
		}
		return true;
		
	}
	public void setCur()
	{
		cur = 0;
	}
	public Sample getNextSample()
	{
		if(cur >= Samples.size())
			return null;
		return Samples.get(cur);
	}

}
