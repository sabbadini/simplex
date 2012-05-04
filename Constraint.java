
public class Constraint {
	
	public double[] elements;
	/* sign of constraint:
	 * 0 = less than;
	 * 1 = greater than;
	 * 2 = equals;
	 */
	public int sign;
	
	public Constraint(double[] elements, int sign){
		this.elements = new double[elements.length];
		for(int i=0;i<elements.length;i++){
			this.elements[i] = elements[i];
			this.sign = sign;
		}
	}

}
