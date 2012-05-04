import java.util.ArrayList;

public class Start {

	public static void main(String[] args) {
		
		ArrayList<Constraint> constraints = new ArrayList<Constraint>();
		int numStepsStop = 20;
		double[] z0 = {2,3};
		boolean max = false;
		double[] c1 = {0.5,0.25,4};
		int s1 = 0;
		double[] c2 = {1,3,20};
		int s2 = 1;
		double[] c3 = {1,1,10};
		int s3 = 2;
		constraints.add(new Constraint(c1,s1));
		constraints.add(new Constraint(c2,s2));
		constraints.add(new Constraint(c3,s3));
		StandardForm stdFrm = new StandardForm(z0,max,constraints);
		System.out.println("Standard Form Tableu:");
		stdFrm.Tableu.print(2,2);
		Simplex simplex = new Simplex(numStepsStop);
		simplex.solve(stdFrm);
		System.out.println("Optimal Tableu:");
		stdFrm.Tableu.print(2,2);
		System.out.println("Variables:\n-------------------------------\n");
		for(int i=0;i<simplex.solution.length;i++){
			System.out.println("variable "+(i+1)+": "+simplex.solution[i]);
		}
		System.out.println("\nz value: "+simplex.z);
		System.out.println("number of steps: "+simplex.numSteps);

	}

}
