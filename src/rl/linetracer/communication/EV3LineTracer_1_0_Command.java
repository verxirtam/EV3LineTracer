package rl.linetracer.communication;

import rl.communication.message.MessageProcedure;
import rl.communication.message.context.MessageInputContext;

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
public class EV3LineTracer_1_0_Command implements MessageProcedure
{

	@Override
	public void process(MessageInputContext context) throws Exception
	{
		// コマンド文字列の取得
		String commandstring = context.nextToken();
		// 改行
		context.skipReturn();

		// コマンドの取得と実行
		createCommand(commandstring).process(context);

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