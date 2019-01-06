package pyre.spawnpreventingbeacon.config;

import net.minecraftforge.common.config.Config;
import pyre.spawnpreventingbeacon.util.Reference;

@Config(modid = Reference.MOD_ID)
public class ModConfig {

    @Config.LangKey("spawnpreventingbeacon.config.spawn_prevented_mobs.list")
    @Config.Comment("List of mobs that should not be able to spawn.")
    public static String[] preventedMobs = {"minecraft:blaze",
            "minecraft:creeper",
            "minecraft:enderman",
            "minecraft:endermite",
            "minecraft:ghast",
            "minecraft:guardian",
            "minecraft:husk",
            "minecraft:magma_cube",
            "minecraft:silverfish",
            "minecraft:skeleton",
            "minecraft:slime",
            "minecraft:spider",
            "minecraft:stray",
            "minecraft:witch",
            "minecraft:wither_skeleton",
            "minecraft:zombie",
            "minecraft:zombie_villager"};
}
