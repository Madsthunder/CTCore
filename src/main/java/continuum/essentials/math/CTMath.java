package continuum.essentials.math;

import org.apache.commons.lang3.math.Fraction;
import org.apache.commons.lang3.tuple.Triple;

import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class CTMath
{
	public static Slope2D getSlope(Double x1, Double y1, Double x2, Double y2)
	{
		Fraction x = Fraction.getFraction(x1 - x2);
		Fraction y = Fraction.getFraction(y1 - y2);
		if(y1.equals(y2))
			return new IndefiniteSlope(x);
		if(x1.equals(x2))
			return new ZeroSlope(y);
		Fraction slope = y.divideBy(x);
		Fraction yIntercept = y.subtract(slope.multiplyBy(x));
		return new Slope2D(slope, yIntercept);
	}
	
	public static Vec3d subtractVec(Vec3d vec1, Vec3i vec2)
	{
		return vec1.subtract(vec2.getX(), vec2.getY(), vec2.getZ());
	}
	
	public static class IndefiniteSlope extends Slope2D
	{
		private IndefiniteSlope(Fraction x)
		{
			super(x, null);
		}
		
		@Override
		public Double solveForX(Double y, Double fallbackX)
		{
			return this.slope.doubleValue();
		}
		
		@Override
		public Fraction solveForX(Fraction y, Fraction fallbackX)
		{
			return this.slope;
		}
		
		@Override
		public Double solveForY(Double x, Double fallbackY)
		{
			return fallbackY;
		}
		
		@Override
		public Fraction solveForY(Fraction x, Fraction fallbackY)
		{
			return fallbackY;
		}
		
		@Override
		public Triple<Boolean, Fraction, Fraction> getIntercept(Slope2D other)
		{
			if(other instanceof ZeroSlope)
				return Triple.of(true, this.slope, other.slope);
			return null;
		}
		
		public String toString()
		{
			return "x = " + this.slope;
		}
	}
	
	public static class ZeroSlope extends Slope2D
	{
		private ZeroSlope(Fraction y)
		{
			super(y, null);
		}
		
		@Override
		public Double solveForX(Double y, Double fallbackX)
		{
			return fallbackX;
		}
		
		@Override
		public Fraction solveForX(Fraction y, Fraction fallbackX)
		{
			return fallbackX;
		}
		
		@Override
		public Double solveForY(Double x, Double fallbackY)
		{
			return this.slope.doubleValue();
		}
		
		@Override
		public Fraction solveForY(Fraction x, Fraction fallbackY)
		{
			return this.slope;
		}
		
		@Override
		public Triple<Boolean, Fraction, Fraction> getIntercept(Slope2D other)
		{
			if(other instanceof IndefiniteSlope)
				return Triple.of(true, other.slope, this.slope);
			return null;
		}
		
		public String toString()
		{
			return "y = " + this.slope;
		}
	}
	
}
