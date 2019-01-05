package pyre.spawnpreventingbeacon.util;

import net.minecraft.client.gui.inventory.GuiBeacon;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import pyre.spawnpreventingbeacon.SpawnPreventingBeacon;
import pyre.spawnpreventingbeacon.gui.CustomBeaconGUI;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber
public class EventHandler {

    @SubscribeEvent
    public static void swapBeaconGUI(GuiOpenEvent event) {
        if (event.getGui() instanceof GuiBeacon) {
            GuiBeacon gui = (GuiBeacon)event.getGui();
            event.setGui(new CustomBeaconGUI(gui.inventorySlots, getTileBeacon(gui)));
        }
    }

    private static IInventory getTileBeacon(GuiBeacon gui) {
        try {
            Field tileBeacon = ReflectionHelper.findField(gui.getClass(), "tileBeacon", "field_147024_w");
            return (IInventory) tileBeacon.get(gui);
        } catch (IllegalAccessException e) {
            SpawnPreventingBeacon.logger.error("Cannot get 'tileBeacon' value for CustomBeaconGUI.", e);
            throw new RuntimeException("Cannot get 'tileBeacon' value for CustomBeaconGUI.", e);
        }
    }
}
