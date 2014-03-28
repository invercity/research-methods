package ua.invercity.research;

import Jama.Matrix;

public class Statistic {
	public int rowCount;
	public int columnCount;	
	public double [][] dataMatrix = new double[10][4];
	
	private double [] middleValues; 
	private double [] standOtkl;
	private double [][] normMatr;
	private double [][] cormatr;
	
	public Statistic(int rows, int col, Object [][] matr) {
		// initialize variables
		rowCount = rows;
		columnCount = col;
		parseDouble(matr);
		middleValues = new double[columnCount];
		standOtkl = new double[columnCount]; 
		normMatr = new double[rowCount][columnCount];
		cormatr = new double[3][3]; 
		// make calculations
		computeAll();
	}
	
	private void computeAll() {
		computeAvg();
		computeStandartOtkl();
		computeNorm();
		computeTransMult();
	}
	
	private void computeAvg() { 
		for (int k=0; k<columnCount; k++) { 
			double summa = 0; 
			for (int i=0; i<rowCount; i++) summa += dataMatrix[i][k]; 
			middleValues[k] = summa/rowCount; 
		}
	}
	
	private void computeStandartOtkl() { 
		for (int k=0; k<columnCount; k++) { 
			double summa = 0; 
			for (int i=0; i<rowCount; i++) summa += Math.pow((dataMatrix[i][k]-middleValues[k]),2); 
			standOtkl[k] = Math.sqrt(summa/(rowCount-1)); 
		} 
	}
	
	private void computeNorm() { 
		for (int k=0; k<columnCount; k++) { 
			for (int i=0; i<rowCount; i++) normMatr[i][k]=(dataMatrix[i][k]-middleValues[k])/standOtkl[k];
		} 
	} 
	
	private void computeTransMult() { 
		double[][] tr = new double[columnCount-1][rowCount]; 
		double[][] normMatrNew = new double[rowCount][columnCount-1];
		for (int i=1; i<columnCount; i++) { 
			for (int k=0; k<rowCount; k++) normMatrNew[k][i-1]=normMatr[k][i]; 
		}
		for (int i=0; i<rowCount; i++) { 
			for (int k=0; k<columnCount-1; k++) tr[k][i]=normMatrNew[i][k]; 
		}
		
		cormatr = multMatrix(tr,normMatrNew,columnCount-1,rowCount,columnCount-1);
		for (int i=0; i<columnCount-1; i++) { 
			for (int k=0; k<columnCount-1; k++) cormatr[k][i] = (cormatr[k][i])/9; 
		}
	} 
	
	public double getDetermMatrix() { 
		return cormatr[0][0]*cormatr[1][1]*cormatr[2][2]+cormatr[2][0]*cormatr[0][1]*cormatr[1][2]+cormatr[1][0]*cormatr[0][2]*cormatr[2][1]-(cormatr[0][2]*cormatr[1][1]*cormatr[2][0]+cormatr[0][1]*cormatr[1][0]*cormatr[2][2]+cormatr[0][0]*cormatr[2][1]*cormatr[1][2]); 
	}
	
	public double getTestFi() { 
		return -(rowCount-1-((2*3+5)/6))*Math.log10(getDetermMatrix());
	}

	public double[] getTestFArray() { 
        Matrix a = new Matrix(cormatr);
        Matrix b = a.inverse();
        double[] result = new double[3];
		result[0] = b.get(0,0)*((rowCount-3)/(3-1));
		result[1] = b.get(1,1)*((rowCount-3)/(3-1));
		result[2] = b.get(2,2)*((rowCount-3)/(3-1));
		return result;
	}
	
	public double[] getTestKor() { 
		Matrix a = new Matrix(cormatr);
	    Matrix b = a.inverse();
	    double[] result = new double[3];
		result[0] = -(b.get(0,1))/Math.sqrt(b.get(0,0)*b.get(1,1));
		result[1] = -(b.get(0,2))/Math.sqrt(b.get(0,0)*b.get(2,2));
		result[2] = -(b.get(1,2))/Math.sqrt(b.get(1,1)*b.get(2,2));
		return result;
	}
	
