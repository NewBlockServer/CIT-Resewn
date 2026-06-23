package top.newblock.citresewn.defaults.mixin.types.enchantment;

import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import top.newblock.citresewn.cit.CITCache;
import top.newblock.citresewn.defaults.cit.types.TypeEnchantment;

@Mixin(ItemStack.class)
public class ItemStackMixin implements TypeEnchantment.CITCacheEnchantment {
    private final CITCache.MultiList<TypeEnchantment> citresewn$cacheTypeEnchantment = new CITCache.MultiList<>(TypeEnchantment.CONTAINER::getRealTimeCITs);

    @Override
    public CITCache.MultiList<TypeEnchantment> citresewn$getCacheTypeEnchantment() {
        return this.citresewn$cacheTypeEnchantment;
    }
}
