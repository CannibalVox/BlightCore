package net.technicpack.blightcore.coremod.asm;

import static org.objectweb.asm.Opcodes.*;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class DeathsReduceRepEditor implements IAsmEditor {

    public DeathsReduceRepEditor() {}

    @Override
    public void edit(MethodNode method) {
        AbstractInsnNode firstInstruction = method.instructions.getFirst();
        method.instructions.insertBefore(firstInstruction, new VarInsnNode(ALOAD, 0));
        MethodInsnNode methodInstruction = new MethodInsnNode(INVOKESTATIC, "net/technicpack/blightcore/support/HQMAsmSupport", "removeTenRep", "(Lhardcorequesting/QuestingData;)V");
        method.instructions.insertBefore(firstInstruction, methodInstruction);
    }

    @Override
    public String getClassName() {
        return "hardcorequesting.QuestingData";
    }

    @Override
    public String getMethodName() {
        return "die";
    }

    @Override
    public String getMethodDesc() {
        return "(Lnet/minecraft/entity/player/EntityPlayer;)V";
    }
}
