package rl.linetracer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import rl.communication.Command;

public class CommandSetPolicy implements Command
{
	private EV3LineTracer EV3LineTracer;
	
	public CommandSetPolicy(EV3LineTracer ev3linetracer)
	{
		EV3LineTracer=ev3linetracer;
	}
	@Override
	public void DoCommand(BufferedReader body, BufferedWriter result)
			throws IOException
	{
		try
		{
			ReadCurrentPolicy(body);
			//結果をresultに格納
			result.write("OK\n");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result.write("NG\n");
			//StackTraceをresultに追加する
			e.printStackTrace(new PrintWriter(result));
			throw e;
		}
	}
	//Probabilityの読み取り
	private void ReadCurrentPolicy(BufferedReader br)
			throws IOException
	{
		String s;
		int statecount=EV3LineTracer.GetStateCount();
		double[][] prob=new double[statecount][];
		for(int i=0;i<statecount;i++)
		{
			s=br.readLine();
			String[] t=s.split("\t");
			int controlcount=EV3LineTracer.GetControlCount(i);
			if(t.length != controlcount+1)
			{
				throw new IllegalArgumentException("ControlCount is wrong.");
			}
			prob[i]=new double[controlcount];
			//State Indexの確認
			if(i!=Integer.parseInt(t[0]))
			{
				throw  new IllegalArgumentException("StateIndex is wrong.");
			}
			
			for(int u=0;u<controlcount;u++)
			{
				prob[i][u]=Double.parseDouble(t[u+1]);
			}
		}
		EV3LineTracer.SetCurrentPolicy(prob);

	}
}
