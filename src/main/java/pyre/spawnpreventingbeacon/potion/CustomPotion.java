package pyre.spawnpreventingbeacon.potion;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pyre.spawnpreventingbeacon.util.Reference;

import java.util.Objects;

public class CustomPotion extends Potion {

    private final ResourceLocation iconTexture;

    public CustomPotion(String name, boolean isBadEffectIn, int liquidColorIn) {
        super(isBadEffectIn, liquidColorIn);
        setPotionName(this, name);
        iconTexture = new ResourceLocation(Reference.MOD_ID, "textures/potions/" + name + ".png");
    }

    public static void setPotionName(final Potion potion, final String potionName) {
        potion.setRegistryName(Reference.MOD_ID, potionName);
        final ResourceLocation registryName = Objects.requireNonNull(potion.getRegistryName());
        potion.setPotionName("effect." + registryName.toString());
    }

    @Override
    public boolean hasStatusIcon() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(iconTexture);
        Gui.drawModalRectWithCustomSizedTexture(x + 6, y + 7, 0, 0, 18, 18, 18, 18);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha) {
        mc.getTextureManager().bindTexture(iconTexture);
        Gui.drawModalRectWithCustomSizedTexture(x + 3, y + 3, 0, 0, 18, 18, 18, 18);
    }

    public ResourceLocation getIconTexture() {
        return iconTexture;
    }
}
