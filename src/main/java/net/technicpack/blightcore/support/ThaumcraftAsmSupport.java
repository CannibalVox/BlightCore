package net.technicpack.blightcore.support;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class ThaumcraftAsmSupport {
    public static boolean checkNodeSpawnBiome(World world, int x, int z) {
        BiomeGenBase biome = world.getBiomeGenForCoords(x, z);

        return biome.biomeID == 0;
    }
}
