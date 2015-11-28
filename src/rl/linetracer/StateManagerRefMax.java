package rl.linetracer;

import rl.Step;

public class StateManagerRefMax implements StateManager
{
	private State[] State;

	public StateManagerRefMax()
	{
		State = null;
	}

	@Override
	public void getCurrentState(Step step, MachineControl MC)
	{
		//CostMax秒に達していない場合
		//継続してEpisodeを進める
		double r=MC.GetReflection();
		//測定値に対応するStateを定める
		for(int i=1;i<State.length;i++)
		{
			if(r<State[i].RefMax)
			{
				step.State=i;
				break;
			}
		}
	}

	public void setStateCount(int statecount)
	{
		if(statecount<=0)
		{
			throw new IllegalArgumentException();
		}
		State=new State[statecount];

	}

	public void setState(int i, double refmax, int controlcount)
	{
		if(
				(i<0)||
				(i>=State.length)||
				(refmax<0.0)||
				(refmax>1.0)||
				(controlcount<0)
			)
		{
			throw new IllegalArgumentException();
		}
		State[i]=new State();
		State[i].RefMax=refmax;
		State[i].ControlCount=controlcount;

	}


	@Override
	public int getControlCount(int i)
	{
		return State[i].ControlCount;
	}

	@Override
	public rl.linetracer.State _getState(int i)
	{
		return State[i];
	}

	@Override
	public int getStateCount()
	{
		return State.length;
	}

}
