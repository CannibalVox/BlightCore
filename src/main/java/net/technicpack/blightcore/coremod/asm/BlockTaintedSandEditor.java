package net.technicpack.blightcore.coremod.asm;

import static org.objectweb.asm.Opcodes.*;

import net.minecraft.block.material.Material;
import org.objectweb.asm.tree.*;

public class BlockTaintedSandEditor implements IAsmEditor {
    private boolean isObfuscated;

    public BlockTaintedSandEditor(boolean isObfuscated) {
        this.isObfuscated = isObfuscated;
    }

    @Override
    public void edit(MethodNode method) {

        String sandField = getCorrectSymbol("sand", "field_151595_p");

        //Find the instruction where we reference Material.sand, we'll get inside
        //the if scope immediately after that and then inject the wipeout code
        int sandRef = -1;
        for (int i = 0; i < method.instructions.size(); i++) {
            AbstractInsnNode insn = method.instructions.get(i);

            if (insn.getOpcode() == GETSTATIC && insn instanceof FieldInsnNode) {
                FieldInsnNode fieldInsn = (FieldInsnNode)insn;

                if (fieldInsn.name.equals(sandField) && fieldInsn.owner.equals("net/minecraft/block/material/Material")) {
                    sandRef = i;
                    break;
                }
            }
        }

        if (sandRef < 0)
            throw new RuntimeException("BlightCore failed to find a reference to the field "+ sandField+" when blocking tainted sand.");

        for (int i = sandRef+1; i < method.instructions.size(); i++) {
            AbstractInsnNode instruction = method.instructions.get(i);

            if (instruction instanceof FrameNode) {
                AbstractInsnNode nextInstruction = method.instructions.get(i+1);
                method.instructions.insertBefore(nextInstruction, new VarInsnNode(ALOAD, 12));
                method.instructions.insertBefore(nextInstruction, new FieldInsnNode(GETSTATIC, "net/minecraft/block/material/Material", sandField, "Lnet/minecraft/block/material/Material;"));

                LabelNode returnToMethod = new LabelNode();
                method.instructions.insertBefore(nextInstruction, new JumpInsnNode(IF_ACMPNE, returnToMethod));
                method.instructions.insertBefore(nextInstruction, new InsnNode(RETURN));
                method.instructions.insertBefore(nextInstruction, returnToMethod);
                return;
            }
        }
    }

    @Override
    public String getClassName() {
        return "thaumcraft.common.blocks.BlockTaintFibres";
    }

    @Override
    public String getMethodName() {
        return getCorrectSymbol("updateTick", "func_149674_a");
    }

    @Override
    public String getMethodDesc() {
        return "(Lnet/minecraft/world/World;IIILjava/util/Random;)V";
    }

    private String getCorrectSymbol(String deobfuscated, String obfuscated) {
        return isObfuscated?obfuscated:deobfuscated;
    }
}
