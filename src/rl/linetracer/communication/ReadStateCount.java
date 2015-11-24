package rl.linetracer.communication;

import rl.communication.message.MessageProcedure;
import rl.communication.message.context.MessageInputContext;
import rl.communication.message.context.MessageOutputContext;
import rl.linetracer.ControlManagerNormal;
import rl.linetracer.StateManagerRefMax;

// StateCountを取得する
//<StateCount>::=DIGIT
public class ReadStateCount implements MessageProcedure
{

	private StateManagerRefMax stateManagerRefMax;
	private ControlManagerNormal controlManagerNormal;
	private int StateCount;

	public ReadStateCount(StateManagerRefMax _stateManagerRefMax, ControlManagerNormal _controlManagerNormal)
	{
		stateManagerRefMax = _stateManagerRefMax;
		controlManagerNormal = _controlManagerNormal;
	}

	@Override
	public void process(MessageInputContext input, MessageOutputContext output)
			throws Exception
	{

		// StateCountを取得
		StateCount = Integer.parseInt(input.nextToken());

		// StateCountの設定
		stateManagerRefMax.setStateCount(StateCount);
		controlManagerNormal.setStateCount(StateCount);
	}

	public int getStateCount()
	{
		return StateCount;
	}
}