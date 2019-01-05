package pyre.spawnpreventingbeacon.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BeaconPositionWorldSaveData extends WorldSavedData {

    private static final String DATA_NAME = Reference.MOD_ID + "_BeaconPosition";

    private Map<Long, BlockPos> beaconPositions = new HashMap<>();

    public BeaconPositionWorldSaveData() {
        super(DATA_NAME);
    }

    public BeaconPositionWorldSaveData(String name) {
        super(name);
    }

    public static BeaconPositionWorldSaveData get(World world) {
        MapStorage storage = world.getPerWorldStorage();
        BeaconPositionWorldSaveData instance = (BeaconPositionWorldSaveData) storage.getOrLoadData(BeaconPositionWorldSaveData.class, DATA_NAME);

        if (instance == null) {
            instance = new BeaconPositionWorldSaveData();
            storage.setData(DATA_NAME, instance);
        }
        return instance;
    }

    public void addBeaconPosition(BlockPos pos) {
        beaconPositions.put(pos.toLong(), pos);
        markDirty();
    }

    public void removeBeaconPosition(BlockPos pos) {
        if (beaconPositions.containsKey(pos.toLong())) {
            beaconPositions.remove(pos.toLong());
            markDirty();
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        Set<String> keySet = nbt.getKeySet();
        keySet.forEach(k -> {
            BlockPos posFromTag = NBTUtil.getPosFromTag(nbt.getCompoundTag(k));
            beaconPositions.put(Long.parseLong(k), posFromTag);
        });
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        beaconPositions.forEach((k, v) -> {
            NBTTagCompound posTag = NBTUtil.createPosTag(v);
            compound.setTag(k.toString(), posTag);
        });
        return compound;
    }
}
