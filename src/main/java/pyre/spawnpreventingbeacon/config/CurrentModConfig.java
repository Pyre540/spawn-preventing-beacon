package pyre.spawnpreventingbeacon.config;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.List;

public class CurrentModConfig {

    public static List<String> preventedMobs = Arrays.asList(ModConfig.preventedMobs);

    public static void setPreventedMobs(List<String> mobList) {
        preventedMobs = mobList;
    }

    @SideOnly(Side.CLIENT)
    public static void useClientConfig() {
        setPreventedMobs(Arrays.asList(ModConfig.preventedMobs));
    }
}
