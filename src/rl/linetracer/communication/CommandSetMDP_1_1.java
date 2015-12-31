package rl.linetracer.communication;

import rl.communication.message.MessageProcedure;
import rl.communication.message.context.MessageInputContext;
import rl.communication.message.context.MessageOutputContext;
import rl.linetracer.EV3LineTracer;
import rl.linetracer.MDPManager;
import rl.linetracer.MDPManagerRefmax;

public class CommandSetMDP_1_1 implements MessageProcedure
{
	public static final String COMMAND_STRING = "SetMDP";
	public static final String RESULT_OK = "OK";
	@Override
	public void process(MessageInputContext input, MessageOutputContext output)
			throws Exception
	{
		
		//EV3の取得
		EV3LineTracer ev3 = EV3LineTracer.getInstance();
		
		//MDPManagerの取得
		MDPManager mdp_manager = getMDPManager(input);
		ev3.setMDPManager(mdp_manager);
		
		//出力の設定
		output.writeToken(COMMAND_STRING);
		output.newLine();
		output.writeToken(RESULT_OK);
		output.newLine();
		output.writeToken(mdp_manager.getManagerName());
		output.newLine();
		
		//ev3の設定の読み込み
		ev3.getReadMDPManager().process(input, output);
		
		//CurrentPolicyをRegularPolicyに設定する
		ev3.SetCurrentPolicy(ev3.GetRegularPolicy());
		
	}
	
	protected MDPManager getMDPManager(MessageInputContext input) throws Exception
	{
		//MDPManagerName文字列の取得
		String mdp_manager_name = input.nextToken();
		input.skipReturn();
		
		if(mdp_manager_name.equals(MDPManagerRefmax.MANAGER_NAME))
		{
			return new MDPManagerRefmax();
		}
		// どのコマンドにも当てはまらない場合は例外を投げる
		throw new Exception(this.getClass().getName());
	}

}
