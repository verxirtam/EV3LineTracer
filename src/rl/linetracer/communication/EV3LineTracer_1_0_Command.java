package rl.linetracer.communication;

import rl.communication.message.MessageProcedure;
import rl.communication.message.context.MessageContext;
import rl.linetracer.EV3LineTracer;

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
public class EV3LineTracer_1_0_Command extends MessageProcedure_EV3LineTracer_1_0
{

	public EV3LineTracer_1_0_Command(EV3LineTracer ev3)
	{
		super(ev3);
	}

	@Override
	public void process(MessageContext context) throws Exception
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
			return new CommandSetMDPBody(this.getEV3LineTracer());
		}
		// ExecEpisode
		if (commandstring.equals("ExecEpisode"))
		{
			return new CommandExecEpisode(this.getEV3LineTracer());
		}
		// どのコマンドにも当てはまらない場合は例外を投げる
		throw new Exception(this.getClass().getName());
	}
}