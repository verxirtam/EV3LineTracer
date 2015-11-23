package rl.linetracer.communication;

import rl.communication.message.MessageProcedure;
import rl.communication.message.context.MessageInputContext;
import rl.communication.message.context.MessageOutputContext;
import rl.linetracer.CostManagerNextStateRef;

public class ReadCostMax implements MessageProcedure
{

	CostManagerNextStateRef costManagerNextStateRef;
	
	public ReadCostMax(CostManagerNextStateRef _costManagerNextStateRef)
	{
		costManagerNextStateRef = _costManagerNextStateRef;
	}

	@Override
	public void process(MessageInputContext input, MessageOutputContext output)
			throws Exception
	{
		
		// CostMaxを取得
		double cost_max = Double.parseDouble(input.nextToken());
		
		//CostMaxを設定
		costManagerNextStateRef.setCostMax(cost_max);

	}

}
