package rl.communication.old;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import rl.linetracer.EV3LineTracer;
import rl.linetracer.communication.old.CommandExecEpisode;
import rl.linetracer.communication.old.CommandSetMDP;
import rl.linetracer.communication.old.CommandSetPolicy;

public class MessageProcedure
{
	private EV3LineTracer EV3LineTracer;
	public MessageProcedure()
	{
		EV3LineTracer=new EV3LineTracer();
	}
	//MessageProcess
	//サーバから受け取ったメッセージをCommand部,Body部として解釈し、
	//対応するコマンドオブジェクトを生成してコマンドを実行する。
	public void MessageProcess(BufferedReader messagebody, BufferedWriter result)throws IOException
	{
		//Status-Lineのチェック
		if(!messagebody.readLine().equals("EV3LineTracer_1.0"))
		{
			throw new IllegalArgumentException("Status line is wrong.");
		}
		//Command部の読み取り
		//Command部からGetCommand()でコマンドを作成
		Command c=GetCommand(messagebody);
		//DoCommand()にBody部を渡してresultを得る
		c.DoCommand(messagebody, result);
	}
	public Command GetCommand(BufferedReader messagebody) throws IOException
	{
		//コマンド部(messagebodyの1行目)を解釈して対応するコマンドを返却する
		//1行目を読み取り
		String s=messagebody.readLine();
		//コマンド部の内容に応じたコマンドオブジェクトを返却する
		if(s.equals("SetMDP"))
		{
			return new CommandSetMDP(EV3LineTracer);
		}
		if(s.equals("SetPolicy"))
		{
			return new CommandSetPolicy(EV3LineTracer);
		}
		if(s.equals("ExecEpisode"))
		{
			return new CommandExecEpisode(EV3LineTracer);
		}
		//コマンド部に対応するコマンドが無い場合は例外発生
		throw new IllegalArgumentException("Invalid command section.");
	}
}
