package net.technicpack.blightcore.coremod;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import net.technicpack.blightcore.coremod.asm.BlockOceanNodesEditor;
import net.technicpack.blightcore.coremod.asm.BlockTaintedSandEditor;
import net.technicpack.blightcore.coremod.asm.IAsmEditor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BlightCoreCoremod implements IFMLLoadingPlugin {
    public static List<IAsmEditor> editors = new ArrayList<IAsmEditor>(2);

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
        boolean isObfuscated = (Boolean) data.get("runtimeDeobfuscationEnabled");

        editors.clear();
        editors.add(new BlockOceanNodesEditor());
        editors.add(new BlockTaintedSandEditor(isObfuscated));
    }

    @Override
    public String getAccessTransformerClass() {
        return "net.technicpack.blightcore.coremod.BlightCoreAccessTransformer";
    }
}
