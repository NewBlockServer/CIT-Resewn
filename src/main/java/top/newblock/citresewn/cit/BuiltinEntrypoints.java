package top.newblock.citresewn.cit;

import top.newblock.citresewn.api.CITConditionContainer;
import top.newblock.citresewn.api.CITGlobalProperties;
import top.newblock.citresewn.api.CITTypeContainer;
import top.newblock.citresewn.cit.builtin.conditions.core.FallbackCondition;
import top.newblock.citresewn.cit.builtin.conditions.core.WeightCondition;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

public final class BuiltinEntrypoints {
    private static final String[] DEFAULT_CONDITION_CLASSES = {
            "top.newblock.citresewn.defaults.cit.conditions.ConditionComponents",
            "top.newblock.citresewn.defaults.cit.conditions.ConditionDamage",
            "top.newblock.citresewn.defaults.cit.conditions.ConditionDamageMask",
            "top.newblock.citresewn.defaults.cit.conditions.ConditionEnchantments",
            "top.newblock.citresewn.defaults.cit.conditions.ConditionEnchantmentLevels",
            "top.newblock.citresewn.defaults.cit.conditions.ConditionHand",
            "top.newblock.citresewn.defaults.cit.conditions.ConditionItems",
            "top.newblock.citresewn.defaults.cit.conditions.ConditionStackSize"
    };
    private static final String[] DEFAULT_TYPE_CLASSES = {
            "top.newblock.citresewn.defaults.cit.types.TypeArmor",
            "top.newblock.citresewn.defaults.cit.types.TypeElytra",
            "top.newblock.citresewn.defaults.cit.types.TypeEnchantment",
            "top.newblock.citresewn.defaults.cit.types.TypeItem"
    };

    private BuiltinEntrypoints() {
    }

    public static List<CITConditionContainer<?>> conditionContainers() {
        LinkedHashSet<CITConditionContainer<?>> containers = new LinkedHashSet<>();
        containers.add(FallbackCondition.CONTAINER);
        containers.add(WeightCondition.CONTAINER);
        addConditionContainers(containers, DEFAULT_CONDITION_CLASSES);
        return new ArrayList<>(containers);
    }

    public static List<CITTypeContainer<?>> typeContainers() {
        LinkedHashSet<CITTypeContainer<?>> containers = new LinkedHashSet<>();
        addTypeContainers(containers, DEFAULT_TYPE_CLASSES);
        return new ArrayList<>(containers);
    }

    public static List<CITGlobalProperties> globalProperties() {
        LinkedHashSet<CITGlobalProperties> handlers = new LinkedHashSet<>();
        handlers.add(FallbackCondition::globalProperty);
        addGlobalHandlers(handlers, new String[] {
                "top.newblock.citresewn.defaults.cit.types.TypeEnchantment"
        });
        return new ArrayList<>(handlers);
    }

    private static void addConditionContainers(Collection<CITConditionContainer<?>> target, String[] classNames) {
        for (String className : classNames) {
            CITConditionContainer<?> container = getStaticContainer(className, CITConditionContainer.class);
            if (container != null)
                target.add(container);
        }
    }

    private static void addTypeContainers(Collection<CITTypeContainer<?>> target, String[] classNames) {
        for (String className : classNames) {
            CITTypeContainer<?> container = getStaticContainer(className, CITTypeContainer.class);
            if (container != null)
                target.add(container);
        }
    }

    private static void addGlobalHandlers(Collection<CITGlobalProperties> target, String[] classNames) {
        for (String className : classNames) {
            CITGlobalProperties container = getStaticContainer(className, CITGlobalProperties.class);
            if (container != null)
                target.add(container);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T getStaticContainer(String className, Class<T> expectedType) {
        try {
            Class<?> clazz = Class.forName(className, true, BuiltinEntrypoints.class.getClassLoader());
            Field field = clazz.getField("CONTAINER");
            Object value = field.get(null);
            return expectedType.isInstance(value) ? (T) value : null;
        } catch (ReflectiveOperationException ignored) {
            return null;
        }
    }
}
