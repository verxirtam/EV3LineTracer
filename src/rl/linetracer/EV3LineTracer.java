package rl.linetracer;

import rl.*;
import rl.communication.TCPServer;

//TODO TEST
public class EV3LineTracer
{
	//////////////////////////////////////////
	public static void main(String args[])
	{
		TCPServer ts = new TCPServer();
		
		ts.ServerRun();
		
	}
	///////////////////////////////////////////
	
	//EV3LineTracerの唯一のインスタンス
	private static EV3LineTracer ev3 = new EV3LineTracer();
	///////////////////////////////////////////
	///////////////////////////////////////////
	private int Interval=10;
	private final int RegularInterval=10;
	private MachineControl MC;
	private State[] State;
	private Control[][] Control;
	private double CostMax;
	private StochasticPolicy RegularPolicy;
	private StochasticPolicy CurrentPolicy;
	private boolean IsReady;
	private long StartTime;
	
	//唯一のコンストラクタ
	//Singletonにするため、privateにしている
	private EV3LineTracer()
	{
		MC=new MachineControl();
		State=null;
		Control=null;
		IsReady=false;
	}
	
	//EV3LineTracerのインスタンスの取得
	//このメソッドが最初に実行される際にインスタンスが生成される
	public static EV3LineTracer getInstance()
	{
		return ev3;
	}
	
	
	public void TestInit()
	{
		//状態数
		int statecount=11;
		//Control数
		int controlcount=11;
		
		//Stateの配列の宣言
		State=new State[statecount];
		//State[0]の定義
		State[0]=new State();
		State[0].RefMax=0.0;
		State[0].ControlCount=1;
		for(int i=1;i<State.length;i++)
		{
			//State[i]の定義
			State[i]=new State();
			State[i].RefMax=((double)(i))/((double)(statecount-1));
			State[i].ControlCount=controlcount;
		}
		
		//Controlの配列の配列の宣言
		Control=new Control[State.length][];
		//Control[0][]の定義
		Control[0]=new Control[1];
		Control[0][0]=new Control();
		Control[0][0].LMotorSpeed=0;
		Control[0][0].RMotorSpeed=0;
		//Control[i][]の定義
		for(int i=1;i<State.length;i++)
		{
			Control[i]=new Control[State[i].ControlCount];
			for(int u=0;u<State[i].ControlCount;u++)
			{
				Control[i][u]=new Control();
			}
			Control[i][ 0].RMotorSpeed= 300;Control[i][ 0].LMotorSpeed=-200;
			Control[i][ 1].RMotorSpeed= 300;Control[i][ 1].LMotorSpeed=-100;
			Control[i][ 2].RMotorSpeed= 200;Control[i][ 2].LMotorSpeed=-100;
			Control[i][ 3].RMotorSpeed= 200;Control[i][ 3].LMotorSpeed=   0;
			Control[i][ 4].RMotorSpeed= 100;Control[i][ 4].LMotorSpeed=   0;
			Control[i][ 5].RMotorSpeed= 100;Control[i][ 5].LMotorSpeed= 100;
			Control[i][ 6].RMotorSpeed=   0;Control[i][ 6].LMotorSpeed= 100;
			Control[i][ 7].RMotorSpeed=   0;Control[i][ 7].LMotorSpeed= 200;
			Control[i][ 8].RMotorSpeed=-100;Control[i][ 8].LMotorSpeed= 200;
			Control[i][ 9].RMotorSpeed=-100;Control[i][ 9].LMotorSpeed= 300;
			Control[i][10].RMotorSpeed=-200;Control[i][10].LMotorSpeed= 300;
		}
		//CostMaxの定義
		CostMax=1000;
		
		//RegularPolicyの定義
		double[][] prob=new double[State.length][];
		prob[0]=new double[1];
		prob[0][0]=1.0;
		for(int i=1;i<State.length;i++)
		{
			prob[i]=new double[controlcount];
			for(int u=0;u<prob[i].length;u++)
			{
				prob[i][u]=0.0;
			}
		}
		prob[ 1][7]=1.0;
		prob[ 2][7]=1.0;
		prob[ 3][5]=1.0;
		prob[ 4][3]=1.0;
		prob[ 5][3]=1.0;
		prob[ 6][3]=1.0;
		prob[ 7][3]=1.0;
		prob[ 8][3]=1.0;
		prob[ 9][3]=1.0;
		prob[10][3]=1.0;
		RegularPolicy=new StochasticPolicy(prob);
		
		for(int i=1;i<State.length;i++)
		{
			for(int u=0;u<prob[i].length;u++)
			{
				prob[i][u]=0.05;
			}
		}
		prob[ 1][7]=1.0;
		prob[ 2][7]=1.0;
		prob[ 3][5]=1.0;
		prob[ 4][3]=1.0;
		prob[ 5][3]=1.0;
		prob[ 6][3]=1.0;
		prob[ 7][3]=1.0;
		prob[ 8][3]=1.0;
		prob[ 9][3]=1.0;
		prob[10][3]=1.0;
		CurrentPolicy=new StochasticPolicy(prob);
	}
	
