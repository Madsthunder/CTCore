package continuum.essentials.math;

import org.apache.commons.lang3.math.Fraction;
import org.apache.commons.lang3.tuple.Triple;

public class Slope2D
{
	protected final Fraction slope;
	protected final Fraction yIntercept;
	
	public Slope2D(Number slope, Number yIntercept)
	{
		this(Fraction.getFraction(slope.toString()), Fraction.getFraction(yIntercept.toString()));
	}
	
	public Slope2D(Fraction slope, Fraction yIntercept)
	{
		this.slope = slope;
		this.yIntercept = yIntercept;
	}
	
	public Double solveForX(Double y, Double fallbackX)
	{
		return (y - this.yIntercept.doubleValue()) / this.slope.doubleValue();
	}
	
	public Fraction solveForX(Fraction y, Fraction fallbackX)
	{
		return (y.subtract(this.yIntercept)).divideBy(this.slope);
	}
	
	public Double solveForY(Double x, Double fallbackY)
	{
		return (this.slope.doubleValue() * x) + this.yIntercept.doubleValue();
	}
	
	public Fraction solveForY(Fraction x, Fraction fallbackY)
	{
		return (this.slope.multiplyBy(x)).add(this.yIntercept);
	}
	
	/**
	 * If there are infinite solutions, the first value will be false and the
	 * second and third values will be null. If there are no solutions, then the
	 * method will return null. If there is one solution, the first value will
	 * be true, the second x and the third y.
	 */
	public Triple<Boolean, Fraction, Fraction> getIntercept(Slope2D other)
	{
		if(this.slope.equals(other.slope))
			if(this.yIntercept.equals(other.yIntercept))
				return Triple.of(false, null, null);
			else
				return null;
		Fraction x = this.yIntercept.subtract(other.yIntercept).divideBy(other.slope.subtract(this.slope));
		Fraction y1 = this.solveForY(x, Fraction.ZERO);
		Fraction y2 = other.solveForY(x, Fraction.ZERO);
		System.out.println(x + ", " + y1 + ", " + y2);
		if(y1.equals(y2))
			return Triple.of(true, x, y1);
		return null;
	}
	
	@Override
	public String toString()
	{
		return "y = " + this.slope + "x + " + this.yIntercept;
	}
}
