package rl.linetracer.communication;

import java.util.ArrayList;

import rl.communication.message.MessageProcedure;
import rl.communication.message.context.MessageContext;

// コマンドSetMDP
//<SetMDPBody>::=
//	<Interval><endl>;Interval(msec)
//	<StateCount><endl>;State数
//	<State>;Stateの定義
//	<Control>;Controlの定義(M=全てのStateの全てのControl数の合計)
//	<RegularPolicy>;RegularPolicyの定義
class CommandSetMDPBody implements MessageProcedure
{

	@Override
	public void process(MessageContext context) throws Exception
	{

		// インターバルの読み取り
		new ReadInterval().process(context);
		// 改行
		context.skipReturn();

		// StateCountの読み取り
		ReadStateCount rsc = new ReadStateCount();
		rsc.process(context);
		// 改行
		context.skipReturn();

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
			rs.process(context);
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
				rc.process(context);
			}
		}
		// RegularPolicyの読み取り
		ReadRegularPolicy rrp = new ReadRegularPolicy();
		rrp.setStateCount(statecount);
		rrp.setControlCount(controlcount);
		rrp.process(context);
	}
}