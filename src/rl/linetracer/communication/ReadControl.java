package rl.linetracer.communication;

import rl.communication.message.MessageProcedure;
import rl.communication.message.context.MessageInputContext;
import rl.communication.message.context.MessageOutputContext;

// Controlを取得する
//<Control>::=
//	M(<SingleControl><endl>)
//		;(Controlの定義 StateIndexは0からN-1まで順に並ぶ(N=StateCount)
//		; ControlIndexは０からStateのControlCount-1まで順に並ぶ)
//
//<SingleControl>::=
//	<StateIndex><tab>;StateIndex
//	<ControlIndex><tab>;ControlIndex
//	<LMotorSpeed><tab>;LMotorSpeed
//	<RMotorSpeed>;RMotorSpeed
class ReadControl implements MessageProcedure
{
	

	int StateIndex;
	int ControlIndex;
	int LMotorSpeed;
	int RMotorSpeed;

	public void setStateIndex(int i)
	{
		StateIndex = i;
	}

	public void setControlIndex(int j)
	{
		ControlIndex = j;

	}

	@Override
	public void process(MessageInputContext input, MessageOutputContext output) throws Exception
	{
		// StateIndexの検証
		if (StateIndex != Integer.parseInt(input.nextToken()))
		{
			throw new Exception(this.getClass().getName());
		}
		// ControlIndexの検証
		if (ControlIndex != Integer.parseInt(input.nextToken()))
		{
			throw new Exception(this.getClass().getName());
		}
		// LMotorSpeedの取得
		LMotorSpeed = Integer.parseInt(input.nextToken());
		// LMotorSpeedの取得
		RMotorSpeed = Integer.parseInt(input.nextToken());
		// 改行
		input.skipReturn();
	}

}