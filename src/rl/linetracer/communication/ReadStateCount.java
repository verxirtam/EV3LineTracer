package rl.linetracer.communication;

import rl.communication.message.MessageProcedure;
import rl.communication.message.context.MessageInputContext;

// StateCountを取得する
//<StateCount>::=DIGIT
class ReadStateCount implements MessageProcedure
{

	private int StateCount;

	@Override
	public void process(MessageInputContext context) throws Exception
	{
		// StateCountを取得
		StateCount = Integer.parseInt(context.nextToken());
	}

	public int getStateCount()
	{
		return StateCount;
	}
}