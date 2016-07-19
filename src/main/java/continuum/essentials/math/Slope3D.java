package continuum.essentials.math;

public class Slope3D
{
	private final Slope2D xSlope;
	private final Slope2D zSlope;
	
	public Slope3D(Slope2D xSlope, Slope2D zSlope)
	{
		this.xSlope = xSlope;
		this.zSlope = zSlope;
	}
}
