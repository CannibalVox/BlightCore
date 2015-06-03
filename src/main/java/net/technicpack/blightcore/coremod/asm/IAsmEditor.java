package net.technicpack.blightcore.coremod.asm;

import org.objectweb.asm.tree.MethodNode;

public interface IAsmEditor {
    void edit(MethodNode method);
}
