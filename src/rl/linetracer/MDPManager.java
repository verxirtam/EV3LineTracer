package rl.linetracer;

import rl.Step;

abstract public class MDPManager
{
	protected StateManager stateManager;
	protected ControlManager controlManager;
	protected CostManager costManager;
	// 現在のStateとCostを取得する
	// argument:
	// Step step: 取得したStateを格納するStep
	// Step previousstep: 取得したCostを格納するStep
	final public void GetCurrentState(Step step, MachineControl MC)
	{
		stateManager.GetCurrentState(step, MC);
	}

	// 指定したControlに応じた行動を行う
	final public void DoControl(Step step, MachineControl MC,int interval)
	{
		controlManager.DoControl(step, MC, interval);
	}
	
	/////////////////////////////////////////
	
	//ゴール時のcostを算出
	final public double getCostWhenGoal(Step step, double elapsed_time)
	{
		return costManager.getCostWhenGoal(step, elapsed_time);
	}

	//コースアウト時のcostを算出
	final public double getCostWhenCourseOut(Step step, double elapsed_time)
	{
		return costManager.getCostWhenCourseOut(step, elapsed_time);
	}
	
	//タイムアウト時のcostを算出
	final public double getCostWhenTimeOut(Step step, double elapsed_time)
	{
		return costManager.getCostWhenTimeOut(step, elapsed_time);
	}

	//実行時のcostを算出
	final public double getCostWhenRunning(Step step, double elapsed_time)
	{
		return costManager.getCostWhenRunning(step, elapsed_time);
	}
	
	///////////////////////////////////////////////
	
	///////////////////////
	//移行期間限定のメソッド
	//メソッド名先頭に"_"をつけて区別する
	///////////////////////
	//StateCountを指定して、
	//Conrolの配列の領域を確保する
	final public void _SetStateCount(int statecount)
	{
		stateManager._SetStateCount(statecount);
		controlManager._SetStateCount(statecount);
	}
	//Stateを設定する
	final public void _SetState(int i,double refmax,int controlcount)
	{
		stateManager._SetState(i,refmax,controlcount);
		controlManager._SetControlCount(i,refmax,controlcount);
	}
	//Contolを設定する
	final public void _SetControl(int i,int u,int lmotorspeed,int rmotorspeed)
	{
		controlManager._SetControl(i,u,lmotorspeed,rmotorspeed);
	}
	final public Control _GetControl(int i, int u)
	{
		return controlManager._GetControl(i, u);
	}
	//ControlCountの取得
	final public int _GetControlCount(int i)
	{
		return stateManager._GetControlCount(i);
	}
	final public State _GetState(int i)
	{
		return stateManager._GetState(i);
	}
	//StateCountの取得
	final public int _GetStateCount()
	{
		return stateManager._GetStateCount();
	}
	final public void _setCostMax(double cost_max)
	{
		costManager.setCostMax(cost_max);
	}

}
