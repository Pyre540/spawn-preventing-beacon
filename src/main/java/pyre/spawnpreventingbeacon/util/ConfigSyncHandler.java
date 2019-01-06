package pyre.spawnpreventingbeacon.util;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pyre.spawnpreventingbeacon.config.CurrentModConfig;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ConfigSyncHandler {

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(Reference.MOD_ID)) {
            ConfigManager.sync(Reference.MOD_ID, Config.Type.INSTANCE);
            if (Minecraft.getMinecraft().isSingleplayer() || Minecraft.getMinecraft().world == null) {
                CurrentModConfig.useClientConfig();
            }
        }
    }
}
