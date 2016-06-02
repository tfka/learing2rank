package com.jige.mylistnet;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import com.jige.mylistnet.MAXMIN;
//这里是连带train和test数据一起规范化。
public class Normalizer 
{
	private String train;
	private String test;
	private int feature_num;
	List<MAXMIN> feature_maxmin = new ArrayList<MAXMIN>();
	List<String> traindata = new ArrayList<String>();
	List<String> testdata = new ArrayList<String>();
	
	public Normalizer(String train,String test)
	{
		this.train = train;
		this.test = test;
	}
	public int NormalizerData() throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(this.train),"UTF-8"));
		BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(this.test),"UTF-8"));
		String line;
		line = br.readLine();
		if (line == null)
			return -1;
		else 
		{
			traindata.add(line);
			String[] parts = line.trim().split(" ");
			this.feature_num = parts.length - 4;//对于每一行，第一个是doc，第二个是sen1，第三个是sen2，所以feature是从第4个开始到倒数第二个，最后一个是y
			//feature_maxmin = new LinkedList
			for(int i = 0 ; i < feature_num; i++)
			{
				feature_maxmin.add(new MAXMIN());
			}
			for(int i = 3; i < parts.length - 1; i++)
			{
				if(Double.parseDouble(parts[i]) > feature_maxmin.get(i - 3).get_mymax())
				{
					feature_maxmin.get(i - 3).set_mymax(Double.parseDouble(parts[i]));
				}
				if(Double.parseDouble(parts[i]) < feature_maxmin.get(i - 3).get_mymin())
				{
					feature_maxmin.get(i - 3).set_mymin(Double.parseDouble(parts[i]));
				}
			}
			
		}
		while((line = br.readLine()) != null)
		{
			traindata.add(line);
			String[] parts = line.trim().split(" ");
			for(int i = 3; i < parts.length - 1; i++)
			{
				if(Double.parseDouble(parts[i]) > feature_maxmin.get(i - 3).get_mymax())
				{
					feature_maxmin.get(i - 3).set_mymax(Double.parseDouble(parts[i]));
				}
				if(Double.parseDouble(parts[i]) < feature_maxmin.get(i - 3).get_mymin())
				{
					feature_maxmin.get(i - 3).set_mymin(Double.parseDouble(parts[i]));
				}
			}
		}
		while((line = br1.readLine()) != null)
		{
			testdata.add(line);
			String[] parts = line.trim().split(" ");
			for(int i = 3; i < parts.length - 1; i++)
			{
				if(Double.parseDouble(parts[i]) > feature_maxmin.get(i - 3).get_mymax())
				{
					feature_maxmin.get(i - 3).set_mymax(Double.parseDouble(parts[i]));
				}
				if(Double.parseDouble(parts[i]) < feature_maxmin.get(i - 3).get_mymin())
				{
					feature_maxmin.get(i - 3).set_mymin(Double.parseDouble(parts[i]));
				}
			}	
		}
		FileOutputStream fs = new FileOutputStream(new File(this.train + "normalize"));
		PrintStream p = new PrintStream(fs);
		FileOutputStream fs1 = new FileOutputStream(new File(this.test + "normalize"));
		PrintStream p1 = new PrintStream(fs1);
		for(int i = 0 ; i < traindata.size() ; i++)
		{
			String[] parts = traindata.get(i).trim().split(" ");
			p.print(parts[0] + " "+parts[1] +" " + parts[2] + " ");
			for(int j = 3 ; j < parts.length - 1 ; j++)
			{
				double tmp;
				if(feature_maxmin.get(j - 3).get_mymax() == feature_maxmin.get(j - 3).get_mymin())
				{
					tmp = 0;
				}
				else
				{
					tmp = (Double.parseDouble(parts[j]) - feature_maxmin.get(j - 3).get_mymin()) / (feature_maxmin.get(j - 3).get_mymax() - feature_maxmin.get(j - 3).get_mymin());
				}
				p.print(String.valueOf(tmp) + " ");
			}
			p.print(parts[parts.length - 1] + "\n");
		}
		for(int i = 0 ; i < testdata.size(); i++)
		{
			String[] parts = testdata.get(i).trim().split(" ");
			p1.print(parts[0] + " "+parts[1] +" " + parts[2] + " ");
			for(int j = 3 ; j < parts.length - 1 ; j++)
			{
				double tmp;
				if(feature_maxmin.get(j - 3).get_mymax() == feature_maxmin.get(j - 3).get_mymin())
				{
					tmp = 0;
				}
				else
				{
					tmp = (Double.parseDouble(parts[j]) - feature_maxmin.get(j - 3).get_mymin()) / (feature_maxmin.get(j - 3).get_mymax() - feature_maxmin.get(j - 3).get_mymin());
				}
				p1.print(String.valueOf(tmp) + " ");
			}
			p1.print(parts[parts.length - 1] + "\n");
			
		}
		return feature_num;
		
	}
	
	

}
