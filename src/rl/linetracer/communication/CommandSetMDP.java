package rl.linetracer.communication;

import java.io.IOException;
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
		EV3LineTracer ev3 = EV3LineTracer.getInstance();
		
		//ev3の設定の読み込み
		ev3.getReadMDPManager().process(input, output);
		
		//CurrentPolicyをRegularPolicyに設定する
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