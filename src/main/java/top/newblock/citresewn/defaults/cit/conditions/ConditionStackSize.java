package top.newblock.citresewn.defaults.cit.conditions;

import top.newblock.citresewn.fletchingtable.api.Entrypoint;
import top.newblock.citresewn.api.CITConditionContainer;
import top.newblock.citresewn.cit.CITContext;
import top.newblock.citresewn.cit.builtin.conditions.IntegerCondition;

public class ConditionStackSize extends IntegerCondition {
    @Entrypoint(CITConditionContainer.ENTRYPOINT)
    public static final CITConditionContainer<ConditionStackSize> CONTAINER = new CITConditionContainer<>(ConditionStackSize.class, ConditionStackSize::new,
            "stack_size", "stackSize", "amount");

    public ConditionStackSize() {
        super(true, false, false);
    }

    @Override
    protected int getValue(CITContext context) {
        return context.stack.getCount();
    }
}
