package net.technicpack.blightcore.coremod.asm;

import org.objectweb.asm.tree.*;

import static org.objectweb.asm.Opcodes.*;

public class BlockOceanNodesEditor implements IAsmEditor {
    @Override
    public void edit(MethodNode method) {
        AbstractInsnNode firstInstruction = method.instructions.getFirst();

        method.instructions.insertBefore(firstInstruction, new VarInsnNode(ALOAD, 0));
        method.instructions.insertBefore(firstInstruction, new VarInsnNode(ILOAD, 1));
        method.instructions.insertBefore(firstInstruction, new VarInsnNode(ILOAD, 3));
        MethodInsnNode methodInstruction = new MethodInsnNode(INVOKESTATIC, "net/technicpack/blightcore/support/ThaumcraftAsmSupport", "checkNodeSpawnBiome", "(Lnet/minecraft/world/World;II)Z", false);
        method.instructions.insertBefore(firstInstruction, methodInstruction);

        LabelNode returnToMethod = new LabelNode();
        method.instructions.insertBefore(firstInstruction, new JumpInsnNode(IFEQ, returnToMethod));
        method.instructions.insertBefore(firstInstruction, new InsnNode(RETURN));
        method.instructions.insertBefore(firstInstruction, returnToMethod);
    }
}