	public void DoTest()
	{
		TestInit();
		
		Episode episode=new Episode();
		
		while(MC.IsEscapeButtonDown()!=false)
		{
			ExecEpisode(episode);
			
			System.out.println("--------------------StepCount:"
					+episode.GetStepCount());
			for(int i=0;i<episode.GetStepCount();i++)
			{
				Step s=episode.GetStep(i);
				System.out.println("--------------------"+i+":"
						+s.State+","
						+s.Control+","
						+s.Cost);
			}
		}
	}
	//Intervalを設定する
	public void SetInterval(int t)
	{
		if(t<0)
		{
			throw new IllegalArgumentException();
		}
		Interval=t;
	}
	//StateCountを指定して、
	//StateとConrolの配列の領域を確保する
	public void SetStateCount(int statecount)
	{
		if(statecount<=0)
		{
			throw new IllegalArgumentException();
		}
		State=new State[statecount];
		//Controlの配列の配列の宣言
		Control=new Control[State.length][];
	}
	//Stateを設定する
	public void SetState(int i,double refmax,int controlcount)
	{
		if(
				(i<0)||
				(i>=State.length)||
				(refmax<0.0)||
				(refmax>1.0)||
				(controlcount<0)
			)
		{
			throw new IllegalArgumentException();
		}
		State[i]=new State();
		State[i].RefMax=refmax;
		State[i].ControlCount=controlcount;
		Control[i]=new Control[State[i].ControlCount];
	}
	//StateCountの取得
	public int GetStateCount()
	{
		return State.length;
	}
	//ControlCountの取得
	public int GetControlCount(int i)
	{
		return State[i].ControlCount;
	}
	//Contolを設定する
	public void SetControl(int i,int u,int lmotorspeed,int rmotorspeed)
	{
		if(
				(i<0)||
				(i>=Control.length)||
				(u<0)||
				(u>=Control[i].length)
			)
		{
			throw new IllegalArgumentException();
		}
		Control[i][u]=new Control();
		Control[i][u].LMotorSpeed=lmotorspeed;
		Control[i][u].RMotorSpeed=rmotorspeed;
	}
	//RegularPolicyの設定
	public void SetRegularPolicy(double[][] prob)
	{
		RegularPolicy=new StochasticPolicy(prob);
	}
	//CurrentPolicyの設定
	public void SetCurrentPolicy(double[][] prob)
	{
		CurrentPolicy=new StochasticPolicy(prob);
	}

	//経過時間を取得する
	public double GetElapsedTime()
	{
		double NowTime=java.lang.System.currentTimeMillis();
		return (double)(NowTime-StartTime)/1000.0;
	}
	//現在のStateとCostを取得する
	//argument:
	//		Step step:	取得したStateを格納するStep
	//		Step previousstep:	取得したCostを格納するStep
	public void GetStateAndCost(Step step,Step previousstep)
	{
		
		switch(MC.GetColor())
		{
		//ゴールした場合
		case Color.RED:
			step.State=0;
			previousstep.Cost=GetElapsedTime();
			break;
		//コースアウトした場合
		case Color.BLUE:
			step.State=0;
			previousstep.Cost=CostMax;
			break;
		//上記以外(コースを進行中の場合)
		default:
			if(GetElapsedTime()>=CostMax)
			{
				//CostMax秒以上経過した場合
				//タイムアウトとして終了
				step.State=0;
				previousstep.Cost=CostMax;
			}
			//CostMax秒に達していない場合
			//継続してEpisodeを進める
			double r=MC.GetReflection();
			//測定値に対応するStateを定める
			for(int i=1;i<State.length;i++)
			{
				if(r<State[i].RefMax)
				{
					step.State=i;
					break;
				}
			}
			previousstep.Cost=0.0;
			break;
		}
	}
	//コースに復帰する
	public void ReturnCource()
	{
		int lspeed=  0;
		int rspeed=100;
		int c=0;
		while((!MC.OnBlackLine())&&(MC.GetColor()!=Color.RED))
		{
			MC.GoBack(lspeed,rspeed);
			MC.Delay(RegularInterval);
			c++;
			c%=10;
			if((c==0)&&(lspeed<rspeed))
			{
				lspeed++;
			}
		}
		//EV3を停止させる
		MC.Stop();
	}
	
