package net.technicpack.blightcore.coremod.asm;

import static org.objectweb.asm.Opcodes.*;

import org.objectweb.asm.tree.*;

public class BlockTaintedBotaniaFlowerEditor implements IAsmEditor {
    @Override
    public void edit(MethodNode method) {
        AbstractInsnNode beforeNode = null;
        for (int i = 0; i < method.instructions.size(); i++) {
            AbstractInsnNode testInsn = method.instructions.get(i);

            if (testInsn.getOpcode() == ASTORE) {
                beforeNode = testInsn.getNext();
                break;
            }
        }

        if (beforeNode == null)
            throw new RuntimeException("BlightCore failed to find an injection point to block the tainting of botania flowers.");

        method.instructions.insertBefore(beforeNode, new VarInsnNode(ALOAD, 4));
        method.instructions.insertBefore(beforeNode, new TypeInsnNode(INSTANCEOF, "vazkii/botania/common/block/BlockSpecialFlower"));

        LabelNode outLabel = new LabelNode();
        method.instructions.insertBefore(beforeNode, new JumpInsnNode(IFEQ, outLabel));
        method.instructions.insertBefore(beforeNode, new InsnNode(ICONST_0));
        method.instructions.insertBefore(beforeNode, new InsnNode(IRETURN));
        method.instructions.insertBefore(beforeNode, outLabel);

        method.instructions.insertBefore(beforeNode, new VarInsnNode(ALOAD, 4));
        method.instructions.insertBefore(beforeNode, new TypeInsnNode(INSTANCEOF, "vazkii/botania/common/block/BlockModFlower"));

        outLabel = new LabelNode();
        method.instructions.insertBefore(beforeNode, new JumpInsnNode(IFEQ, outLabel));
        method.instructions.insertBefore(beforeNode, new InsnNode(ICONST_0));
        method.instructions.insertBefore(beforeNode, new InsnNode(IRETURN));
        method.instructions.insertBefore(beforeNode, outLabel);
    }

    @Override
    public String getClassName() {
        return "thaumcraft.common.blocks.BlockTaintFibres";
    }

    @Override
    public String getMethodName() {
        return "spreadFibres";
    }

    @Override
    public String getMethodDesc() {
        return "(Lnet/minecraft/world/World;III)Z";
    }
}
