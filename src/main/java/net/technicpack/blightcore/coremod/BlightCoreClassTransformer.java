package net.technicpack.blightcore.coremod;

import net.minecraft.launchwrapper.IClassTransformer;
import net.technicpack.blightcore.coremod.asm.BlockOceanNodesEditor;
import net.technicpack.blightcore.coremod.asm.BlockTaintedSandEditor;
import net.technicpack.blightcore.coremod.asm.IAsmEditor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.*;

import java.util.ArrayList;
import java.util.List;

import static org.objectweb.asm.Opcodes.*;

public class BlightCoreClassTransformer implements IClassTransformer {

    public BlightCoreClassTransformer() {
    }

    @Override
    public byte[] transform(String s, String s1, byte[] bytes) {
        if (bytes == null)
            return null;

        for (IAsmEditor editor : BlightCoreCoremod.editors) {
            if (s1.equals(editor.getClassName()))
                bytes = updateMethod(bytes, editor);
        }

        return bytes;
    }

    private byte[] updateMethod(byte[] bytes, IAsmEditor editor) {
        ClassReader reader = new ClassReader(bytes);
        ClassNode node = new ClassNode(ASM5);
        reader.accept(node, 0);

        boolean foundMethod = false;
        for (MethodNode method : node.methods) {
            if (method.name.equals(editor.getMethodName()) && method.desc.equals(editor.getMethodDesc())) {
                foundMethod = true;
                editor.edit(method);
            }
        }

        if (!foundMethod)
            throw new RuntimeException("BlightCore failed to find a method named "+editor.getMethodName()+" in class "+editor.getClassName());

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        node.accept(writer);
        return writer.toByteArray();
    }
}
