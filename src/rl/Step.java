package rl;

public class Step
{
	public int State;
	public int Control;
	public double Cost;
	public Step(int state, int control, double cost)
	{
		State	=state;
		Control	=control;
		Cost	=cost;
	}
	public Step(Step s)
	{
		State	=s.State;
		Control	=s.Control;
		Cost	=s.Cost;
	}
	
}
