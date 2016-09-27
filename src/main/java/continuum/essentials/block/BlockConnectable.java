package continuum.essentials.block;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.base.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.BlockStateContainer.Builder;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.common.property.Properties;

public abstract class BlockConnectable extends Block implements IConnectable
{
	public static final PropertyBool[] isConnected = new PropertyBool[] { PropertyBool.create("down"), PropertyBool.create("up"), PropertyBool.create("north"), PropertyBool.create("south"), PropertyBool.create("west"), PropertyBool.create("east") };
	public static final IUnlistedProperty<Boolean>[] isConectedUnlisted = new IUnlistedProperty[] { Properties.toUnlisted(isConnected[0]), Properties.toUnlisted(isConnected[1]), Properties.toUnlisted(isConnected[2]), Properties.toUnlisted(isConnected[3]), Properties.toUnlisted(isConnected[4]), Properties.toUnlisted(isConnected[5]) };
	
	public BlockConnectable(Material material)
	{
		super(material);
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess access, BlockPos pos)
	{
		for (EnumFacing direction : EnumFacing.values())
			state = state.withProperty(isConnected[direction.ordinal()], IConnectable.isConnectedTo(this, access, pos, direction));
		return state;
	}
	
	public Builder getBuilder()
	{
		return new Builder(this).add(isConnected);
	}
	
	@Override
	public BlockStateContainer createBlockState()
	{
		return new Builder(this).add(isConnected).build();
	}
}
