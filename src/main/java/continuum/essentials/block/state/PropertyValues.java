package continuum.essentials.block.state;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.block.properties.PropertyHelper;
import net.minecraft.util.IStringSerializable;

public class PropertyValues<T extends Comparable<T> & IStringSerializable> extends PropertyHelper<T>
{
    private final List<T> allowedValues;
    private final HashMap<String, T> nameToValue = Maps.newHashMap();

    public PropertyValues(String name, Class<T> valueClass, Collection<T> allowedValues)
    {
        super(name, valueClass);
        this.allowedValues = Lists.newArrayList(allowedValues);
        String string;
        for (T t : allowedValues)
            if(this.nameToValue.containsKey(string = t.getName()))
                throw new IllegalArgumentException("Multiple values have the same name \'" + string + "\'");
            else
            	this.nameToValue.put(string, t);
    }

    @Override
    public List<T> getAllowedValues()
    {
        return Lists.newArrayList(this.allowedValues);
    }

    @Override
    public Optional<T> parseValue(String value)
    {
        return Optional.fromNullable(this.nameToValue.get(value));
    }
    
    @Override
    public String getName(T value)
    {
        return value.getName();
    }

    public boolean equals(Object p_equals_1_)
    {
        if (this == p_equals_1_)
            return true;
        else if (p_equals_1_ instanceof PropertyValues && super.equals(p_equals_1_))
        {
            PropertyValues propertyvalues = (PropertyValues)p_equals_1_;
            return this.allowedValues.equals(propertyvalues.allowedValues) && this.nameToValue.equals(propertyvalues.nameToValue);
        }
        else
        {
            return false;
        }
    }

    public int hashCode()
    {
        int i = super.hashCode();
        i = 31 * i + this.allowedValues.hashCode();
        i = 31 * i + this.nameToValue.hashCode();
        return i;
    }

    public static <T extends Comparable<T> & IStringSerializable> PropertyValues<T> create(String name, Class<T> clazz)
    {
        return create(name, clazz, Predicates.alwaysTrue());
    }

    public static <T extends Comparable<T> & IStringSerializable> PropertyValues<T> create(String name, Class<T> clazz, Predicate<T> filter)
    {
        return create(name, clazz, Collections2.filter(Lists.newArrayList(clazz.getEnumConstants()), filter));
    }

    public static <T extends Comparable<T> & IStringSerializable> PropertyValues<T> create(String name, Class<T> clazz, T... values)
    {
        return create(name, clazz, Lists.newArrayList(values));
    }

    public static <T extends Comparable<T> & IStringSerializable> PropertyValues<T> create(String name, Class<T> clazz, Collection<T> values)
    {
        return new PropertyValues(name, clazz, values);
    }
}
