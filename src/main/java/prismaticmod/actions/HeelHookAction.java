package prismaticmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class HeelHookAction extends AbstractGameAction {

    private final AbstractMonster m;
    private final boolean isUpgraded;

    public HeelHookAction(AbstractMonster m, boolean isUpgraded) {
        this.m = m;
        this.isUpgraded = isUpgraded;
    }

    public void update() {
        int debuffs = 0;
        for(AbstractPower power : m.powers){
            if(power.type == AbstractPower.PowerType.DEBUFF && !power.ID.equals("Shackled")){
                debuffs++;
            }
        }
        if(debuffs >= 2){
            if(this.isUpgraded){
                addToBot(new GainEnergyAction(2));
            } else {
                addToBot(new GainEnergyAction(1));
            }
        }
        this.isDone = true;
    }
}
