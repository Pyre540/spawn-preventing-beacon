package pyre.spawnpreventingbeacon.util;

import net.minecraft.client.gui.inventory.GuiBeacon;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import pyre.spawnpreventingbeacon.SpawnPreventingBeacon;
import pyre.spawnpreventingbeacon.gui.CustomBeaconGUI;
import pyre.spawnpreventingbeacon.init.ModPotions;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber
public class EventHandler {

    @SubscribeEvent
    public static void swapBeaconGUI(GuiOpenEvent event) {
        if (event.getGui() instanceof GuiBeacon) {
            GuiBeacon gui = (GuiBeacon) event.getGui();
            event.setGui(new CustomBeaconGUI(gui.inventorySlots, getTileBeacon(gui)));
        }
    }

    @SubscribeEvent
    public static void saveBeaconPosition(BlockEvent.PlaceEvent event) {
        if (event.getPlacedBlock().getBlock() == Blocks.BEACON) {
            BeaconPositionWorldSaveData.get(event.getWorld()).addBeaconPosition(event.getPos());
        }
    }

    @SubscribeEvent
    public static void removeBeaconPosition(BlockEvent.BreakEvent event) {
        if (event.getState().getBlock() == Blocks.BEACON) {
            BeaconPositionWorldSaveData.get(event.getWorld()).removeBeaconPosition(event.getPos());
        }
    }

    @SubscribeEvent
    public static void preventMobSpawn(LivingSpawnEvent.CheckSpawn event) {
        if (event.getSpawner() == null) {
            World world = event.getWorld();
            BlockPos mobPos = new BlockPos(event.getEntity());
            boolean preventSpawn = shouldPreventSpawn(world, mobPos);
            if (preventSpawn) {
                event.setResult(Event.Result.DENY);
            }
        }
    }

    private static boolean shouldPreventSpawn(World world, BlockPos mobPos) {
        BeaconPositionWorldSaveData beaconPositionWorldSaveData = BeaconPositionWorldSaveData.get(world);
        return beaconPositionWorldSaveData.getBeaconPositions().entrySet().stream().anyMatch(e -> {
            TileEntity tileEntity = world.getTileEntity(e.getValue());
            if (tileEntity instanceof TileEntityBeacon) {
                TileEntityBeacon te = (TileEntityBeacon) tileEntity;
                int primaryEffectId = te.getField(1);
                if (Potion.getPotionById(primaryEffectId) == ModPotions.SPAWN_PREVENTING) {
                    int level = te.getField(0);
                    int beaconRange = level * 10 + 10;
                    return isMobInRange(mobPos, e.getValue(), beaconRange, world.getHeight());
                }
            } else {
                beaconPositionWorldSaveData.removeBeaconPosition(e.getValue());
            }
            return false;
        });
    }

    private static boolean isMobInRange(BlockPos mobPos, BlockPos beaconPos, int beaconRange, int worldHeight) {
        return mobPos.getX() <= beaconPos.getX() + beaconRange &&
                mobPos.getX() >= beaconPos.getX() - beaconRange &&
                mobPos.getZ() <= beaconPos.getZ() + beaconRange &&
                mobPos.getZ() >= beaconPos.getZ() - beaconRange &&
                mobPos.getY() <= beaconPos.getY() + beaconRange + worldHeight &&
                mobPos.getY() >= beaconPos.getY() - beaconRange;
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
