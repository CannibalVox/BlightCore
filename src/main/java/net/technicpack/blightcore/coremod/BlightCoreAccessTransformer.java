package net.technicpack.blightcore.coremod;

import cpw.mods.fml.common.asm.transformers.AccessTransformer;

import java.io.IOException;

public class BlightCoreAccessTransformer extends AccessTransformer {
    public BlightCoreAccessTransformer() throws IOException {
        super("blightcore_at.cfg");
    }
}
