package continuum.essentials.mod;

import java.io.File;

import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ConfigParser
{
	private final Configuration config;
	
	public ConfigParser(File file)
	{
		this(new Configuration(file));
	}
	
	public ConfigParser(Configuration config)
	{
		this.config = config;
	}
	
	public Configuration getConfig()
	{
		return this.config;
	}
	
	public ConfigCategoryParser getCategoryParser(String category)
	{
		return new ConfigCategoryParser(this.config.getCategory(category));
	}
	
	public static class ConfigCategoryParser
	{
		private final ConfigCategory category;
		
		public ConfigCategoryParser(ConfigCategory category)
		{
			this.category = category;
			System.out.println(category);
		}
		
		public Object get(String name, Object defaultt)
		{
			if(defaultt instanceof Boolean)
				return this.getBoolean(name, (boolean)defaultt);
			else if(defaultt instanceof Integer)
				return this.getInt(name, (int)defaultt);
			else if(defaultt instanceof Double)
				return this.getDouble(name, (double)defaultt);
			return this.getString(name, defaultt.toString());
		}
		
		public String getString(String name, String defaultt)
		{
			return this.getProperty(name, defaultt, 'S').getString();
		}
		
		public boolean getBoolean(String name, boolean defaultt)
		{
			return this.getProperty(name, defaultt, 'B').getBoolean(defaultt);
		}
		
		public int getInt(String name, int defaultt)
		{
			return this.getProperty(name, defaultt, 'I').getInt(defaultt);
		}
		
		public double getDouble(String name, double defaultt)
		{
			return this.getProperty(name, defaultt, 'D').getDouble(defaultt);
		}
		
		public Property getProperty(String name, Object defaultt, char id)
		{
			Property property = this.category.containsKey(name) ? this.category.get(name) : new Property(name, defaultt.toString(), Property.Type.tryParse(id));
			this.category.put(name, property);
			return property;
		}
	}
}