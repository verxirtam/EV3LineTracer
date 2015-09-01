package rl.linetracer.communication;

import rl.StochasticPolicy;
import rl.communication.message.MessageProcedure;
import rl.communication.message.context.MessageInputContext;
import rl.communication.message.context.MessageOutputContext;
import rl.linetracer.EV3LineTracer;

public class CommandSetCurrentPolicy implements MessageProcedure
{

	public static final String COMMAND_STRING = "SetCurrentPolicy";
	public static final String RESULT_OK = "OK";

	@Override
	public void process(MessageInputContext input, MessageOutputContext output)
			throws Exception
	{
		//EV3を取得
		EV3LineTracer ev3 = EV3LineTracer.getInstance();
		//state数を取得
		int state_count = ev3.GetStateCount();
		//選択確率を格納する配列
		double[][] prob = new double[state_count][];
		//stateについて繰り返し
		for (int i = 0; i < state_count; i++)
		{
			//stateIndexを確認
			input.skipToken(""+i);
			//control数の取得
			int control_count = ev3.GetControlCount(i);
			//control数文の配列の取得
			prob[i] = new double[control_count];
			//controlについて繰り返し
			for (int u = 0; u < control_count; u++)
			{
				//選択確率の読み取り
				prob[i][u] = Double.parseDouble(input.nextToken());
			}
			//改行
			input.skipReturn();
		}
		//CurrentPolicyの設定
		ev3.SetCurrentPolicy(prob);
		
		//コマンド文字列の出力
		output.writeToken(COMMAND_STRING);
		output.newLine();
		//"OK"の出力
		output.writeToken(RESULT_OK);
		output.newLine();
		
		//currentPolicyの出力
		//EV3からcurrentPolicyの取得
		StochasticPolicy cp = ev3.GetCurrentPolicy();
		//stateについて繰り返し
		for (int i = 0; i < state_count; i++)
		{
			//stateIndexを書き込み
			output.writeToken(""+i);
			//control数の取得
			int control_count = ev3.GetControlCount(i);
			//controlについて繰り返し
			for (int u = 0; u < control_count; u++)
			{
				//選択確率の取得
				double prob_i_u = cp.At(i).GetProbability(u);
				//選択確率の出力
				output.writeToken("" + prob_i_u);
			}
			//改行
			output.newLine();
		}
		
	}

}
