package rl.communication.message.context;

//改行単位でTokenを読み取る
//行末まで来たらskipReturn()で次の行に進む必要がある。
public interface MessageContext
{
	// 次のTokenを取得し現在のTokenを1つ進める
	String nextToken() throws Exception;

	// 次のTokenがskipStringであることを確認し現在のTokenを1つ進める
	void skipToken(String skipString) throws Exception;

	// 次のTokenが行末であることを確認し現在のTokenを次の行の初めに進める
	void skipReturn() throws Exception;

	// 現在の行で次のTokenがあるかどうかを確認する
	boolean hasNextToken();

	// 呼び出し元オブジェクトを取得する
	Object getTarget();
}