	public double[] getTestT() { 
		// need to be replaced
		double r123= -(0.641897945)/Math.sqrt(1.316084621*1.317019012);
		double r132=-(-0.034464957)/Math.sqrt(1.00484673*1.316084621);
		double r231=-(0.046116733)/Math.sqrt(1.317019012*1.00484673);
		double[] result = new double[3];
		result[0] = r123*Math.sqrt(rowCount-3)/Math.sqrt(1-Math.pow(r123,2));
		result[1] = r132*Math.sqrt(rowCount-3)/Math.sqrt(1-Math.pow(r132,2));
		result[2] = r231*Math.sqrt(rowCount-3)/Math.sqrt(1-Math.pow(r231,2));
		return result;
	}
	
	private double[][] multMatrix(double[][]matr1, double[][] matr2, int lengrows1, int lengcol1, int lengcol2) {
		double[][] resultMatr = new double[lengrows1][lengcol2];
		
		double summa = 0;
		for (int i=0; i<lengrows1; i++) { 
			for (int j=0; j<lengcol2; j++) { 
				for (int z=0; z<lengcol1; z++) {
					summa += matr1[i][z]*matr2[z][j];
				}
				resultMatr[i][j]=summa;
				summa = 0;
			}			
		}
		return resultMatr;
	}
	
	public void ComputArgument() {
		// МНК В=(Хтрансп*Х)-1*(Хтрасп*У)
		// Матрица Х(х-си нормализованные) {1,x11,x12,x13; 1,x21,x22,x32,....}
		double[][] matrX = new double[rowCount][columnCount];
		//транспонированая матр Х
		double[][] matrXt = new double[columnCount][rowCount];
		//резулитирующая матр умножения транспонированой матр Х на обычную Х
		double[][] matrMultXtX = new double[columnCount][columnCount];
		//резулитирующая матр умножения транспонированой матр Х на У
		double[][]matrMultXtY = new double[columnCount][1];
		//результирующая матр коефициентов {в0,в1,в2,в3}
		double[][] resultMatr = new double[columnCount][1];
		//матр нормализованных У
		double[][] arrY = new double[rowCount][1];
		
		//формируем маирицы Х(нулевой столбик забит эдиницами) и У
		for(int i=0; i<columnCount;i++) {
			for(int j=0; j<rowCount;j++) {
				if(i==0) {
					arrY[j][i] = normMatr[j][i];
					matrX[j][i] = 1;
				}
				else matrX[j][i] = normMatr[j][i];
			}
		}
		//транспонирование матр Х
		for (int i=0; i<rowCount; i++) { 
			for (int k=0; k<columnCount; k++) { 
				matrXt[k][i]=matrX[i][k]; 
			} 
		}
		//умножение транспонированой матр Х на обычную Х
		matrMultXtX = multMatrix(matrXt,matrX,columnCount,rowCount,columnCount);
		//умножение транспонированой матр Х на матр У
		matrMultXtY = multMatrix(matrXt,arrY,columnCount,rowCount,1);
	    
		//нахождение обратной матрицы к резулитирующая матр умножения транспонированой матр Х на обычную Х
	    Matrix a = new Matrix(matrMultXtX);
	    Matrix b = a.inverse();
		for (int i=0; i<columnCount; i++) { 
			for (int z=0; z<columnCount; z++) {
				matrMultXtX[i][z] = b.get(i,z);
			}
		}
	    resultMatr = multMatrix(matrMultXtX,matrMultXtY,columnCount,columnCount,1);
	    
		//подсчет коефициентов
		double b1= resultMatr[1][0]*standOtkl[0]/standOtkl[1];		
		double b2=resultMatr[2][0]*standOtkl[0]/standOtkl[2];
		double b3=resultMatr[3][0]*standOtkl[0]/standOtkl[3];
		double b0= middleValues[0]-b1*middleValues[1]-b2*middleValues[2]-b3*middleValues[3];
		for(int i=0; i<rowCount;i++) {
			//System.out.println(b0+b1*dataMatrix[i][1]+b2*dataMatrix[i][2]+b3*dataMatrix[i][3]);
		}

	}
	
	private double[][] parseDouble(Object[][] data) {
		for (int i=0;i<rowCount;i++)
			for (int j=0;j<columnCount;j++)
				dataMatrix[i][j] = (double) data[i][j];
		return dataMatrix;
	}
}
