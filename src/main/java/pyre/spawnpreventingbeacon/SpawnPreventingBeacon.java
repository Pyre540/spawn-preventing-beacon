package pyre.spawnpreventingbeacon;

import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.apache.logging.log4j.Logger;
import pyre.spawnpreventingbeacon.init.ModPotions;
import pyre.spawnpreventingbeacon.util.Reference;

import java.lang.reflect.Field;
import java.util.Set;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class SpawnPreventingBeacon {
    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        addBeaconEffect();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
    }

    @SuppressWarnings("unchecked")
    private void addBeaconEffect() {
        TileEntityBeacon.EFFECTS_LIST[0] = new Potion[]{MobEffects.SPEED, MobEffects.HASTE, ModPotions.SPAWN_PREVENTING};
        try {
            Field tileBeacon = ReflectionHelper.findField(TileEntityBeacon.class, "VALID_EFFECTS", "field_184280_f");
            Set<Potion> validEffects = (Set<Potion>)tileBeacon.get(null);
            validEffects.add(ModPotions.SPAWN_PREVENTING);
        } catch (IllegalAccessException e) {
            SpawnPreventingBeacon.logger.error("Cannot get 'tileBeacon' value for CustomBeaconGUI.", e);
            throw new RuntimeException("Cannot get 'tileBeacon' value for CustomBeaconGUI.", e);
        }
    }
}