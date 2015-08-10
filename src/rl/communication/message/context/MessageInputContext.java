package rl.communication.message.context;

import java.io.IOException;

//改行単位でTokenを読み取る
//行末まで来たらskipReturn()で次の行に進む必要がある。
public interface MessageInputContext
{
	// 次のTokenを取得し現在のTokenを1つ進める
	String nextToken() throws Exception;

	// 次のTokenがskipStringであることを確認し現在のTokenを1つ進める
	void skipToken(String skipString) throws Exception;

	// 次のTokenが行末であることを確認し現在のTokenを次の行の初めに進める
	void skipReturn() throws Exception;

	// 現在の行で次のTokenがあるかどうかを確認する
	boolean hasNextToken() throws IOException;

}