	//State、CurrentPolicyに応じたControlを取得する
	public void GetControl(Step step)
	{
		//Controlを定める
		step.Control=CurrentPolicy.At(step.State).Get();
	}
	//指定したControlに応じた行動を行う
	public void DoControl(Step step)
	{
		//Controlに対応するモータの速度を取得
		int lspeed=Control[step.State][step.Control].LMotorSpeed;
		int rspeed=Control[step.State][step.Control].RMotorSpeed;
		//取得したモータの速度で進む
		MC.GoForward(lspeed, rspeed);
		//Interval(msec)だけこの状態を維持
		MC.Delay(Interval);
	}
	//Episodeを実行する準備を行う
	//コースアウトしていたら復帰し、
	public void Ready()
	{
		//既に準備完了なら終了
		if(IsReady)
		{
			return;
		}
		
		//コースアウトしていたらコースに復帰する
		//ReturnCource();
		
		//スタートラインをわずかに超えていた場合に備え、
		//少し後退しておく
		System.out.println("--------------------GoBack().");//TODO
		MC.GoBack(50, 50);
		MC.Delay(500);
		
		//CurrentPolicyをoldpolicyに退避しておく
		StochasticPolicy oldpolicy=CurrentPolicy;
		//CurrentPolicyをRegularPolicyに設定する
		CurrentPolicy=RegularPolicy;
		Step step=new Step(0,0,0.0);
		//EV3がスタートラインに達するまでループ
		while(MC.GetColor()!=Color.RED)
		{
			if(MC.GetColor()==Color.BLUE)
			{
				//コースアウトしている場合
				//コースに復帰する
				ReturnCource();
			}
			//ポリシーに従って走行する
			GetStateAndCost(step,step);
			GetControl(step);
			DoControl(step);
		}
		System.out.println("--------------------On Red Line.");//TODO
		//EV3がスタートラインを超えるまで前進する
		//whitecountmax回連続で白を検出したら超えたと判断する
		int whitecounter=0;
		int whitecountmax=10;
		while(whitecounter!=whitecountmax)
		{
			switch(MC.GetColor())
			{
			case Color.WHITE:
				//カウンタを1つ増やす
				whitecounter++;
				break;
			default:
				//カウンタをリセットする
				whitecounter=0;
				break;
			}
			
			MC.GoForward(50, 50);
			MC.Delay(10);
		}
		System.out.println("--------------------Over Red Line.");//TODO
		//EV3を停止させる
		MC.Stop();
		//CurrentPolicyを退避していたものに戻す
		CurrentPolicy=oldpolicy;
		//準備完了状態にする
		IsReady=true;
	}
	//Episodeを開始する
	private void StartEpisode()
	{
		StartTime=System.currentTimeMillis();
		IsReady=false;
		System.out.println("--------------------Episode Start.");//TODO
	}
	
	
	//Episodeを実行する
	public void ExecEpisode(Episode e)
	{
		//Episodeを初期化する
		e.ClearEpisode();
		
		System.out.println("--------------------Ready() Start..");//TODO
		MC.OnLED(MachineControl.LED_PATTERN_STATIC_RED);//TODO
		//スタートラインに着く
		Ready();
		
		System.out.println("--------------------Ready() End.");//TODO
		
		//Episodeを開始する
		StartEpisode();
		MC.OnLED(MachineControl.LED_PATTERN_STATIC_GREEN);//TODO
		
		//次のStep
		Step nextstep=new Step(0,0,0.0);
		//現在のStep
		Step currentstep=new Step(0,0,0.0);
		
		//現在の状態を取得
		GetStateAndCost(currentstep,new Step(0,0,0.0));
		while(true)
		{
			//StateとCurrentPolicyに応じたControlを取得
			GetControl(currentstep);
			//Controlに応じた行動を実行
			DoControl(currentstep);
			//次のStateと行動の結果掛かったCostを取得
			GetStateAndCost(nextstep,currentstep);
			//現在のStepをEpisodeに追加
			e.AddStep(currentstep);
			//TerminateStateに達したらループを抜けて終了
			if(nextstep.State==0)
			{
				nextstep.Control=0;
				nextstep.Cost=0.0;
				e.AddStep(nextstep);
				break;
			}
			
			//nextstepとcurrentstepを入れ替える
			Step dummy=nextstep;
			nextstep=currentstep;
			currentstep=dummy;
		}
		//Episode終了
		//EV3を停止させる
		MC.Stop();
		System.out.println("--------------------Episode End.");//TODO
		MC.OnLED(MachineControl.LED_PATTERN_OFF);//TODO
	}
	public int GetInterval()
	{
		return Interval;
	}
	public StochasticPolicy GetRegularPolicy()
	{
		return RegularPolicy;
	}
	public StochasticPolicy GetCurrentPolicy()
	{
		return CurrentPolicy;
	}

	public State GetState(int i)
	{
		return State[i];
	}

	public Control GetControl(int i, int u)
	{
		return Control[i][u];
	}


}
