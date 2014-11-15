package rl.linetracer.communication.old;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;

import rl.communication.old.Command;
import rl.linetracer.EV3LineTracer;

public class CommandSetMDP implements Command
{
	private EV3LineTracer EV3LineTracer;
	
	public CommandSetMDP(EV3LineTracer ev3linetracer)
	{
		EV3LineTracer=ev3linetracer;
	}
	
	@Override
	public void DoCommand(BufferedReader body, BufferedWriter result)
			throws IOException
	{
		try
		{
			//Intervalの読み取り
			ReadInterval(body);
			
			//State数の読み取り
			ReadStateCount(body);
			
			//StateとControlCountの読み取り
			ReadState(body);
			
			//Controlの読み取り
			ReadControl(body);
			
			//RegularPolicyの読み取り
			ReadRegularPolicy(body);
			
			//結果をresultに格納
			result.write("OK");
			result.newLine();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result.write("NG");
			result.newLine();
			//StackTraceをresultに追加する
			e.printStackTrace(new PrintWriter(result));
			throw e;
		}
	}

	private void ReadRegularPolicy(BufferedReader br)
			throws IOException
	{
		String s;
		int statecount=EV3LineTracer.GetStateCount();
		double[][] prob=new double[statecount][];
		for(int i=0;i<statecount;i++)
		{
			int controlcount=EV3LineTracer.GetControlCount(i);
			prob[i]=new double[controlcount];
			for(int u=0;u<prob[i].length;u++)
			{
				prob[i][u]=0.0;
			}
		}
		for(int i=0;i<statecount;i++)
		{
			s=br.readLine();
			String[] t=s.split("\t");
			//State Indexの確認
			if(i!=Integer.parseInt(t[0]))
			{
				throw new IOException();
			}
			int controlcount=EV3LineTracer.GetControlCount(i);
			//Control Indexの読み取り
			int u=Integer.parseInt(t[1]);
			if(
					(u<0)||
					(u>=controlcount)
				)
			{
				throw new IOException();
			}
			prob[i][u]=1.0;
		}
		EV3LineTracer.SetRegularPolicy(prob);
	}
	
	//Controlの読み取り
	private void ReadControl(BufferedReader br)
			throws IOException
	{
		String s;
		int statecount=EV3LineTracer.GetStateCount();
		for(int i=0;i<statecount;i++)
		{
			int controlcount=EV3LineTracer.GetControlCount(i);
			for(int u=0;u<controlcount;u++)
			{
				s=br.readLine();
				String[] t=s.split("\t");
				//State Indexの確認
				if(i!=Integer.parseInt(t[0]))
				{
					throw new IOException();
				}
				//Control Indexの確認
				if(u!=Integer.parseInt(t[1]))
				{
					throw new IOException();
				}
				//LMotorSpeedの取得
				int lmotorspeed=Integer.parseInt(t[2]);
				//LMotorSpeedの取得
				int rmotorspeed=Integer.parseInt(t[3]);
				EV3LineTracer.SetControl(i,u,lmotorspeed,rmotorspeed);
			}
		}
	}
	//StateとControlCountの読み取り
	private void ReadState(BufferedReader br)
			throws IOException
	{
		String s;
		int statecount=EV3LineTracer.GetStateCount();
		for(int i=0;i<statecount;i++)
		{
			s=br.readLine();
			String[] t=s.split("\t");
			//State Indexの確認
			if(i!=Integer.parseInt(t[0]))
			{
				throw new IOException();
			}
			//RefMaxの取得
			double refmax=Double.parseDouble(t[1]);
			//ControlCountの取得
			int controlcount=Integer.parseInt(t[2]);
			EV3LineTracer.SetState(i,refmax,controlcount);
		}
	}

	//State数の読み取り
	private void ReadStateCount(BufferedReader br) throws IOException
	{
		String s;
		s=br.readLine();
		int statecount=Integer.parseInt(s);
		EV3LineTracer.SetStateCount(statecount);
	}

	//Intervalの読み取り
	private void ReadInterval(BufferedReader br) throws IOException
	{
		String s=br.readLine();
		int interval=Integer.parseInt(s);
		EV3LineTracer.SetInterval(interval);
	}
}
