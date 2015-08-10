package rl.communication.message.context;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;

// タブ区切りのメッセージ
public class TSVInputContext implements MessageInputContext,Closeable
{
	//読み込み開始したかを表す
	//最初のnewLine()を行うかの判断に利用する
	boolean isBeginning;
	// メッセージを保持するBufferedReader
	BufferedReader MessageReader;
	// 現在の行をタブ区切りでTokenにわけて配列で保持したもの
	String[] CurrentLine;
	// CurrentLine上での現在位置
	int CurrentIndex;
	//読み込み開始のため最初の行を読み込む
	//読み込み開始済みなら何もしない
	private void begin() throws IOException
	{
		//読み込み前の場合は最初の行を読み込む
		if(isBeginning)
		{
			//最初の行を読み込む
			newLine();
			//isBeginningを読み込み開始済みに設定する
			isBeginning=false;
		}
	}
	// 現在行を次の行に移す
	// 行末に達しているかどうかのチェックは行わない
	private void newLine() throws IOException
	{
		// 次の行を取得
		String newline = MessageReader.readLine();
		// バッファの最後に達したか確認
		if (newline == null)
		{
			//バッファの最後に達した場合は空のToken列を設定する
			CurrentLine = new String[0];
			return;
		}
		// 取得した行をタブ区切りに分割
		CurrentLine = newline.split("\t");
		// 現在位置の初期化
		CurrentIndex = -1;
	}

	public TSVInputContext(BufferedReader br)
	{
		isBeginning = true;
		MessageReader = br;
	}

	// 次のTokenを取得し現在のTokenを1つ進める
	@Override
	public String nextToken() throws Exception
	{
		//読み込み前の場合は最初の行を読み込む
		begin();
		// 現在位置が行末か確認
		if (!hasNextToken())
		{
			// 行末なら例外発生
			String msg="";
			for(int i=0;i<CurrentLine.length;i++)
			{
				msg += CurrentLine[i] + "\n";
			}
			throw new Exception("error on nextToken() CurrentLine = \"" + msg + "\"");
		}
		// 現在位置を1つ進める
		CurrentIndex++;
		// 進めた位置でのTokenを返却
		return CurrentLine[CurrentIndex];
	}

	// 次のTokenがskipStringであることを確認し現在のTokenを1つ進める
	@Override
	public void skipToken(String skipString) throws Exception
	{
		//読み込み前の場合は最初の行を読み込む
		begin();
		// 次のTokenが無ければ例外発生
		if (!hasNextToken())
		{
			throw new Exception("error on skipToken():" + "end of line.");
		}
		// 次のTokenがskipStringと一致して入れば現在位置を進める
		if (CurrentLine[CurrentIndex + 1].equals(skipString))
		{
			CurrentIndex++;
		} else
		{
			// 一致していない場合は例外発生
			throw new Exception(
					"error on skipToken():NExtString is unmatch to "
							+ skipString);
		}

	}

	// 次のTokenが行末であることを確認し現在のTokenを次の行の初めに進める
	@Override
	public void skipReturn() throws Exception
	{
		//読み込み前の場合は最初の行を読み込む
		begin();
		// 現在行に次のTokenがある場合は例外発生
		if (hasNextToken())
		{
			throw new Exception("error on skipReturn():"
					+ "here is not end of line.");
		}
		// 次の行に進む
		newLine();
	}

	// 現在の行で次のTokenがあるかどうかを確認する
	@Override
	public boolean hasNextToken() throws IOException
	{
		//読み込み前の場合は最初の行を読み込む
		begin();
		// 現在行で次のTokenがあるかを返却する(Tokenがあるならtrue)
		return ( CurrentIndex < ( CurrentLine.length - 1 ) );
	}

	@Override
	public void close() throws IOException
	{
		MessageReader.close();
	}

}