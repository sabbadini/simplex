
public class Simplex {
	
	int numStepsStop;
	int numSteps = 0;
	boolean[] basicVariables;
	public double[] solution;
	public double z;
	
	public Simplex(int numStepsStop){
		this.numStepsStop = numStepsStop;
	}
	
	public void solve(StandardForm stdFrm){
		basicVariables = new boolean[stdFrm.n-2];
		for(int j=1;j<stdFrm.n-1;j++){
			if(stdFrm.tableu[0][j]!=0){basicVariables[j-1]=false;}
			else{
				basicVariables[j-1]=false;
				int num1 = 0;
				int num0 = 1;
				for(int i=1;i<stdFrm.m;i++){
					if(stdFrm.tableu[i][j]==0){num0 += 1;}
					else{
						if(stdFrm.tableu[i][j]==1){num1 += 1;}
					}
				}
				if(num1==1 && num0==stdFrm.m-1){basicVariables[j-1]=true;}
			}
		}
		if(stdFrm.max==true){System.out.println("Solving Max:\n-------------------------------\n");}
		else{System.out.println("Solving Min:\n-------------------------------\n");}
		while(solved(stdFrm)==false&&numSteps<=numStepsStop){
			numSteps +=1;
			int entryColumn = 1;
			if(stdFrm.max==true){
				for(int i=0;i<basicVariables.length;i++){
					if(basicVariables[i]==false && stdFrm.tableu[0][i+1]<stdFrm.tableu[0][entryColumn]){entryColumn=i+1;}
				}
			}
			else{
				for(int i=0;i<basicVariables.length;i++){
					if(basicVariables[i]==false && stdFrm.tableu[0][i+1]>stdFrm.tableu[0][entryColumn]){entryColumn=i+1;}
				}	
			}
			int minRatioRow = -1;
			double minRatio = -1;
			for(int i=1;i<stdFrm.m;i++){
				double denom = stdFrm.tableu[i][entryColumn];
				if(denom>0){
					minRatioRow=i;
					minRatio=stdFrm.tableu[i][stdFrm.n-1]/denom;
				}
				if(minRatioRow!=-1){break;}
			}
			for(int i=minRatioRow+1;i<stdFrm.m;i++){
				double denom = stdFrm.tableu[i][entryColumn];
				if(denom>0){
					double ratio = stdFrm.tableu[i][stdFrm.n-1]/denom;
					if(ratio<minRatio){
						minRatioRow = i;
						minRatio = ratio;
					}
				}
			}
			double denom = stdFrm.tableu[minRatioRow][entryColumn];
			for(int j=0;j<stdFrm.n;j++){
				stdFrm.tableu[minRatioRow][j]=stdFrm.tableu[minRatioRow][j]/denom;
				if(stdFrm.tableu[minRatioRow][j]==-0){
					stdFrm.tableu[minRatioRow][j]=0;
				}
			}
			for(int i=0;i<stdFrm.m;i++){
				if(i!=minRatioRow){
					double c = -stdFrm.tableu[i][entryColumn];
					for(int j=0;j<stdFrm.n;j++){
						stdFrm.tableu[i][j]=stdFrm.tableu[i][j]+c*stdFrm.tableu[minRatioRow][j];
					}
				}
			}
			for(int j=1;j<stdFrm.n-1;j++){
				if(stdFrm.tableu[0][j]!=0){basicVariables[j-1]=false;}
				else{
					basicVariables[j-1]=false;
					int num1 = 0;
					int num0 = 1;
					for(int i=1;i<stdFrm.m;i++){
						if(stdFrm.tableu[i][j]==0){num0 += 1;}
						else{
							if(stdFrm.tableu[i][j]==1){num1 += 1;}
						}
					}
					if(num1==1 && num0==stdFrm.m-1){basicVariables[j-1]=true;}
				}
			}
		}
	}
	
	boolean solved(StandardForm stdFrm){
		boolean solved = false;
		if(stdFrm.max==true){
			int negVar = 0;
			for(int i=0;i<basicVariables.length;i++){
				if(basicVariables[i]==false && stdFrm.tableu[0][i+1]<0){
					negVar += 1;
				}
			}
			if(negVar==0){
				solved = true;
				solution = new double[basicVariables.length];
				for(int j=0;j<solution.length;j++){
					if(basicVariables[j]==true){
						for(int i=1;i<stdFrm.m;i++){
							if(stdFrm.tableu[i][j+1]==1){solution[j]=stdFrm.tableu[i][stdFrm.n-1];}
						}
					}
				}
				z = stdFrm.tableu[0][stdFrm.n-1];
			}
		}
		else{
			int posVar = 0;
			for(int i=0;i<basicVariables.length;i++){
				if(basicVariables[i]==false && stdFrm.tableu[0][i+1]>0){
					posVar += 1;
				}
			}
			if(posVar==0){
				solved = true;
				solution = new double[basicVariables.length];
				for(int j=0;j<solution.length;j++){
					if(basicVariables[j]==true){
						for(int i=1;i<stdFrm.m;i++){
							if(stdFrm.tableu[i][j+1]==1){solution[j]=stdFrm.tableu[i][stdFrm.n-1];}
						}
					}
				}
				z = stdFrm.tableu[0][stdFrm.n-1];
			}
		}
		return solved;
	}
	
}
