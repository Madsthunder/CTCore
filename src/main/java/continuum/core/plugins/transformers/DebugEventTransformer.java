package continuum.core.plugins.transformers;

import java.util.ListIterator;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.common.FMLLog;

public class DebugEventTransformer implements IClassTransformer
{
	private static final String STRING_LIST_METHOD = "()Ljava/util/List;";
	private MethodNode leftMethod;
	private MethodNode rightMethod;
	private Boolean isGettingMethod;
	@Override
	public byte[] transform(String name, String transformedName, byte[] baseClass)
	{
		final ClassNode node = new ClassNode();
		final ClassReader reader = new ClassReader(baseClass);
		if("continuum.essentials.events.DebugInfoEvent".equals(name))
		{
			reader.accept(node, 0);
			for(MethodNode method : node.methods)
				if("modifyLeft".equals(method.name))
					this.leftMethod = method;
				else if("modifyRight".equals(method.name))
					this.rightMethod = method;
		}
		
		else if("net.minecraft.client.gui.GuiOverlayDebug".equals(name))
		{
			reader.accept(node, 0);
			MethodNode debugInfoLeft = null;
			MethodNode debugInfoRight = null;
			for(MethodNode method : node.methods)
				if(STRING_LIST_METHOD.equals(method.desc))
					if(debugInfoLeft == null)
						debugInfoLeft = method;
					else if(debugInfoRight == null)
						debugInfoRight = method;
					else
					{
						FMLLog.warning("GuiOverlayDebug has more than 2 methods with a return type %s?", STRING_LIST_METHOD.substring(2));
						return baseClass;
					}
			if(!insertDebugEvent(debugInfoLeft, this.leftMethod, 5))
			{
				FMLLog.bigWarning("Method call()/modifyLeft() doesent exist?! Not transforming GuiOverlayDebug.");
				return baseClass;
			}
			if(!insertDebugEvent(debugInfoRight, this.rightMethod, 9))
			{
				FMLLog.bigWarning("Method debugInfoRight()/modifyRight() doesent exist?! Not transforming GuiOverlayDebug.");
				return baseClass;
			}
	        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
	        node.accept(writer);
	        return writer.toByteArray();
		}
		return baseClass;
	}
	
	private static boolean insertDebugEvent(MethodNode node, MethodNode eventMethod, Integer variable)
	{
		if(node != null && eventMethod != null)
		{
			ListIterator<AbstractInsnNode> iterator = node.instructions.iterator();
			while(iterator.hasNext())
			{
				AbstractInsnNode instruction = iterator.next();
				if(instruction instanceof InsnNode && instruction.getOpcode() == Opcodes.ARETURN)
				{
					if(iterator.next() instanceof org.objectweb.asm.tree.LabelNode)
						if(!iterator.hasNext())
						{
							iterator.previous();
							iterator.previous();
							iterator.previous();
							iterator.add(new VarInsnNode(Opcodes.ALOAD, variable));
							iterator.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "continuum/essentials/events/DebugInfoEvent", eventMethod.name, eventMethod.desc, false));
							iterator.next();
							iterator.next();
						}
						else
							iterator.previous();
				}
			}
			return true;
		}
		return false;
	}
}
