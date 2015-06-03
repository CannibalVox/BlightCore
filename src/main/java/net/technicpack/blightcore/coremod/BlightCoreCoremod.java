package net.technicpack.blightcore.coremod;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

public class BlightCoreCoremod implements IFMLLoadingPlugin {
    @Override
    public String[] getASMTransformerClass() {
        return new String[] { "net.technicpack.blightcore.coremod.BlightCoreClassTransformer" };
    }

    @Override
    public String getModContainerClass() {
        return "net.technicpack.blightcore.BlightCore";
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return "net.technicpack.blightcore.coremod.BlightCoreAccessTransformer";
    }
}
