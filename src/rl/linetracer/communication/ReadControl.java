package rl.linetracer.communication;

import rl.communication.message.MessageProcedure;
import rl.communication.message.context.MessageInputContext;
import rl.communication.message.context.MessageOutputContext;
import rl.linetracer.ControlManagerNormal;

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

	private ControlManagerNormal controlManagerNormal;
	private int StateIndex;
	private int ControlIndex;

	public ReadControl(ControlManagerNormal _controlManagerNormal)
	{
		controlManagerNormal = _controlManagerNormal;
	}

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
		controlManagerNormal.setControl(StateIndex, ControlIndex, l_motor_speed, r_motor_speed);
	}

}