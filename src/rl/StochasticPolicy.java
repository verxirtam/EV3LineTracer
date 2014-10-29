package rl;

//確率的ポリシー
public class StochasticPolicy
{
	//状態数
	private int StateCount;
	//Control(RandomIntの列)
	private RandomInt[] Control;
	//コンストラクタ
	public StochasticPolicy(double[][] prob)
	{
		StateCount=prob.length;
		Control=new RandomInt[StateCount];
		for(int i=0;i<StateCount;i++)
		{
			Control[i]=new RandomInt(prob[i],16);
		}
		Correct();
	}
	//整合性チェック
	public void Correct()
	{
		if(Control[0].GetValueMax()!=1)
		{
			throw new IllegalStateException("Control[0].GetValueMax()!=1");
		}
	}
	//i番めのControlへの参照
	public RandomInt At(int i)
	{
		return Control[i];
	}
	//ControlCount(RandomIdxのValueMax)を返す
	public int GetControlCount(int i)
	{
		return Control[i].GetValueMax();
	}
}
