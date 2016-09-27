package continuum.essentials.client.state;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;

public class StateMapperStatic extends StateMapperBase
{
	private final ModelResourceLocation location;
	
	public StateMapperStatic(ModelResourceLocation location)
	{
		this.location = location;
	}
	
	@Override
	protected ModelResourceLocation getModelResourceLocation(IBlockState state)
	{
		return this.location;
	}
	
	public static IStateMapper create(ModelResourceLocation location)
	{
		return new StateMapperStatic(location);
	}
}
