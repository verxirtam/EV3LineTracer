package rl.linetracer.communication;

import rl.communication.message.MessageProcedure;
import rl.communication.message.context.MessageInputContext;
import rl.communication.message.context.MessageOutputContext;
import rl.linetracer.EV3LineTracer;

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

	public void setStateIndex(int i)
	{
		StateIndex = i;
	}

	public void setControlIndex(int j)
	{
		ControlIndex = j;

	}

	@Override
	public void process(MessageInputContext input, MessageOutputContext output)
			throws Exception
	{
		// EV3LineTracer(Singleton)を取得
		EV3LineTracer ev3 = EV3LineTracer.getInstance();

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
		int l_motor_speed = Integer.parseInt(input.nextToken());
		// LMotorSpeedの取得
		int r_motor_speed = Integer.parseInt(input.nextToken());
		// 改行
		input.skipReturn();

		// Controlの設定
		ev3.SetControl(StateIndex, ControlIndex, l_motor_speed, r_motor_speed);
	}

}