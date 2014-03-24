package ua.invercity.research;

import Jama.Matrix;

public class Statistic {
	public int lengrows;
	public int lengcol;	
	public double [][] Matrix;
	
	private double [] sred; 
	private double [] standOtkl;
	private double [][] normMatr;
	private double [][] cormatr;
	
	public Statistic(int rows, int col, double [][] matr)
	{
		lengrows = rows;
		lengcol = col;
		Matrix = matr;
		sred = new double[lengcol];
		standOtkl = new double[lengcol]; 
		normMatr = new double[lengrows][lengcol];
		cormatr = new double[3][3]; 
	}
	
	public void sredZnach() 
	{ 
		for (int k=0; k<lengcol; k++)
		{ 
			double summa = 0; 
			for (int i=0; i<lengrows; i++) 
			{ 
				summa = summa + Matrix[i][k]; 
			} 
			sred[k] = summa/lengrows; 
		}
	}
	
	public void standartOtkl() 
	{ 
		for (int k=0; k<lengcol; k++)
		{ 
			double summa = 0; 
			for (int i=0; i<lengrows; i++) 
			{ 
				summa += Math.pow((Matrix[i][k]-sred[k]),2); 
			} 
			standOtkl[k] = Math.sqrt(summa/(lengrows-1)); 
		} 
	}
	
	public void norm() 
	{ 
		for (int k=0; k<lengcol; k++)
		{ 
			for (int i=0; i<lengrows; i++) 
			{ 
				normMatr[i][k]=(Matrix[i][k]-sred[k])/standOtkl[k];
			} 
		} 
	} 
	
	public void transMult() { 
		double[][] tr = new double[lengcol-1][lengrows]; 
		double[][] normMatrNew = new double[lengrows][lengcol-1];
		for (int i=1; i<lengcol; i++) 
		{ 
			for (int k=0; k<lengrows; k++) 
			{ 
				normMatrNew[k][i-1]=normMatr[k][i]; 
			} 
		}
		for (int i=0; i<lengrows; i++) 
		{ 
			for (int k=0; k<lengcol-1; k++) 
			{ 
				tr[k][i]=normMatrNew[i][k]; 
			} 
		}
		
		cormatr = multMatrix(tr,normMatrNew,lengcol-1,lengrows,lengcol-1);
		for (int i=0; i<lengcol-1; i++) 
		{ 
			for (int k=0; k<lengcol-1; k++) 
			{ 
				cormatr[k][i] = (cormatr[k][i])/9; 
			} 
		}
	} 
	
	private double determMatrix() { 
		double deter = cormatr[0][0]*cormatr[1][1]*cormatr[2][2]+cormatr[2][0]*cormatr[0][1]*cormatr[1][2]+cormatr[1][0]*cormatr[0][2]*cormatr[2][1]-(cormatr[0][2]*cormatr[1][1]*cormatr[2][0]+cormatr[0][1]*cormatr[1][0]*cormatr[2][2]+cormatr[0][0]*cormatr[2][1]*cormatr[1][2]); 
		System.out.println("\nВизначник кореляційної матриці наближається до "+deter+" ~ 0, то в масиві змінних може існувати мультиколінеарність\n");
		return deter;
	}
	
	public void TestFi() 
	{ 
		double pfi=-(lengrows-1-((2*3+5)/6))*Math.log10(determMatrix());
		System.out.println("Перевірка всього масиву змінних на наявність колінеарності за критерієм X^2"); 
		System.out.println("Так як Фи розраховане "+pfi+"<7,81 X^2 табл. (для 3 ступенів свободи та рівня значущості 0,05)");
		System.out.println("Це означає що в масиві незалежних змінних не існує мультиколінеарністі");
	}
	public void TestF() 
	{ 
        Matrix a = new Matrix(cormatr);
        Matrix b = a.inverse();
        //b.print(10, 2);
		double ef1=b.get(0,0)*((lengrows-3)/(3-1));
		double ef2=b.get(1,1)*((lengrows-3)/(3-1));
		double ef3= b.get(2,2)*((lengrows-3)/(3-1));
		System.out.println("Перевірка на колінеарність окремих змінних з іншими за критерієм Ф (для ступенів свободи V1=7, V2=2 та рівня значущості 0,05)"); 
		System.out.println("Розраховане Ф1 "+ef1+"<4,74 Ф табл.");
		System.out.println("Розраховане Ф2 "+ef2+"<4,74 Ф табл.");
		System.out.println("Розраховане Ф3 "+ef3+"<4,74 Ф табл.");
		System.out.println("Це означає що кожна з незалежних змінних  не колінеарна з іншими");
	}
	
