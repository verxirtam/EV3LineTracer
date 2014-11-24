package rl.linetracer.communication;

import java.util.ArrayList;

import rl.communication.message.MessageProcedure;
import rl.communication.message.context.MessageInputContext;
import rl.communication.message.context.MessageOutputContext;

// コマンドSetMDP
//<SetMDPBody>::=
//	<Interval><endl>;Interval(msec)
//	<StateCount><endl>;State数
//	<State>;Stateの定義
//	<Control>;Controlの定義(M=全てのStateの全てのControl数の合計)
//	<RegularPolicy>;RegularPolicyの定義
//出力用
//===================================
//<Result>::="OK"|"NG"
// TODO NGのケースを記述する
class CommandSetMDPBody implements MessageProcedure
{

	@Override
	public void process(MessageInputContext input, MessageOutputContext output) throws Exception
	{

		// インターバルの読み取り
		new ReadInterval().process(input,output);
		// 改行
		input.skipReturn();

		// StateCountの読み取り
		ReadStateCount rsc = new ReadStateCount();
		rsc.process(input,output);
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
			rs.process(input,output);
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
				rc.process(input,output);
			}
		}
		// RegularPolicyの読み取り
		ReadRegularPolicy rrp = new ReadRegularPolicy();
		rrp.setStateCount(statecount);
		rrp.setControlCount(controlcount);
		rrp.process(input,output);
		
		//出力の設定
		output.writeToken(EV3LineTracer_1_0_Command.RESULT_OK);
		output.newLine();
	}
}