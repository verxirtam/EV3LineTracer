package rl.linetracer.communication;

import rl.communication.message.MessageProcedure;
import rl.communication.message.context.MessageInputContext;
import rl.communication.message.context.MessageOutputContext;
import rl.linetracer.ControlManagerNormal;

// Intervalを取得する
//<Interval>::=DIGIT
class ReadInterval implements MessageProcedure
{
	private ControlManagerNormal controlManagerNormal;
	
	public ReadInterval(ControlManagerNormal _controlManagerNormal)
	{
		controlManagerNormal = _controlManagerNormal;
	}

	@Override
	public void process(MessageInputContext input, MessageOutputContext output) throws Exception
	{
		
		// Intervalを取得
		int interval = Integer.parseInt(input.nextToken());
		
		//Intervalを設定
		controlManagerNormal.setInterval(interval);
		
	}

}