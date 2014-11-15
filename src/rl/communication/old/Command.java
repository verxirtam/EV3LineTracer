package rl.communication.old;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

//TODO インターフェースをクラスにしてコマンド実行時の例外処理を追加する
//TCPクライアントからのテキストメッセージを命令として解釈して
//EV3LineTracerに実行させる
public interface Command
{
	//命令を実行する
	public void DoCommand(BufferedReader message, BufferedWriter result) throws IOException;
}
