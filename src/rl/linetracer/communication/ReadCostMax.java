package rl.linetracer.communication;

import rl.communication.message.MessageProcedure;
import rl.communication.message.context.MessageInputContext;
import rl.communication.message.context.MessageOutputContext;
import rl.linetracer.MDPManagerRefmax;

public class ReadCostMax implements MessageProcedure
{

	private MDPManagerRefmax mdpManagerRefmax;
	
	public ReadCostMax(MDPManagerRefmax _mdpManagerRefmax)
	{
		mdpManagerRefmax = _mdpManagerRefmax;
	}

	@Override
	public void process(MessageInputContext input, MessageOutputContext output)
			throws Exception
	{
		
		// CostMaxを取得
		double cost_max = Double.parseDouble(input.nextToken());
		
		//CostMaxを設定
		mdpManagerRefmax.setCostMax(cost_max);

	}

}
