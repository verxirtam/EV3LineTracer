package rl.linetracer;

import rl.Step;

public class ControlManagerNormal implements ControlManager
{
	int interval;
	private ControlNormal[][] control;
	
	public ControlManagerNormal()
	{
		control = null;
	}

	@Override
	public void doControl(Step step, MachineControl MC)
	{
		//Controlに対応するモータの速度を取得
		int lspeed=control[step.State][step.Control].LMotorSpeed;
		int rspeed=control[step.State][step.Control].RMotorSpeed;
		//取得したモータの速度で進む
		MC.GoForward(lspeed, rspeed);
		//Interval(msec)だけこの状態を維持
		MC.Delay(interval);
	}

	public void setStateCount(int statecount)
	{
		//Controlの配列の配列の宣言
		control=new ControlNormal[statecount][];
		
	}

	@Override
	public void setControlCount(int i, int controlcount)
	{
		control[i]=new ControlNormal[controlcount];
	}

	@Override
	public void setControl(int i, int u, int lmotorspeed, int rmotorspeed)
	{
		if(
				(i<0)||
				(i>=control.length)||
				(u<0)||
				(u>=control[i].length)
			)
		{
			throw new IllegalArgumentException();
		}
		control[i][u]=new ControlNormal();
		control[i][u].LMotorSpeed=lmotorspeed;
		control[i][u].RMotorSpeed=rmotorspeed;
	}

	@Override
	public Control _GetControl(int i, int u)
	{
		return control[i][u];
	}

	@Override
	public int _getInterval()
	{
		return interval;
	}

	public void setInterval(int t)
	{
		if(t<0)
		{
			throw new IllegalArgumentException();
		}
		interval=t;
	}

}
