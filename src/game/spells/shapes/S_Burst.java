package game.spells.shapes;

import static engine.Behavior.track;
import game.creatures.Creature;
import game.spells.SpellInfo;
import game.spells.TypeDefinitions.SpellShapeModifier;
import java.util.Collection;
import java.util.LinkedList;

public class S_Burst extends SpellShapeModifier {

    private static final Collection<Creature> ALL_CREATURES = track(Creature.class);

    @Override
    public void cast(SpellInfo info) {
        if (info.target.targetsCreature) {
            for (Creature c : new LinkedList<>(ALL_CREATURES)) {
                if (c.position.position.sub(info.position()).length() < 6) {
                    hit(info.setTarget(c).multiplyPower(.5));
                }
            }
        } else {
            throw new RuntimeException("Not implemented yet");
        }
    }
}