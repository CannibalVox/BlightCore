package net.technicpack.blightcore;

import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.EventBus;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import net.technicpack.blightcore.coremod.BlightCoreResourcePack;

public class BlightCore extends DummyModContainer {
    public static final String MODID = "blightcore";
    public static final String MODNAME = "Blight Core";
    public static final String VERSION = "1.2.0";

    public BlightCore() {
        super(new ModMetadata());

        ModMetadata metadata = getMetadata();
        metadata.modId = BlightCore.MODID;
        metadata.version = BlightCore.VERSION;
        metadata.name = BlightCore.MODNAME;
        metadata.authorList = ImmutableList.of("Cannibalvox");
        metadata.url = "http://www.technicpack.net/";
        metadata.credits = "Developed by Technic";
        metadata.description = "It's a coremod.  For blightfall!";
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller) {
        bus.register(this);
        return true;
    }

    @Override
    public Class<?> getCustomResourcePackClass() { return BlightCoreResourcePack.class; }

}
