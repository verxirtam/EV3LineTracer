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
		new ReadInterval().process(input, output);
		// 改行
		input.skipReturn();

		// CostMaxの読み取り
		new ReadCostMax().process(input, output);
		// 改行
		input.skipReturn();

		// StateCountの読み取り
		ReadStateCount rsc = new ReadStateCount();
		rsc.process(input, output);
		// 改行
		input.skipReturn();

		// StateCountの取得
		int statecount = rsc.getStateCount();

		// StateIndex毎のcontrolcount格納用
		ArrayList<Integer> controlcount = new ArrayList<Integer>();

		// State読み取り用MessageProcedure
		ReadState rs = new ReadState();
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
		ReadControl rc = new ReadControl();
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
	}

}
