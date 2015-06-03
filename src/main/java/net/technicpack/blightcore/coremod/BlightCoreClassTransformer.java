package net.technicpack.blightcore.coremod;

import net.minecraft.launchwrapper.IClassTransformer;
import net.technicpack.blightcore.coremod.asm.BlockOceanNodesEditor;
import net.technicpack.blightcore.coremod.asm.BlockTaintedSandEditor;
import net.technicpack.blightcore.coremod.asm.IAsmEditor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.*;

import static org.objectweb.asm.Opcodes.*;

public class BlightCoreClassTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String s, String s1, byte[] bytes) {
        if (bytes == null)
            return null;

        if (s1.equals("thaumcraft.common.lib.world.ThaumcraftWorldGenerator")) {
            bytes = updateMethod(bytes, "createRandomNodeAt",
                    "(Lnet/minecraft/world/World;IIILjava/util/Random;ZZZ)V",
                    new BlockOceanNodesEditor());
        } else if (s1.equals("thaumcraft.common.blocks.BlockTaintFibres")) {
            bytes = updateMethod(bytes, "updateTick",
                    "(Lnet/minecraft/world/World;IIILjava/util/Random;)V",
                    new BlockTaintedSandEditor());
        }
        return bytes;
    }

    private byte[] updateMethod(byte[] bytes, String name, String desc, IAsmEditor editor) {
        ClassReader reader = new ClassReader(bytes);
        ClassNode node = new ClassNode(ASM5);
        reader.accept(node, 0);

        for (MethodNode method : node.methods) {
            if (method.name.equals(name) && method.desc.equals(desc)) {
                editor.edit(method);
            }
        }

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        node.accept(writer);
        return writer.toByteArray();
    }
}
