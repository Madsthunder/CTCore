package continuum.essentials.tileentity;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IInteractionObject;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class TileEntityInventory extends TileEntitySyncable implements IInventory, IInteractionObject
{
	private String customName;
	private final NonNullList<ItemStack> stacks;
	private final int stackSizeLimit;
	private UUID owner;
	private boolean modified;
	private InvWrapper wrapper;
	
	public TileEntityInventory(int inventorySize)
	{
		this(inventorySize, 64);
	}
	
	public TileEntityInventory(int inventorySize, int stackSizeLimit)
	{
		this.stacks = NonNullList.func_191197_a(inventorySize, ItemStack.field_190927_a);
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
		return this.stacks.size();
	}
	
	@Override
	public ItemStack getStackInSlot(int index)
	{
		return this.stacks.get(index);
	}
	
	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		ItemStack stack = ItemStackHelper.getAndSplit(this.stacks, index, count);
		this.markDirty();
		return stack;
	}
	
	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		this.markDirty();
		return ItemStackHelper.getAndRemove(this.stacks, index);
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		this.stacks.set(index, stack);
		if(stack != null && stack.func_190916_E() > this.getInventoryStackLimit())
			stack.func_190920_e(this.getInventoryStackLimit());
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
		this.markDirty();
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

	@Override
	public boolean func_191420_l()
	{
		for(ItemStack stack : this.stacks)
			if(stack.func_190926_b())
				return false;
		return true;
	}
}
