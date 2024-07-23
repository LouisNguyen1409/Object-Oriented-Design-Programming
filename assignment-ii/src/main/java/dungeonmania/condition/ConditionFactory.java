package dungeonmania.condition;

import dungeonmania.entities.Conductor;
import java.util.List;

public class ConditionFactory {
    public static LogicalCondition createLogic(String logicString, List<Conductor> conductor) {
        switch (logicString) {
        case "and":
            return new And(conductor);
        case "or":
            return new Or(conductor);
        case "xor":
            return new Xor(conductor);
        case "co_and":
            return new CoAnd(conductor);
        default:
            return null;
        }
    }
}
