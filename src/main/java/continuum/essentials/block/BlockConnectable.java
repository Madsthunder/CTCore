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

public abstract class BlockConnectable extends Block
{
	public static final PropertyBool[] isConnected = new PropertyBool[]
			{
					PropertyBool.create("down"),
					PropertyBool.create("up"),
					PropertyBool.create("north"),
					PropertyBool.create("south"),
					PropertyBool.create("west"),
					PropertyBool.create("east")
			};
	
	public static final IUnlistedProperty<Boolean>[] isConectedUnlisted = new IUnlistedProperty[]
			{
					Properties.toUnlisted(isConnected[0]),
					Properties.toUnlisted(isConnected[1]),
					Properties.toUnlisted(isConnected[2]),
					Properties.toUnlisted(isConnected[3]),
					Properties.toUnlisted(isConnected[4]),
					Properties.toUnlisted(isConnected[5])
			};
	
	private final Predicate<Pair<IBlockAccess, BlockPos>> predicate;
	
	public BlockConnectable(Material material, Predicate<Pair<IBlockAccess, BlockPos>> predicate)
	{
		super(material);
		this.predicate = predicate;
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess access, BlockPos pos)
	{
		for(EnumFacing direction : EnumFacing.values())
			state = state.withProperty(isConnected[direction.ordinal()], this.isConnectedTo(access, pos, direction));
		return state;
	}
	
	public Boolean isConnectedTo(IBlockAccess access, BlockPos pos, EnumFacing direction)
	{
		return this.canConnectTo(access, pos, direction) && this.canOtherConnect(access, pos, direction);
	}
	
	public Boolean canOtherConnect(IBlockAccess access, BlockPos pos, EnumFacing direction)
	{
		Block block;
		return (block = access.getBlockState(pos.offset(direction)).getBlock()) instanceof BlockConnectable ? ((BlockConnectable)block).canConnectTo(access, pos.offset(direction), direction.getOpposite()) : true;
	}
	
	public Boolean canConnectTo(IBlockAccess access, BlockPos pos, EnumFacing direction)
	{
		return this.getPredicate().apply(Pair.of(access, pos.offset(direction)));
	}
	
	public Predicate<Pair<IBlockAccess, BlockPos>> getPredicate()
	{
		return this.predicate;
	}
	
	@Override
	public BlockStateContainer createBlockState()
	{
		return new Builder(this).add(isConnected).build();
	}
}
