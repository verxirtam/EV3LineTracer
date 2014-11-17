package rl.linetracer.communication;

import rl.communication.message.MessageProcedure;
import rl.communication.message.context.MessageInputContext;
import rl.communication.message.context.MessageOutputContext;

// StateCountを取得する
//<StateCount>::=DIGIT
class ReadStateCount implements MessageProcedure
{

	private int StateCount;

	@Override
	public void process(MessageInputContext input, MessageOutputContext output) throws Exception
	{
		// StateCountを取得
		StateCount = Integer.parseInt(input.nextToken());
	}

	public int getStateCount()
	{
		return StateCount;
	}
}