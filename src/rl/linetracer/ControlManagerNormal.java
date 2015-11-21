package rl.linetracer;

import rl.Step;

public class ControlManagerNormal extends ControlManager
{
	private Control[][] Control;
	
	public ControlManagerNormal()
	{
		Control = null;
	}

	@Override
	public void DoControl(Step step, MachineControl MC, int interval)
	{
		//Controlに対応するモータの速度を取得
		int lspeed=Control[step.State][step.Control].LMotorSpeed;
		int rspeed=Control[step.State][step.Control].RMotorSpeed;
		//取得したモータの速度で進む
		MC.GoForward(lspeed, rspeed);
		//Interval(msec)だけこの状態を維持
		MC.Delay(interval);
	}

	@Override
	public void _SetStateCount(int statecount)
	{
		//Controlの配列の配列の宣言
		Control=new Control[statecount][];
		
	}

	@Override
	public void _SetControlCount(int i, double refmax, int controlcount)
	{
		Control[i]=new Control[controlcount];
	}

	@Override
	public void _SetControl(int i, int u, int lmotorspeed, int rmotorspeed)
	{
		if(
				(i<0)||
				(i>=Control.length)||
				(u<0)||
				(u>=Control[i].length)
			)
		{
			throw new IllegalArgumentException();
		}
		Control[i][u]=new Control();
		Control[i][u].LMotorSpeed=lmotorspeed;
		Control[i][u].RMotorSpeed=rmotorspeed;
	}

	@Override
	public Control _GetControl(int i, int u)
	{
		return Control[i][u];
	}

}