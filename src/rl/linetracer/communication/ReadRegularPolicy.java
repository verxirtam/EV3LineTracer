package rl.linetracer.communication;

import java.util.ArrayList;

import rl.communication.message.MessageProcedure;
import rl.communication.message.context.MessageContext;

//RegularPolicyの読み取り
//<RegularPolicy>::=
//	N(<SinglePolicy><endl>);RegularPolicyの定義(StateIndexは0からN-1まで順に並ぶ(N=StateCount))
//	
//<SinglePolicy>::=
//	<StateIndex><tab>;StateIndex
//	<ControlIndex>;ControlIndex
class ReadRegularPolicy implements MessageProcedure
{


	private int StateCount;
	private ArrayList<Integer> ControlCount;


	/**
	 * @param stateCount the stateCount to set
	 */
	public void setStateCount(int stateCount)
	{
		StateCount = stateCount;
	}

	/**
	 * @param controlCount the controlCount to set
	 */
	public void setControlCount(ArrayList<Integer> controlCount)
	{
		ControlCount = controlCount;
	}

	
	@Override
	public void process(MessageContext context) throws Exception
	{
		for (int i = 0; i < StateCount; i++)
		{
			if (i != Integer.parseInt(context.nextToken()))
			{
				throw new Exception(this.getClass().getName());
			}
			int j=Integer.parseInt(context.nextToken());
			if(j<0 || ControlCount.get(i)<=j)
			{
				throw new Exception(this.getClass().getName());
			}
			// (i,j)をRegularPolicyとしてセットする
			context.skipReturn();
		}
	}



}