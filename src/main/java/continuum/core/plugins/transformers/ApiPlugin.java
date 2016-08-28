package continuum.core.plugins.transformers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;

import org.apache.commons.lang3.tuple.Pair;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.MethodNode;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import continuum.essentials.hooks.ObjectHooks;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.Launch;

public class ApiPlugin implements IClassTransformer
{
	private static final HashMap<String, HashMap<String, MethodNode>> methods = Maps.newHashMap();
	private static final HashMap<String, HashSet<String>> packages = Maps.newHashMap();
	private static final HashMap<String, String> classes = Maps.newHashMap();
	private static final Predicate<AnnotationNode> isReflection = new Predicate<AnnotationNode>()
			{
				@Override
				public boolean apply(AnnotationNode node)
				{
					return "Lcontinuum/essentials/mod/APIMethodReflectable;".equals(node.desc);
				}
			};
	private static final Predicate<AnnotationNode> isMirrorable = new Predicate<AnnotationNode>()
			{
				@Override
				public boolean apply(AnnotationNode node)
				{
					return "Lcontinuum/essentials/mod/APIMethodMirrorable;".equals(node.desc);
				}
			};
	
	@Override
	public byte[] transform(String name, String transformedName, byte[] base)
	{
		final ClassNode node = new ClassNode();
		final ClassReader reader = new ClassReader(base);
		if(name.startsWith("continuum.core.plugins.transformers.ApiPlugin"))
			return base;
		String clasz = getMethodsContainer(name);
		if(clasz != null)
		{
			reader.accept(node, 0);
			for(MethodNode method : node.methods)
			{
				AnnotationNode annotation = getReflectionMatch(method.visibleAnnotations); 
				if(annotation != null)
				{
					String mirroredClass = "clasz".equals((String)annotation.values.get(0)) ? (String)annotation.values.get(1) : (String)annotation.values.get(3);
					String mirroredMethod = "method".equals((String)annotation.values.get(2)) ? (String)annotation.values.get(3) : (String)annotation.values.get(1);
					if(methods.containsKey(mirroredClass))
						methods.get(mirroredClass).put(mirroredMethod, method);
					else
						methods.put(mirroredClass, ObjectHooks.fromEntries(Pair.of(mirroredMethod, method)));
				}
			}
			return base;
		}
		
		if(!packages.isEmpty() && containsPackage(name))
		{
			if(!methods.containsKey(name))
				try
				{
					for(HashSet<String> set : packages.values())
						for(String string : set)
							Launch.classLoader.loadClass(string);
				}
				catch(ClassNotFoundException e)
				{
					e.printStackTrace();
				}
			for(Entry<String, String> entry : classes.entrySet())
				if(methods.containsKey(name) && !methods.get(name).isEmpty() && entry.getValue().equals(name))
				{
					reader.accept(node, 0);
					Pair<String, String> pair;
					MethodNode replacement;
					for(MethodNode method : node.methods)
						if(Iterables.any(method.visibleAnnotations == null ? Lists.newArrayList() : method.visibleAnnotations, isMirrorable) && (replacement = getMethod(methods.get(name), method.name)) != null)
						{
							ListIterator<AbstractInsnNode> iterator = method.instructions.iterator();
							while(iterator.hasNext())
							{
								AbstractInsnNode instruction = iterator.next();
								if(!(instruction instanceof LabelNode || instruction instanceof LineNumberNode))
								{
									iterator.next();
									iterator.remove();
								}
							}
							ListIterator<AbstractInsnNode> iterator1 = replacement.instructions.iterator();
							while(iterator1.hasNext())
							{
								AbstractInsnNode instruction = iterator1.next();
								if(!(instruction instanceof LabelNode || instruction instanceof LineNumberNode))
									method.instructions.add(instruction);
							}
						}
			        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
			        node.accept(writer);
			        return writer.toByteArray();
				}
		}
		return base;
	}
	
	private MethodNode getMethod(HashMap<String, MethodNode> methods, String name)
	{
		for(Entry<String, MethodNode> entry : methods.entrySet())
			if(entry.getKey().equals(name))
				return entry.getValue();
		return null;
	}
	
	private static String getMethodsContainer(String name)
	{
		for(HashSet<String> set : packages.values())
			for(String methodsClass : set)
				if(methodsClass.equals(name))
					return methodsClass;
		return null;
	}
	
	/**
	 * Put this here to avoid FMLSecurity swatting us
	 */
	private static boolean containsPackage(String name)
	{
		try
		{
			return Iterables.any(packages.keySet(), new PackagesContains(name));
		}
		catch(Exception e)
		{
			
		}
		return false;
	}
	
	private static AnnotationNode getReflectionMatch(List<AnnotationNode> annotations)
	{
		try
		{
			return annotations == null ? null : Iterables.find(annotations, isReflection);
		}
		catch(Exception e)
		{
			
		}
		return null;
	}
	
	public static void putAPIPackage(String _package, Iterable<String> prerequisites)
	{
		packages.put(_package, Sets.newHashSet(prerequisites));
	}
	
	public static void putAPIPackages(Iterable<String> packages, Iterable<String> prerequisites)
	{
		for(String _package : packages)
			putAPIPackage(_package, prerequisites);
	}
	
	public static void putAPIClass(String name, String path)
	{
		classes.put(name, path);
	}
	
	private static class PackagesContains implements Predicate<String>
	{
		private final String name;
		
		private PackagesContains(String name)
		{
			this.name = name;
		}

		@Override
		public boolean apply(String _pacckage)
		{
			return this.name.startsWith(_pacckage);
		}
	}
}
