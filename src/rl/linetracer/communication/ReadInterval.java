package rl.linetracer.communication;

import rl.communication.message.MessageProcedure;
import rl.communication.message.context.MessageInputContext;
import rl.communication.message.context.MessageOutputContext;
import rl.linetracer.MDPManagerRefmax;

// Intervalを取得する
//<Interval>::=DIGIT
public class ReadInterval implements MessageProcedure
{
	private MDPManagerRefmax mdpManagerRefmax;
	
	public ReadInterval(MDPManagerRefmax _mdpManagerRefmax)
	{
		mdpManagerRefmax = _mdpManagerRefmax;
	}

	@Override
	public void process(MessageInputContext input, MessageOutputContext output) throws Exception
	{
		
		// Intervalを取得
		int interval = Integer.parseInt(input.nextToken());
		
		//Intervalを設定
		mdpManagerRefmax.setInterval(interval);
		
	}

}