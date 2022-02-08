package immersive_armors.armorDamageEffects;

import java.util.List;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class FireResistanceArmorEffect extends ArmorEffect {
    private final float strength;

    public FireResistanceArmorEffect(float strength) {
        this.strength = strength;
    }

    @Override
    public float applyArmorToDamage(DamageSource source, float amount, ItemStack armor) {
        if (source.isFire()) {
            return amount * (1.0f - strength);
        } else {
            return amount;
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, tooltip, context);

        tooltip.add(new TranslatableText("damageEffect.fireResistance", strength * 100).formatted(Formatting.RED));
    }

    @Override
    public void equippedTick(ItemStack stack, World world, LivingEntity entity, int slot) {
        if (world.isClient) {
            if (entity.getRandom().nextInt(200) == 0 && !entity.isSilent()) {
                world.playSound(entity.getX() + 0.5D, entity.getY() + 0.5D, entity.getZ() + 0.5D,
                        SoundEvents.ENTITY_BLAZE_BURN, entity.getSoundCategory(), 0.5F + entity.getRandom().nextFloat() * 0.5F, entity.getRandom().nextFloat() * 0.7F + 0.3F, false);
            }

            if (entity.getRandom().nextInt(20) == 0) {
                world.addParticle(ParticleTypes.FLAME, entity.getParticleX(0.5D), entity.getRandomBodyY(), entity.getParticleZ(0.5D), 0.0D, 0.0D, 0.0D);
            }
        }
    }
}
