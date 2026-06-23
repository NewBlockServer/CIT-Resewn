package top.newblock.citresewn.defaults.mixin.types.item;

import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import top.newblock.citresewn.cit.CITCache;
import top.newblock.citresewn.defaults.cit.types.TypeItem;

@Mixin(ItemStack.class)
public class ItemStackMixin implements TypeItem.CITCacheItem {
    private final CITCache.Single<TypeItem> citresewn$cacheTypeItem = new TypeItem.ItemCITCache();

    @Override
    public CITCache.Single<TypeItem> citresewn$getCacheTypeItem() {
        return this.citresewn$cacheTypeItem;
    }
}
