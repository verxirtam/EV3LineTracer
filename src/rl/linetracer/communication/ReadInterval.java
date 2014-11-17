package rl.linetracer.communication;

import rl.communication.message.MessageProcedure;
import rl.communication.message.context.MessageInputContext;

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
	public void process(MessageInputContext context) throws Exception
	{
		// Intervalを取得
		Interval = Integer.parseInt(context.nextToken());

	}

}