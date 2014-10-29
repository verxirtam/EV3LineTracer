package rl;

public class RandomInt
{
	//Valueの値の最大値
	//(Probability[]のサイズ)
	private int ValueMax;
	//整数の乱数に対する値を格納する配列
	private int[] Value;
	//確率の小数部に対する分布を表す配列
	private double[] Decimal;
	//分割数
	private int PartitionCount;
	//各確率の小数部の和
	double DecimalTotal;
	//乱数の管理を行う
	java.util.Random Rand;
	
	//デフォルトコンストラクタ
	public RandomInt()
	{
		ValueMax=0;
		Value=null;
		Decimal=null;
		PartitionCount=0;
		DecimalTotal=0;
	}
	//コンストラクタ
	public RandomInt(final double[] prob, int part_count)
	{
		if(part_count<1)
		{
			throw new java.lang.IllegalArgumentException("part_count < 1");
		}
		//メンバ変数の初期化
		ValueMax=prob.length;
		Value=new int[part_count];
		Decimal=new double[ValueMax+1];
		PartitionCount=part_count;
		
		//乱数の初期化
		Rand=new java.util.Random();
		
		//prob[]の値を正規化しprobability[]に格納する
		//合わせて各値が正であるかをチェックする
		double[] probability=new double[prob.length];
		double sum=0.0;
		for(int i=0;i<ValueMax;i++)
		{
			if(prob[i]>=0.0)
			{
				sum+=prob[i];
			}
			else
			{
				//prob[i] < 0のため例外発生
				throw new java.lang.IllegalArgumentException("prob[i] < 0");
			}
		}
		//prob[]の合計が0の時は例外発生
		if(sum==0.0)
		{
			throw new java.lang.IllegalArgumentException("sum == 0.0");
		}
		for(int i=0;i<ValueMax;i++)
		{
			probability[i]=prob[i]/sum;
		}
		
		//確率を整数部(prob_int[])、小数部(prob_deci[])に分ける
		int[]     prob_int=new    int[ValueMax];
		double[] prob_deci=new double[ValueMax];
		for(int i=0;i<ValueMax;i++)
		{
			prob_int[i] =(int)(probability[i]*PartitionCount);
			prob_deci[i]=probability[i]-((double)prob_int[i])/((double)PartitionCount);
		}
		
		//Value[],DecimalTotalの初期化
		int p=0;
		for(int i=0;i<ValueMax;i++)
		{
			for(int j=0;j<prob_int[i];j++)
			{
				Value[p]=i;
				p++;
			}
		}
		DecimalTotal=((double)(PartitionCount-p))/((double)PartitionCount);
		for(;p<PartitionCount;p++)
		{
			Value[p]=ValueMax;
		}
		
		//Decimal[]の初期化
		sum=0.0;
		Decimal[0]=0.0;
		for(int i=0;i<ValueMax;i++)
		{
			sum+=prob_deci[i];
			Decimal[i+1]=sum;
		}
	}
	//指定した確率分布に従った疑似乱数を返す
	public int Get()
	{
		//整数一様分布に従う疑似乱数を取得
		int x=Rand.nextInt(PartitionCount);
		//xが整数部に適合するか確認
		if(Value[x]!=ValueMax)
		{
			//整数部のためValue[x]を返す
			return Value[x];
		}
		//xが小数部に適合する場合はdouble値の一様分布から返却値を求める
		double d=DecimalTotal*Rand.nextDouble();
		//線形探索で適合する区間を求める
		for(int i=0;i<Decimal.length-1;i++)
		{
			if((Decimal[i]<=d)&&(d<Decimal[i+1]))
			{
				return i;
			}
		}
		//どの区間にも適合しない場合は最大値を返す
		return ValueMax-1;
	}
	public int GetValueMax()
	{
		return ValueMax;
	}
	//引数に指定された値が選択される確率
	public double GetProbability(int i0)
	{
		int count=0;
		for(int i=0;i<PartitionCount;i++)
		{
			if(Value[i]==i0)
			{
				count++;
			}
		}
		return ((double)count)/((double)PartitionCount)+(Decimal[i0+1]-Decimal[i0]);
	}
}
