package top.newblock.citresewn.defaults.cit.conditions;

import top.newblock.citresewn.fletchingtable.api.Entrypoint;
import top.newblock.citresewn.api.CITConditionContainer;
import top.newblock.citresewn.cit.CITCondition;
import top.newblock.citresewn.cit.CITContext;
import top.newblock.citresewn.cit.builtin.conditions.IdentifierCondition;
import top.newblock.citresewn.cit.builtin.conditions.ListCondition;

import java.util.Set;
import net.minecraft.resources.Identifier;

public class ConditionEnchantments extends ListCondition<ConditionEnchantments.EnchantmentCondition> {
    @Entrypoint(CITConditionContainer.ENTRYPOINT)
    public static final CITConditionContainer<ConditionEnchantments> CONTAINER = new CITConditionContainer<>(ConditionEnchantments.class, ConditionEnchantments::new,
            "enchantments", "enchantmentIDs");

    public ConditionEnchantments() {
        super(EnchantmentCondition.class, EnchantmentCondition::new);
    }

    public Identifier[] getEnchantments() {
        Identifier[] enchantments = new Identifier[this.conditions.length];

        for (int i = 0; i < this.conditions.length; i++)
            enchantments[i] = this.conditions[i].getValue(null);

        return enchantments;
    }

    @Override
    public Set<Class<? extends CITCondition>> siblingConditions() {
        return Set.of(ConditionEnchantmentLevels.class);
    }

    protected static class EnchantmentCondition extends IdentifierCondition {
        @Override
        public boolean test(CITContext context) {
            return context.enchantments().containsKey(this.value);
        }

        @Override
        protected Identifier getValue(CITContext context) {
            return this.value;
        }
    }
}
