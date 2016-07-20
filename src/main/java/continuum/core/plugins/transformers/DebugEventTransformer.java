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
		ClassNode node;
		ClassReader reader;
		if("continuum.essentials.events.DebugInfoEvent".equals(name))
		{
			node = new ClassNode();
			reader = new ClassReader(baseClass);
			reader.accept(node, 0);
			for(MethodNode method : node.methods)
				if("modifyLeft".equals(method.name))
					leftMethod = method;
				else if("modifyRight".equals(method.name))
					rightMethod = method;
		}
		
		else if("net.minecraft.client.gui.GuiOverlayDebug".equals(name))
		{
			node = new ClassNode();
			reader = new ClassReader(baseClass);
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
			ListIterator<AbstractInsnNode> iterator;
			if(debugInfoLeft != null)
			{
				for(iterator = debugInfoLeft.instructions.iterator(); iterator.hasNext();)
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
								iterator.add(new VarInsnNode(Opcodes.ALOAD, 5));
								iterator.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "continuum/essentials/events/DebugInfoEvent", this.leftMethod.name, this.leftMethod.desc, false));
								iterator.next();
								iterator.next();
							}
							else
								iterator.previous();
					}
				}
			}
			if(debugInfoRight != null)
			{
				for(iterator = debugInfoRight.instructions.iterator(); iterator.hasNext();)
				{
					AbstractInsnNode instruction = iterator.next();
					System.out.println(instruction);
					if(instruction instanceof VarInsnNode)
					{
						VarInsnNode var = (VarInsnNode)instruction;
						System.out.println("Index: " + var.var);
					}
					if(instruction instanceof InsnNode && instruction.getOpcode() == Opcodes.ARETURN)
					{
						if(iterator.next() instanceof org.objectweb.asm.tree.LabelNode)
							if(!iterator.hasNext())
							{
								iterator.previous();
								iterator.previous();
								iterator.previous();
								iterator.add(new VarInsnNode(Opcodes.ALOAD, 9));
								iterator.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "continuum/essentials/events/DebugInfoEvent", this.rightMethod.name, this.rightMethod.desc, false));
								iterator.next();
								iterator.next();
							}
							else
								iterator.previous();
					}
				}
			}
	        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
	        node.accept(writer);
	        return writer.toByteArray();
		}
		return baseClass;
	}
}
