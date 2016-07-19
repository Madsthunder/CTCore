package continuum.essentials.util;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.common.registry.RegistryDelegate;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTab extends CreativeTabs
{
	private RegistryDelegate delegate;
	private ItemStack override;

	public CreativeTab(String name, IForgeRegistryEntry.Impl impl)
	{
		super(name);
		this.delegate = impl.delegate;
	}
	
	public CreativeTab(String name, ItemStack override)
	{
		super(name);
		this.override = override;
	}
	
	@Override
	public Item getTabIconItem()
	{
		return this.delegate.get() instanceof Block ? Item.getItemFromBlock((Block)this.delegate.get()) : this.delegate.get() instanceof Item ? (Item)this.delegate.get() : null;
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public ItemStack getIconItemStack()
    {
        if (this.override == null)
        {
            this.override = new ItemStack(this.getTabIconItem(), 1, this.getIconItemDamage());
        }

        return this.override;
    }
}
