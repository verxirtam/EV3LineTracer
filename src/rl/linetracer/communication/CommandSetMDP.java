package rl.linetracer.communication;

import java.io.IOException;
import java.util.ArrayList;

import rl.RandomInt;
import rl.StochasticPolicy;
import rl.communication.message.MessageProcedure;
import rl.communication.message.context.MessageInputContext;
import rl.communication.message.context.MessageOutputContext;
import rl.linetracer.Control;
import rl.linetracer.EV3LineTracer;
import rl.linetracer.State;

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
public class CommandSetMDP implements MessageProcedure
{
	public static final String COMMAND_STRING = "SetMDP";
	public static final String RESULT_OK = "OK";
	
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
		// RegularPolicyの読み取り
		ReadRegularPolicy rrp = new ReadRegularPolicy();
		rrp.setStateCount(statecount);
		rrp.setControlCount(controlcount);
		rrp.process(input, output);
		
		//CurrentPolicyをRegularPolicyに設定する
		EV3LineTracer ev3 = EV3LineTracer.getInstance();
		ev3.SetCurrentPolicy(ev3.GetRegularPolicy());
		
		// 出力の設定
		output.writeToken(COMMAND_STRING);
		output.newLine();
		output.writeToken(RESULT_OK);
		output.newLine();
		//TODO 設定した内容を返信する
		output.writeToken(String.valueOf(ev3.GetInterval()));
		output.newLine();
		output.writeToken(String.valueOf(ev3.GetCostMax()));
		output.newLine();
		writeState(output, ev3);
		writeControl(output, ev3);
		writeRegularPolicy(output, ev3);
	}

	private void writeRegularPolicy(MessageOutputContext output,
			EV3LineTracer ev3) throws IOException
	{
		int state_count = ev3.GetStateCount();
		StochasticPolicy regular_policy = ev3.GetRegularPolicy();
		for(int i = 0; i < state_count; i++)
		{
			RandomInt pi = regular_policy.At(i);
			int jmax = pi.GetValueMax();
			
			
			//Probabilityが最大になるようなインデックスjを算出する
			double probmax = 0.0;
			int probmaxindex = 0;
			
			for(int j=0; j < jmax; j++)
			{
				double prob = pi.GetProbability(j);
				if(probmax < prob)
				{
					probmax = prob;
					probmaxindex = j;
				}
			}
			output.writeToken(String.valueOf(i));
			output.writeToken(String.valueOf(probmaxindex));
			output.newLine();
		}
	}

	private void writeControl(MessageOutputContext output, EV3LineTracer ev3) throws IOException
	{
		int state_count = ev3.GetStateCount();
		for(int i = 0; i < state_count; i++)
		{
			int control_count = ev3.GetControlCount(i);
			for(int u = 0; u < control_count; u++)
			{
				Control control = ev3.GetControl(i, u);
				output.writeToken(String.valueOf(i));
				output.writeToken(String.valueOf(u));
				output.writeToken(String.valueOf(control.LMotorSpeed));
				output.writeToken(String.valueOf(control.RMotorSpeed));
				output.newLine();
			}
		}
	}

	private void writeState(MessageOutputContext output, EV3LineTracer ev3) throws IOException
	{
		int state_count = ev3.GetStateCount();
		output.writeToken(String.valueOf(state_count));
		output.newLine();
		for(int i = 0; i < state_count; i++)
		{
			State state = ev3.GetState(i);
			output.writeToken(String.valueOf(i));
			output.writeToken(String.valueOf(state.RefMax));
			output.writeToken(String.valueOf(state.ControlCount));
			output.newLine();
		}
	}

}