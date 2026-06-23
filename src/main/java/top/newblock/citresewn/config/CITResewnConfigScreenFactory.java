package top.newblock.citresewn.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/**
 * Cloth Config integration to CIT Resewn's config
 * @see CITResewnConfig
 */
public class CITResewnConfigScreenFactory {
    /**
     * Creates a Cloth Config screen for the current active config instance.
     * @param parent parent to return to from the config screen
     * @return the config screen
     */
    public static Screen create(Screen parent) {
        CITResewnConfig currentConfig = CITResewnConfig.INSTANCE, defaultConfig = new CITResewnConfig();

        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.translatable("config.citresewn.title"))
                .setSavingRunnable(currentConfig::write);

        ConfigCategory category = builder.getOrCreateCategory(Component.empty());
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        category.addEntry(entryBuilder.startBooleanToggle(Component.translatable("config.citresewn.enabled.title"), currentConfig.enabled)
                .setTooltip(Component.translatable("config.citresewn.enabled.tooltip"))
                .setSaveConsumer(newConfig -> {
                    if (currentConfig.enabled != newConfig) {
                        currentConfig.enabled = newConfig;
                        Minecraft.getInstance().reloadResourcePacks();
                    }
                })
                .setDefaultValue(defaultConfig.enabled)
                .build());

        category.addEntry(entryBuilder.startBooleanToggle(Component.translatable("config.citresewn.mute_errors.title"), currentConfig.mute_errors)
                .setTooltip(Component.translatable("config.citresewn.mute_errors.tooltip"))
                .setSaveConsumer(newConfig -> currentConfig.mute_errors = newConfig)
                .setDefaultValue(defaultConfig.mute_errors)
                .build());

        category.addEntry(entryBuilder.startBooleanToggle(Component.translatable("config.citresewn.mute_warns.title"), currentConfig.mute_warns)
                .setTooltip(Component.translatable("config.citresewn.mute_warns.tooltip"))
                .setSaveConsumer(newConfig -> currentConfig.mute_warns = newConfig)
                .setDefaultValue(defaultConfig.mute_warns)
                .build());

        category.addEntry(entryBuilder.startIntSlider(Component.translatable("config.citresewn.cache_ms.title"), currentConfig.cache_ms / 50, 0, 5 * 20)
                .setTooltip(Component.translatable("config.citresewn.cache_ms.tooltip"))
                .setSaveConsumer(newConfig -> currentConfig.cache_ms = newConfig * 50)
                .setDefaultValue(defaultConfig.cache_ms / 50)
                .setTextGetter(ticks -> {
                    if (ticks <= 1)
                        return Component.translatable("config.citresewn.cache_ms.ticks." + ticks).withStyle(ChatFormatting.AQUA);

                    ChatFormatting color = ChatFormatting.DARK_RED;

                    if (ticks <= 40) color = ChatFormatting.RED;
                    if (ticks <= 20) color = ChatFormatting.GOLD;
                    if (ticks <= 10) color = ChatFormatting.DARK_GREEN;
                    if (ticks <= 5) color = ChatFormatting.GREEN;

                    return Component.translatable("config.citresewn.cache_ms.ticks.any", ticks).withStyle(color);
                })
                .build());

        category.addEntry(entryBuilder.startBooleanToggle(Component.translatable("config.citresewn.broken_paths.title"), currentConfig.broken_paths)
                .setTooltip(Component.translatable("config.citresewn.broken_paths.tooltip"))
                .setSaveConsumer(newConfig -> currentConfig.broken_paths = newConfig)
                .setDefaultValue(defaultConfig.broken_paths)
                .requireRestart()
                .build());

        category.addEntry(entryBuilder.startFloatField(Component.translatable("config.citresewn.enchantment_scroll_multiplier.title"), currentConfig.type_enchantment_scroll_multiplier)
                .setTooltip(Component.translatable("config.citresewn.enchantment_scroll_multiplier.tooltip"))
                .setSaveConsumer(newConfig -> currentConfig.type_enchantment_scroll_multiplier = newConfig)
                .setDefaultValue(defaultConfig.type_enchantment_scroll_multiplier)
                .build());

        return builder.build();
    }
}
