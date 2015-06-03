package net.technicpack.blightcore.coremod.asm;

import static org.objectweb.asm.Opcodes.*;

import net.minecraft.block.material.Material;
import org.objectweb.asm.tree.*;

public class BlockTaintedSandEditor implements IAsmEditor {
    @Override
    public void edit(MethodNode method) {
        //Find the instruction where we reference Material.sand, we'll get inside
        //the if scope immediately after that and then inject the wipeout code
        int sandRef = -1;
        for (int i = 0; i < method.instructions.size(); i++) {
            AbstractInsnNode insn = method.instructions.get(i);

            if (insn.getOpcode() == GETSTATIC && insn instanceof FieldInsnNode) {
                FieldInsnNode fieldInsn = (FieldInsnNode)insn;

                if (fieldInsn.name.equals("sand") && fieldInsn.owner.equals("net/minecraft/block/material/Material")) {
                    sandRef = i;
                    break;
                }
            }
        }

        if (sandRef < 0)
            return;

        for (int i = sandRef+1; i < method.instructions.size(); i++) {
            AbstractInsnNode instruction = method.instructions.get(i);

            if (instruction instanceof FrameNode) {
                AbstractInsnNode nextInstruction = method.instructions.get(i+1);
                method.instructions.insertBefore(nextInstruction, new VarInsnNode(ALOAD, 12));
                method.instructions.insertBefore(nextInstruction, new FieldInsnNode(GETSTATIC, "net/minecraft/block/material/Material", "sand", "Lnet/minecraft/block/material/Material;"));

                LabelNode returnToMethod = new LabelNode();
                method.instructions.insertBefore(nextInstruction, new JumpInsnNode(IF_ACMPNE, returnToMethod));
                method.instructions.insertBefore(nextInstruction, new InsnNode(RETURN));
                method.instructions.insertBefore(nextInstruction, returnToMethod);
                return;
            }
        }
    }
}
