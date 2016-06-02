package com.jige.mylistnet;

import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.io.File;

// learing to rank й╣ож listnet
public class Main 
{
	private int featrue_num;
	public static void main(String[] args) throws IllegalClassFormatException,IOException
	{
		Main mymain = new Main();
		String train = "";
		String module_save = "";
		String test = "";
		int savestep = 500;
		int step = 4000;
		double alpha = 0.01;
		for (int i = 0 ; i < args.length; i++)
		{
			if("train" == args[i])
			{
				train = args[++i];
			}
			else if("module_save" == args[i])
			{
				module_save = args[++i];
			}
			else if("savestep" == args[++i])
			{
				savestep = Integer.parseInt(args[++i]);
			}
			else if("test" == args[i])
			{
				test = args[++i];
			}
			else if("alpha" == args[i])
			{
				alpha = Double.parseDouble(args[++i]);
			}
			else
			{
				step = Integer.parseInt(args[++i]);
			}
		}
		Normalizer normalizer = new Normalizer(train,test);
		mymain.featrue_num = normalizer.NormalizerData();
		if(mymain.featrue_num == -1) 
		{
			System.out.print("data normalizer error\n");
			System.exit(-1);
		}
		ReadData readdata = new ReadData(mymain.featrue_num,train+"nomalize",test+"nomalize");
		if( ! readdata.Read())
		{
			System.out.print("data read error\n");
			System.exit(-1);
		}
		Train mytrain = new Train(readdata,step,savestep,module_save,alpha);
		Module mymodule = mytrain.train();
		mymodule.write(new File(module_save));	
		
	}
	
}
