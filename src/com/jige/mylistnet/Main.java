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
		String train = "D:/essay_generation/ltrtrain.txt";
		String module_save = "D:/essay_generation/ltr.module";
		String test = "D:/essay_generation/ltrtest.txt";
		int savestep = 500;
		int step = 40000;
		double alpha = 3;
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
		System.out.print("finish normalizer\n");
		mymain.featrue_num = normalizer.NormalizerData();
		if(mymain.featrue_num == -1) 
		{
			System.out.print("data normalizer error\n");
			System.exit(-1);
		}
		ReadData readdata = new ReadData(mymain.featrue_num,train+"normalize",test+"normalize");
		System.out.print("finish read train data\n");
		if( ! readdata.Read())
		{
			System.out.print("data read error\n");
			System.exit(-1);
		}
		Train mytrain = new Train(readdata,step,savestep,module_save,alpha);
		
		Module mymodule = mytrain.train();
		System.out.print("finish train\n");
		mymodule.write(module_save);	
		
	}
	
}
