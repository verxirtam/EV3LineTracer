package rl.linetracer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;

import rl.Episode;
import rl.Step;
import rl.communication.Command;

//エピソードを実行するコマンド
public class CommandExecEpisode implements Command
{
	private EV3LineTracer EV3LineTracer;
	
	public CommandExecEpisode(EV3LineTracer ev3linetracer)
	{
		EV3LineTracer=ev3linetracer;
	}

	//エピソードを実行するコマンドを実行する
	//resultに結果のエピソードが格納される
	@Override
	public void DoCommand(BufferedReader message, BufferedWriter result)
			throws IOException
	{
		try
		{
			//取得するエピソード
			Episode e=new Episode();
			//エピソードの実行
			EV3LineTracer.ExecEpisode(e);
			//結果の出力
			//(失敗時は例外が起きているので以下の行が実行されているときは成功している)
			result.write("OK");
			result.newLine();
			//ステップ数の出力
			int stepcount=e.GetStepCount();
			result.write(""+stepcount);
			result.newLine();
			//エピソード内の各ステップの出力
			for(int m=0;m<stepcount;m++)
			{
				//ステップの取得
				Step s=e.GetStep(m);
				//ステップの出力
				result.write(""+m+"\t"+s.State+"\t"+s.Control+"\t"+s.Cost);
				result.newLine();
			}
		}
		catch(Exception e)
		{
			//失敗時は「NG」とともにstacktraceを出力する
			result.write("NG");
			result.newLine();
			e.printStackTrace(new PrintWriter(result));
			//標準出力にも出力する
			e.printStackTrace();
		}
	}

}
