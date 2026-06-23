package top.newblock.citresewn.mixin.broken_paths;

import net.minecraft.server.packs.metadata.pack.PackFormat;
import net.minecraft.server.packs.repository.PackCompatibility;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.newblock.citresewn.config.BrokenPaths;

/**
 * Adds a resourcepack compatibility error message when broken paths are enabled and are detected in a pack.
 * @see BrokenPaths
 * @see AbstractFileResourcePackMixin
 */
@Mixin(PackCompatibility.class)
public abstract class ResourcePackCompatibilityMixin {
    private static final PackCompatibility BROKEN_PATHS = ResourcePackCompatibility("BROKEN_PATHS", -1, "broken_paths");

    @SuppressWarnings("InvokerTarget")
    @Invoker("<init>")
    public static PackCompatibility ResourcePackCompatibility(String internalName, int internalId, String translationSuffix) {
        throw new AssertionError();
    }

    @Inject(method = "forVersion", cancellable = true, at = @At("HEAD"))
    private static void citresewn$brokenpaths$redirectBrokenPathsCompatibility
            (net.minecraft.util.InclusiveRange<PackFormat> range, PackFormat current, CallbackInfoReturnable<PackCompatibility> cir) {
        if (range.minInclusive().major() == Integer.MAX_VALUE - 53)
            cir.setReturnValue(BROKEN_PATHS);
    }
}
