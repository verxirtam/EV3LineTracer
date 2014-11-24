package rl.linetracer.communication;

import rl.communication.message.MessageProcedure;
import rl.communication.message.context.MessageInputContext;
import rl.communication.message.context.MessageOutputContext;

// コマンド
// <Command>::=
// (
// "SetMDP"<endl>;MDPを設定
// <SetMDPBody><endl>;コマンドの内容
// |
// "SetPolicy"<endl>;MDPを設定
// <SetPolicyBody><endl>;コマンドの内容
// |
// "ExecEpisode"<endl>;Episodeを実行
// )
//出力用
//===================================
//
public class EV3LineTracer_1_0_Command implements MessageProcedure
{
	public static String RESULT_OK="OK";
	public static String RESULT_NG="NG";
	
	@Override
	public void process(MessageInputContext input, MessageOutputContext output)
			throws Exception
	{
		// コマンド文字列の取得
		String commandstring = input.nextToken();
		// 改行
		input.skipReturn();

		// コマンドの取得と実行
		createCommand(commandstring).process(input, output);

	}

	// コマンド文字列に応じたコマンドの生成
	MessageProcedure createCommand(String commandstring) throws Exception
	{
		// SetMDP
		if (commandstring.equals("SetMDP"))
		{
			return new CommandSetMDPBody();
		}
		// ExecEpisode
		if (commandstring.equals("ExecEpisode"))
		{
			return new CommandExecEpisode();
		}
		// どのコマンドにも当てはまらない場合は例外を投げる
		throw new Exception(this.getClass().getName());
	}
}