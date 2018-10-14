/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.spells.effects;

import definitions.BlockType;
import game.combat.statuses.SpeedChanged;
import game.spells.SpellInfo;
import game.spells.SpellPart.SpellEffect;
import game.spells.TypeDefinitions;
import static game.spells.TypeDefinitions.SpellElement.LIGHTNING;

/**
 *
 * @author nikolas
 */
public class LightningCharge extends SpellEffect {
    
    private final double TIME = 10.0;

    @Override
    public void cast(SpellInfo info) {
        if (info.target.targetsCreature()) {
            new SpeedChanged(info.target.creature, TIME, 0.0).start();
        }
        if (info.target.targetsTerrain()) {
            //TODO
            BlockType block = info.world.getBlock(info.target.terrain);
        }
        if (info.target.targetsItem()) {
            //TODO
        }
    }
    
    @Override
    public TypeDefinitions.SpellElement element() {
        return LIGHTNING;
    }
}


