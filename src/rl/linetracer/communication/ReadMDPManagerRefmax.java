package rl.linetracer.communication;

import java.util.ArrayList;

import rl.communication.message.MessageProcedure;
import rl.communication.message.context.MessageInputContext;
import rl.communication.message.context.MessageOutputContext;
import rl.linetracer.ControlManagerNormal;
import rl.linetracer.CostManagerNextStateRef;
import rl.linetracer.StateManagerRefMax;

public class ReadMDPManagerRefmax implements MessageProcedure
{
	StateManagerRefMax stateManagerRefMax;
	ControlManagerNormal controlManagerNormal;
	CostManagerNextStateRef costManagerNextStateRef;

	public ReadMDPManagerRefmax(StateManagerRefMax _stateManagerRefMax,
			ControlManagerNormal _controlManagerNormal,
			CostManagerNextStateRef _costManagerNextStateRef)
	{
		stateManagerRefMax = _stateManagerRefMax;
		controlManagerNormal = _controlManagerNormal;
		costManagerNextStateRef = _costManagerNextStateRef;
	}

	@Override
	public void process(MessageInputContext input, MessageOutputContext output)
			throws Exception
	{
		// インターバルの読み取り
		new ReadInterval(controlManagerNormal).process(input, output);
		// 改行
		input.skipReturn();

		// CostMaxの読み取り
		new ReadCostMax(costManagerNextStateRef).process(input, output);
		// 改行
		input.skipReturn();

		// StateCountの読み取り
		ReadStateCount rsc = new ReadStateCount(stateManagerRefMax,controlManagerNormal);
		rsc.process(input, output);
		// 改行
		input.skipReturn();

		// StateCountの取得
		int statecount = rsc.getStateCount();//TODO ev3に依存しないように書き換える

		// StateIndex毎のcontrolcount格納用
		ArrayList<Integer> controlcount = new ArrayList<Integer>();

		// State読み取り用MessageProcedure
		ReadState rs = new ReadState();//TODO ev3に依存しないように書き換える
		// 読み取ったStateCountの分だけ繰り返すループ
		for (int i = 0; i < statecount; i++)
		{
			// rsに読み取るStateIndexを設定
			rs.setStateIndex(i);
			// Stateの読み取り
			rs.process(input, output);
			// 取得したControlCountを保持
			controlcount.add(rs.getControlCount());
		}
		// Control読み取り用MessageProcedure
		ReadControl rc = new ReadControl();//TODO ev3に依存しないように書き換える
		for (int i = 0; i < statecount; i++)
		{
			// rsに読み取るStateIndexを設定
			rc.setStateIndex(i);
			for (int j = 0; j < controlcount.get(i); j++)
			{
				// rsに読み取るControlIndexを設定
				rc.setControlIndex(j);
				// Controlの読み取り
				rc.process(input, output);
			}
		}
		// RegularPolicyの読み取り
		ReadRegularPolicy rrp = new ReadRegularPolicy();//TODO ここは書き換え不要
		rrp.setStateCount(statecount);
		rrp.setControlCount(controlcount);
		rrp.process(input, output);
	}

}
