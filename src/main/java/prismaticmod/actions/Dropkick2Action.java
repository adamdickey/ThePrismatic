package prismaticmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class Dropkick2Action extends AbstractGameAction {

    private final AbstractMonster m;

    public Dropkick2Action(AbstractMonster m) {
        this.m = m;
    }

    public void update() {
        int debuffs = 0;
        for(AbstractPower power : m.powers){
            if(power.type == AbstractPower.PowerType.DEBUFF && !power.ID.equals("Shackled")){
                debuffs++;
            }
        }
        if(debuffs >= 2){
            addToBot(new DrawCardAction(1));
        }
        this.isDone = true;
    }
}
