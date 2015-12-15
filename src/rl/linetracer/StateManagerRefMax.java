package rl.linetracer;

import rl.Step;

public class StateManagerRefMax implements StateManager
{
	private StateRefMax[] state;

	public StateManagerRefMax()
	{
		state = null;
	}

	@Override
	public void getCurrentState(Step step, MachineControl MC)
	{
		//CostMax秒に達していない場合
		//継続してEpisodeを進める
		double r=MC.GetReflection();
		//測定値に対応するStateを定める
		for(int i=1;i<state.length;i++)
		{
			if(r<state[i].RefMax)
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
		state=new StateRefMax[statecount];

	}

	public void setState(int i, double refmax, int controlcount)
	{
		if(
				(i<0)||
				(i>=state.length)||
				(refmax<0.0)||
				(refmax>1.0)||
				(controlcount<0)
			)
		{
			throw new IllegalArgumentException();
		}
		state[i]=new StateRefMax(refmax, controlcount);
	}


	@Override
	public int getControlCount(int i)
	{
		return state[i].ControlCount;
	}

	@Override
	public StateRefMax _getState(int i)
	{
		return state[i];
	}

	@Override
	public int getStateCount()
	{
		return state.length;
	}

}
