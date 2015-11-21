package rl.linetracer;

import rl.Step;

abstract public class MDPManager
{
	protected ControlManager controlManager;
	// 現在のStateとCostを取得する
	// argument:
	// Step step: 取得したStateを格納するStep
	// Step previousstep: 取得したCostを格納するStep
	abstract public void GetStateAndCost(Step step, Step previousstep, MachineControl MC);

	// 指定したControlに応じた行動を行う
	final public void DoControl(Step step, MachineControl MC,int interval)
	{
		controlManager.DoControl(step, MC, interval);
	}
	///////////////////////
	//以降期間限定のメソッド
	//メソッド名先頭に"_"をつけて区別する
	///////////////////////
	//StateCountを指定して、
	//Conrolの配列の領域を確保する
	final public void _SetStateCount(int statecount)
	{
		controlManager._SetStateCount(statecount);
	}
	//Stateを設定する
	final public void _SetState(int i,double refmax,int controlcount)
	{
		controlManager._SetControlCount(i,refmax,controlcount);
	}
	//Contolを設定する
	final public void _SetControl(int i,int u,int lmotorspeed,int rmotorspeed)
	{
		controlManager._SetControl(i,u,lmotorspeed,rmotorspeed);
	}
	final public Control GetControl(int i, int u)
	{
		return controlManager._GetControl(i, u);
	}
}
