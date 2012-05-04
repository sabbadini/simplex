import java.util.ArrayList;

import Jama.Matrix;

public class StandardForm {
	
	public int m;//equations, or rows
	public int n;//variables, or columns
	public int a=0;//artificial variables
	public double bigM=0;
	public boolean max;
	public double[][] tableu;
	public Matrix Tableu;
	
	//NOTE: prepared for max problems, not prepared for big M problems, most min problems...
	
	public StandardForm(double[] z0, boolean max, ArrayList<Constraint> constraints){
		this.max = max;
		m = constraints.size() + 1;
		n = 2*(constraints.get(0).elements.length - 1) + 2;
		for(int i=0;i<constraints.size();i++){
			if(constraints.get(i).elements[constraints.get(i).elements.length-1]<0){
				for(int j=0;j<constraints.get(i).elements.length;j++){
					constraints.get(i).elements[j]=-constraints.get(i).elements[i];
					if(constraints.get(i).sign==0){constraints.get(i).sign=1;}
					if(constraints.get(i).sign==1){constraints.get(i).sign=0;}
				}
			}
		}
		for(int i=0;i<constraints.size();i++){
			if(constraints.get(i).sign>0){a++;}
		}
		n+=a;
		tableu = new double[m][n];
		tableu[0][0]=1;
		for(int j=0;j<z0.length;j++){
			tableu[0][j+1]=-z0[j];
		}
		if(this.max==true){
			double smallestZ = z0[0];
			for(int j=1;j<z0.length;j++){
				if(z0[j]<smallestZ){smallestZ=z0[j];}
			}
			bigM = 1000*smallestZ;
			for(int j=n-a;j<n;j++){
				tableu[0][j-1]=bigM;
			}
		}
		else{
			double largestZ = z0[0];
			for(int j=1;j<z0.length;j++){
				if(z0[j]>largestZ){largestZ=z0[j];}
			}
			bigM = 1000*largestZ;
			for(int j=n-a;j<n;j++){
				tableu[0][j-1]=-bigM;
			}
		}
		int artVar = 0;
		for(int i=1;i<m;i++){
			for(int j=0;j<constraints.get(0).elements.length-1;j++){tableu[i][j+1]=constraints.get(i-1).elements[j];}
			if(constraints.get(i-1).sign==0){tableu[i][constraints.get(0).elements.length+i-1]=1;}
			if(constraints.get(i-1).sign==1){
				tableu[i][constraints.get(0).elements.length+i-1]=-1;
				tableu[i][n-a-1+artVar]=1;
				artVar+=1;
			}
			if(constraints.get(i-1).sign==2){
				tableu[i][n-a-1+artVar]=1;
				artVar+=1;
			}
			tableu[i][n-1]=constraints.get(i-1).elements[constraints.get(i-1).elements.length-1];
		}
		if(a>0){
			for(int j=1;j<n;j++){
				for(int i=1;i<m;i++){
					if(constraints.get(i-1).sign>0){
						if(max==true){tableu[0][j]+=-bigM*tableu[i][j];}
						else{tableu[0][j]+=bigM*tableu[i][j];}
					}
				}
			}
		}
		Tableu = new Matrix(tableu);
	}
	
}
