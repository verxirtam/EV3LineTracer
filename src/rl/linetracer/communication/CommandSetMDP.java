package rl.linetracer.communication;

import rl.communication.message.MessageProcedure;
import rl.communication.message.context.MessageInputContext;
import rl.communication.message.context.MessageOutputContext;
import rl.linetracer.EV3LineTracer;
import rl.linetracer.MDPManager;
import rl.linetracer.MDPManagerRefmax;

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
	
	private String versionString;
	
	public CommandSetMDP(String _versionString)
	{
		versionString = _versionString;
	}

	@Override
	public void process(MessageInputContext input, MessageOutputContext output)
			throws Exception
	{
		// 出力の設定
		output.writeToken(COMMAND_STRING);
		output.newLine();
		output.writeToken(RESULT_OK);
		output.newLine();
		
		//EV3の取得
		EV3LineTracer ev3 = EV3LineTracer.getInstance();
		
		MDPManager mdp_manager = getMDPManager();
		ev3.setMDPManager(mdp_manager);
		
		//ev3の設定の読み込み
		ev3.getReadMDPManager().process(input, output);
		
		//CurrentPolicyをRegularPolicyに設定する
		ev3.SetCurrentPolicy(ev3.GetRegularPolicy());
		
	}

	private MDPManager getMDPManager() throws Exception
	{
		//LineTracer_1.0の場合は
		//MDPManagerNameを読み込まずにMDPManagerRefmaxを返却する
		if(versionString.equals(EV3LineTracer_1_0_Command.VERSION_STRING))
		{
			return new MDPManagerRefmax();
		}
		//LineTracer_1.1の場合は
		//MDPManagerNameを読み込みこれに応じたMDPManagerを返却する
//		if(versionString.equals(EV3LineTracer_1_1_Command.VERSION_STRING))
//		{
//			//MDPManagerName文字列の取得
//			String mdp_manager_name = input.nextToken();
//			return getMDPManager(mdp_manager_name);
//		}

		// どのコマンドにも当てはまらない場合は例外を投げる
		throw new Exception(this.getClass().getName());
	}

}