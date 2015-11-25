package rl.linetracer.communication;

import rl.communication.message.MessageProcedure;
import rl.communication.message.context.MessageInputContext;
import rl.communication.message.context.MessageOutputContext;
import rl.linetracer.MDPManagerRefmax;

// StateCountを取得する
//<StateCount>::=DIGIT
public class ReadStateCount implements MessageProcedure
{

	private MDPManagerRefmax mdpManagerRefmax;
	
	private int StateCount;


	public ReadStateCount(MDPManagerRefmax _mdpManagerRefmax)
	{
		mdpManagerRefmax = _mdpManagerRefmax;
	}

	@Override
	public void process(MessageInputContext input, MessageOutputContext output)
			throws Exception
	{

		// StateCountを取得
		StateCount = Integer.parseInt(input.nextToken());

		// StateCountの設定
		mdpManagerRefmax.setStateCount(StateCount);
		
	}

	public int getStateCount()
	{
		return StateCount;
	}
}