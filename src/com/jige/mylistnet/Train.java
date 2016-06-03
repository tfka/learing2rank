package com.jige.mylistnet;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.util.Arrays;
import java.util.List;

public class Train
{
	private ReadData readdata;
	private int featrue_num;
	double[] weights;
	int step;
	int save_step;
	String sava;
	double alpha;//Ñ§Ï°ÂÊ
	public Train(ReadData readdata, int step, int save_step, String save, double alpha)
	{
		this.readdata = readdata;
		featrue_num = this.readdata.featrue_num;
		weights = new double[this.featrue_num];
		for(int i = 0 ; i < featrue_num; i++)
		{
			weights[i] = 1;
		}
		this.step = step;
		this.save_step = save_step;
		this.sava = save;
		this.alpha = alpha;
		
	}
	public Module train() throws IllegalClassFormatException,IOException
	{
		for(int i = 0 ; i < step; i++)
		{
			System.out.print(i + "\n");
			readdata.setCur();
			Sample sample = null;
			double[] oldweights = Arrays.copyOf(weights, weights.length);
			
			while( (sample = readdata.getNextSample()) != null )
			{
				List<double[]> featrues = sample.getFeatrues();
				List<Integer> Reses = sample.getReses();
				
				double[] Z = new double[featrues.size()];
				double[] ExpYList = new double[featrues.size()];
				double ExpYSum = 0.0;
				for(int doc = 0 ; doc < featrues.size(); doc++)
				{
					Z[doc] = DotMultiply.dotMutply(weights, featrues.get(doc));
					ExpYList[doc] = Math.exp(Reses.get(doc).intValue());
					ExpYSum += ExpYList[doc];
				}
				for(int f = 0 ; f < featrue_num; f++)
				{
					double deltaWP1 = 0.0;
					double deltaWP2 = 0.0;
					double deltaWP3 = 0.0;
					for(int doc = 0; doc < featrues.size();doc++)
					{
						deltaWP1 -= ((ExpYList[doc]) * featrues.get(doc)[f]);
						double expz = Math.exp(Z[doc]);
						deltaWP2 += (expz * featrues.get(doc)[f]);
						deltaWP3 += expz;
						
					}
					double deltaW = deltaWP1/ExpYSum + deltaWP2/deltaWP3;
					weights[f] -= alpha * deltaW;
				}
			}
			//System.out.print("xxx");
			if((save_step > 0 && (i % save_step == 0)) && step > 0 )
			{
				Module m = Module.getModule(weights);
				m.write("tmp" + "_" + String.valueOf(i) + ".module");
			}
			double sum = 0.0;
			for (int v=0; v<weights.length; v++)
			{
				sum += Math.pow(oldweights[v]-weights[v], 2);
			}
			//System.out.println(sum);
		}
		for(int i = 0 ; i < weights.length; i++)
			System.out.print(weights[i]+ " ");
		return Module.getModule(weights);
	}
	
}