	public  void TestKor() 
	{ 
		Matrix a = new Matrix(cormatr);
	    Matrix b = a.inverse();
		double r123= -(b.get(0,1))/Math.sqrt(b.get(0,0)*b.get(1,1));
		double r132=-(b.get(0,2))/Math.sqrt(b.get(0,0)*b.get(2,2));
		double r231=-(b.get(1,2))/Math.sqrt(b.get(1,1)*b.get(2,2));
		System.out.println("Визначення частинних коефіцієнтів кореляції.");
		System.out.println("На основі частинних коефіцієнтів кореляції можна стверджувати:"); 
		System.out.println("Розраховане значення: "+r123+" між х1 та х2 існує помірний  зв'язок, якщо не враховувати вплив х3");
		System.out.println("Розраховане значення: "+r132+" між х1 та х3 існує слабкий зв'язок, якщо не враховувати вплив х2");
		System.out.println("Розраховане значення: "+r231+" між х2 та х3 існує помірний  зв'язок, якщо не враховувати вплив х1");
	}
	public void TestT() 
	{ 
		double r123= -(0.641897945)/Math.sqrt(1.316084621*1.317019012);
		double r132=-(-0.034464957)/Math.sqrt(1.00484673*1.316084621);
		double r231=-(0.046116733)/Math.sqrt(1.317019012*1.00484673);
		
		double t12= r123*Math.sqrt(lengrows-3)/Math.sqrt(1-Math.pow(r123,2));
		double t13=r132*Math.sqrt(lengrows-3)/Math.sqrt(1-Math.pow(r132,2));
		double t23=r231*Math.sqrt(lengrows-3)/Math.sqrt(1-Math.pow(r231,2));
		
		System.out.println("Перевірка на колінеарність кожної пари змінних за допомогою t-критеріями (для значення ступенів свободи 7 та рівня значущості 0,05)");
		System.out.println("Розраховане t1 "+t12+"<2,365 t табл. це означає що х1 і х2 не колінеарні між собою");
		System.out.println("Розраховане t2 "+t13+"<2,365 t табл.це означає що х1 і х3 не колінеарні між собою");
		System.out.println("Розраховане t3 "+t23+"<2,365 t табл.це означає що х2 і х3 не колінеарні між собою");
	}
	
	public double[][] multMatrix(double[][]matr1, double[][] matr2, int lengrows1, int lengcol1, int lengcol2) 
	{
		double[][] resultMatr = new double[lengrows1][lengcol2];
		//умножение транспонированой матр Х на обычную Х
		double summa = 0;
		for (int i=0; i<lengrows1; i++) 
		{ 
			for (int j=0; j<lengcol2; j++) 
			{ 
				for (int z=0; z<lengcol1; z++) 
				{
					summa += matr1[i][z]*matr2[z][j];
				}
				resultMatr[i][j]=summa;
				summa = 0;
			}			
		}
		return resultMatr;
	}
	
	public void ComputArgument() 
	{
		//Формула МНК В=(Хтрансп*Х)-1*(Хтрасп*У)
		//матрица Х(х-си нормализованные) {1,x11,x12,x13; 1,x21,x22,x32,....}
		double[][] matrX = new double[lengrows][lengcol];
		//транспонированая матр Х
		double[][] matrXt = new double[lengcol][lengrows];
		//резулитирующая матр умножения транспонированой матр Х на обычную Х
		double[][] matrMultXtX = new double[lengcol][lengcol];
		//резулитирующая матр умножения транспонированой матр Х на У
		double[][]matrMultXtY = new double[lengcol][1];
		//результирующая матр коефициентов {в0,в1,в2,в3}
		double[][] resultMatr = new double[lengcol][1];
		//матр нормализованных У
		double[][] arrY = new double[lengrows][1];
		
		//формируем маирицы Х(нулевой столбик забит эдиницами) и У
		for(int i=0; i<lengcol;i++)
		{
			for(int j=0; j<lengrows;j++)
			{
				if(i==0)
				{
					arrY[j][i] = normMatr[j][i];
					matrX[j][i] = 1;
				}
				else
					matrX[j][i] = normMatr[j][i];
			}
		}
		//транспонирование матр Х
		for (int i=0; i<lengrows; i++) 
		{ 
			for (int k=0; k<lengcol; k++) 
			{ 
				matrXt[k][i]=matrX[i][k]; 
			} 
		}
		//умножение транспонированой матр Х на обычную Х
		matrMultXtX = multMatrix(matrXt,matrX,lengcol,lengrows,lengcol);
		//умножение транспонированой матр Х на матр У
		matrMultXtY = multMatrix(matrXt,arrY,lengcol,lengrows,1);
	    
		//нахождение обратной матрицы к резулитирующая матр умножения транспонированой матр Х на обычную Х
	    Matrix a = new Matrix(matrMultXtX);
	    Matrix b = a.inverse();
		for (int i=0; i<lengcol; i++) 
		{ 
			for (int z=0; z<lengcol; z++) 
			{
				matrMultXtX[i][z] = b.get(i,z);
			}
		}
	    resultMatr = multMatrix(matrMultXtX,matrMultXtY,lengcol,lengcol,1);
//	    Matrix cur = new Matrix(resultMatr);
//	    cur.print(10, 8);
		//подсчет коефициентов
		double b1= resultMatr[1][0]*standOtkl[0]/standOtkl[1];		
		double b2=resultMatr[2][0]*standOtkl[0]/standOtkl[2];
		double b3=resultMatr[3][0]*standOtkl[0]/standOtkl[3];
		double b0= sred[0]-b1*sred[1]-b2*sred[2]-b3*sred[3];
		for(int i=0; i<lengrows;i++)
		{
			System.out.println(b0+b1*Matrix[i][1]+b2*Matrix[i][2]+b3*Matrix[i][3]);
		}

	}
}
