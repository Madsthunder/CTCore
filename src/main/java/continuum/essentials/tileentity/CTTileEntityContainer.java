package continuum.essentials.tileentity;

import java.util.UUID;

import continuum.essentials.hooks.NBTHooks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class CTTileEntityContainer extends TileEntitySyncable implements IInventory, IInteractionObject
{
	private String customName;
	private final ItemStack[] stacks;
	private final Integer stackSizeLimit;
	private UUID owner;
	private Boolean modified = false;
	private InvWrapper wrapper;
	
	public CTTileEntityContainer(Integer inventorySize)
	{
		this(inventorySize, 64);
	}
	
	public CTTileEntityContainer(Integer inventorySize, Integer stackSizeLimit)
	{
		this.stacks = new ItemStack[inventorySize];
		this.stackSizeLimit = stackSizeLimit;
	}
	
	@Override
	public NBTTagCompound writeItemsToNBT()
	{
		NBTTagCompound compound = new NBTTagCompound();
		compound.setTag("Items", CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(this.getWrapper(), null));
		return compound;
	}
	
	@Override
	public void readItemsFromNBT(NBTTagCompound compound)
	{
		CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(this.getWrapper(), null, compound.getTagList("Items", 10));
		this.modified = false;
	}
	
	@Override
	public String getName()
	{
		return this.hasCustomName() ? this.customName : "container.crafting_grid";
	}
	
	@Override
	public boolean hasCustomName()
	{
		return this.customName != null && !this.customName.isEmpty();
	}
	
	@Override
	public ITextComponent getDisplayName()
	{
		return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName());
	}
	
	@Override
	public Container createContainer(InventoryPlayer inventory, EntityPlayer player)
	{
		return null;
	}
	
	@Override
	public String getGuiID()
	{
		return "moarwoods:crafting_grid";
	}
	
	@Override
	public int getSizeInventory()
	{
		return this.stacks.length;
	}
	
	@Override
	public ItemStack getStackInSlot(int index)
	{
		return this.stacks[index];
	}
	
	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		ItemStack stack = ItemStackHelper.getAndSplit(this.stacks, index, count);
		if(stack != null)
		{
			this.modified = true;
			this.markDirty();
		}
		return stack;
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
	{
		if(this.modified)
			return !(this.modified = false);
		return super.shouldRefresh(world, pos, oldState, newState);
	}
	
	@Override
	public NBTTagCompound getUpdateTag()
	{
		return super.getUpdateTag();
	}
	
	@Override
	public void handleUpdateTag(NBTTagCompound compound)
	{
		super.handleUpdateTag(compound);
		;
	}
	
	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		this.modified = true;
		return ItemStackHelper.getAndRemove(this.stacks, index);
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		this.stacks[index] = stack;
		if(stack != null && stack.stackSize > this.getInventoryStackLimit())
			stack.stackSize = this.getInventoryStackLimit();
		this.modified = true;
		this.markDirty();
	}
	
	@Override
	public int getInventoryStackLimit()
	{
		return this.stackSizeLimit;
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return this.hasOwner() ? this.getOwner().equals(player.getUniqueID()) : true;
	}
	
	@Override
	public void openInventory(EntityPlayer player)
	{
		
	}
	
	@Override
	public void closeInventory(EntityPlayer player)
	{
		
	}
	
	public InvWrapper getWrapper()
	{
		return this.wrapper == null ? (this.wrapper = new InvWrapper(this)) : this.wrapper;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return (T)this.getWrapper();
		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability capability, EnumFacing facing)
	{
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return true;
	}
	
	@Override
	public int getField(int id)
	{
		return 0;
	}
	
	@Override
	public void setField(int id, int value)
	{
		
	}
	
	@Override
	public int getFieldCount()
	{
		return 0;
	}
	
	@Override
	public void clear()
	{
		for (Integer index = 0; index < this.getSizeInventory(); index++)
			this.setInventorySlotContents(index, null);
	}
	
	public Boolean hasOwner()
	{
		return this.owner != null;
	}
	
	public UUID getOwner()
	{
		return this.owner;
	}
	
	public void setOwner(UUID uuid)
	{
		this.owner = uuid;
	}
}
