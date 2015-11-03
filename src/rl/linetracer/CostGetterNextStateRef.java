/**
 * 
 */
package rl.linetracer;

import rl.Step;

/**
 * @author daisuke
 * 次のstateのindexが2より大きいかどうかで1ステップあたりのcostを変更する
 * ゴール時にはコストはかからない
 */
public class CostGetterNextStateRef extends CostGetter
{

	/**
	 * @param cost_max
	 */
	public CostGetterNextStateRef(double cost_max)
	{
		super(cost_max);
	}

	/* (non-Javadoc)
	 * @see rl.linetracer.CostGetter#getCostWhenGoal(rl.Step, double)
	 */
	@Override
	public double getCostWhenGoal(Step step, double elapsed_time)
	{
		return 0.0;
	}

	/* (non-Javadoc)
	 * @see rl.linetracer.CostGetter#getCostWhenRunning(rl.Step, double)
	 */
	@Override
	public double getCostWhenRunning(Step step, double elapsed_time)
	{
		return (step.State > 2) ? 1.0 : 0.125;
	}

}
