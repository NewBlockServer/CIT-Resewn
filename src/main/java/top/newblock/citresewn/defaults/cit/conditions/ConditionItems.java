package top.newblock.citresewn.defaults.cit.conditions;

import top.newblock.citresewn.fletchingtable.api.Entrypoint;
import top.newblock.citresewn.api.CITConditionContainer;
import top.newblock.citresewn.cit.CITContext;
import top.newblock.citresewn.cit.builtin.conditions.IdentifierCondition;
import top.newblock.citresewn.cit.builtin.conditions.ListCondition;
import top.newblock.citresewn.cit.CITParsingException;
import top.newblock.citresewn.pack.format.PropertyGroup;
import top.newblock.citresewn.pack.format.PropertyKey;
import top.newblock.citresewn.pack.format.PropertyValue;

import java.util.LinkedHashSet;
import java.util.Set;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

public class ConditionItems extends ListCondition<ConditionItems.ItemCondition> {
    @Entrypoint(CITConditionContainer.ENTRYPOINT)
    public static final CITConditionContainer<ConditionItems> CONTAINER = new CITConditionContainer<>(ConditionItems.class, ConditionItems::new,
            "items", "matchItems");

    public Item[] items = new Item[0];

    public ConditionItems() {
        super(ItemCondition.class, ItemCondition::new);
    }

    public ConditionItems(Item... items) {
        this();
        this.items = items;
    }

    @Override
    public void load(PropertyKey key, PropertyValue value, PropertyGroup properties) throws CITParsingException {
        super.load(key, value, properties);

        Set<Item> items = new LinkedHashSet<>();

        for (ItemCondition itemCondition : this.conditions)
            items.add(itemCondition.item);

        this.items = items.toArray(new Item[0]);
    }

    @Override
    public boolean test(CITContext context) {
        for (Item item : this.items)
            if (context.stack.getItem() == item)
                return true;

        return false;
    }

    protected static class ItemCondition extends IdentifierCondition {
        public Item item = null;

        @Override
        public void load(PropertyKey key, PropertyValue value, PropertyGroup properties) throws CITParsingException {
            super.load(key, value, properties);

            if (BuiltInRegistries.ITEM.containsKey(this.value))
                this.item = BuiltInRegistries.ITEM.getValue(this.value);
            else {
                this.item = null;
                warn(this.value + " is not in the item registry", value, properties);
            }
        }

        @Override
        protected Identifier getValue(CITContext context) {
            return this.value;
        }
    }
}
