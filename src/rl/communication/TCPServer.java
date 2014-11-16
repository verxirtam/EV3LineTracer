package rl.communication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.net.Socket;

import rl.communication.message.MessageProcedure;

public class TCPServer
{
	//TODO Serverとしての機能を実装する
	private final int Port=51124;
	private final String VersionString="MESSAGE_1.0";
	private MessageProcedure MessageProcedure;
	
	private void WriteSocketMessage(Socket socket,BufferedReader resultreader) throws IOException
	{
		try
		(
				//クライアントへの送信用出力ストリーム
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		)
		{
			
			//クライアントへresultを送信する
			//1行分の文字列
			String s;
			//1行ずつ読み取りoutに格納する
			while(null != (s = resultreader.readLine()))
			{
				//一行書き込み(改行以外)
				out.write(s);
				//改行書き込み
				out.newLine();
			}
			//バッファの内容を書き込む
			out.flush();
		}
	}
	private BufferedReader ReadSocketMessage(Socket socket) throws IOException
	{
		
		try
		(
			//メッセージを受信するためのストリーム
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				StringWriter messagebodystringwriter = new StringWriter();
			//メッセージ本体
			BufferedWriter messagebodywriter = new BufferedWriter(messagebodystringwriter);
		)
		{
			//一行目の読み込み
			String line = in.readLine();
			//バージョンの確認
			CheckVersion(line);
			//空行(\n\n)を受信するまで1行ずつmessagebodyに格納する
			ReadMassageBody(in, messagebodywriter);
			messagebodywriter.flush();
			
			return GetReader(messagebodystringwriter);
		}
	
	}
	public TCPServer()
	{
		//MessageProcedureの初期化
		MessageProcedure=new MessageProcedure();
	}
	//MessageProcedureを指定するコンストラクタ
	public TCPServer(MessageProcedure m)
	{
		//MessageProcedureの初期化
		MessageProcedure=m;
	}
	//サーバサービスの実行
	public void ServerRun()
	{
		//サーバ用のソケット
		ServerSocket serversocket = null;
		try {
			//ソケットの初期化
			serversocket = new ServerSocket(Port);
			//サーバの待ち受け状態が完了したことを標準出力に表示
			System.out.println("Ready to Transfer");
			//待ち受け開始
			for (;;)
			{
				try
				(
						//接続があれば接続ソケットを新規に作成
						Socket s = serversocket.accept( );
				)
				{
					//クライアントからのリクエストを処理
					run(s);
				}
			}
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			//serversocketの開放
			try
			{
				serversocket.close();
			}
			catch(Exception e){}
		}
	}
	public void run(Socket socket) throws IOException
	{

		
		try
		(
				//メッセージ実行結果(書き込み用バッファ)
				StringWriter resultstringwriter = new StringWriter();
				//メッセージ実行結果(書き込み用)
				BufferedWriter resultwriter = new BufferedWriter(resultstringwriter);
				//Socketからメッセージの読み込み
				BufferedReader messagebodyreader = ReadSocketMessage(socket);
		)
		{
			//resultにバージョンを記入
			WriteVersion(resultwriter);
			//MessageProcedureにmessagebodyreaderを渡しコマンド実行
			MessageProcedure.MessageProcess(messagebodyreader, resultwriter);
			//resultに空行を付与してメッセージを完成させる
			resultwriter.newLine();
			resultwriter.flush();
			
			try
			(
				//resultの読み取り用BufferedReaderの初期化
				BufferedReader resultreader = GetReader(resultstringwriter);
			)
			{
				//resultをクライアントへ送信する
				WriteSocketMessage(socket,resultreader);
			}
		}
		catch (IOException e)
		{
			System.out.println(e);
			throw e;
		}
		
	}

	//メッセージのバージョンの確認
	//不正な場合はIllegalArgumentException例外を発出
	public void CheckVersion(String line)
	{
		if(line.equals(VersionString))
		{
			return;
		}
		else
		{
			throw new IllegalArgumentException("MessageのVersionが異なります。");
		}
	}
	//空行(\n\n)を受信するまで1行ずつmessagebodyに格納する
	public void ReadMassageBody(BufferedReader in,BufferedWriter messagebody) throws IOException
	{
		//一行分のメッセージを格納するString
		String line;
		
		//一行読み取り（末尾に改行は入らない）
		line=in.readLine();
		//読み取り不可になるまで繰り返し
		while(line!=null)
		{
			//空行だった場合はループを抜ける
			if(line.equals(""))
			{
				break;
			}
			//一行をmessagebodyに格納
			messagebody.write(line);
			//改行書き込み
			messagebody.newLine();
			//一行読み取り（末尾に改行は入らない）
			line=in.readLine();
		}
	}
	//バージョンを書き込む
	public void WriteVersion(BufferedWriter result) throws IOException
	{
		result.write(VersionString);
		result.newLine();
	}
	//BufferedWriterの内容を元にBufferedReaderを初期化する
	public BufferedReader GetReader(StringWriter resultstringwriter)
			throws IOException
	{
		//writerの内容を書き出す
		resultstringwriter.flush();
		//writerの内容を元にreaderを初期化し返却する
		return new BufferedReader(new StringReader(resultstringwriter.toString()));
	}

}
