package rl.linetracer.communication;

import rl.communication.message.MessageProcedure;
import rl.communication.message.context.MessageInputContext;
import rl.communication.message.context.MessageOutputContext;

// Intervalを取得する
//<Interval>::=DIGIT
class ReadInterval implements MessageProcedure
{
	

	private int Interval;

	public int getInterval()
	{
		return Interval;
	}

	@Override
	public void process(MessageInputContext input, MessageOutputContext output) throws Exception
	{
		// Intervalを取得
		Interval = Integer.parseInt(input.nextToken());

	}

}