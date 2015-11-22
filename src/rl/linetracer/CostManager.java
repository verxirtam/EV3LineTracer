package rl.linetracer;

import rl.Step;

//costの算出を行うクラス
//step("次"のステップ)の値を読み取ってcostを算出し、返り値としてcostを返却する。
//状況に応じて4種のメソッドを利用する。
//getCostWhenGoal()		:ゴール時のcostを算出
//getCostWhenCourseOut():コースアウト時のcostを算出(定義済み)
//getCostWhenTimeOut()	:タイムアウト時のcostを算出(定義済み)
//getCostWhenRunning()	:実行時のcostを算出
public abstract class CostManager
{
	//costの最大値
	private double costMax;

	public CostManager(double cost_max)
	{
		setCostMax(cost_max);
	}
	
	public void setCostMax(double cost_max)
	{
		costMax = cost_max;
	}
	
	//ゴール時のcostを算出
	abstract public double getCostWhenGoal(Step step, double elapsed_time);

	//コースアウト時のcostを算出(定義済み)
	public double getCostWhenCourseOut(Step step, double elapsed_time)
	{
		return costMax;
	}
	
	//タイムアウト時のcostを算出(定義済み)
	public double getCostWhenTimeOut(Step step, double elapsed_time)
	{
		return costMax;
	}

	//実行時のcostを算出
	abstract public double getCostWhenRunning(Step step, double elapsed_time);

	public double _getCostMax()
	{
		return costMax;
	}



}
