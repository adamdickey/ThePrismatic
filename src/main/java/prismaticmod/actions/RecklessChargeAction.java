package prismaticmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class RecklessChargeAction extends AbstractGameAction {
    private final AbstractMonster m;

    public RecklessChargeAction(AbstractMonster m) {
        this.m = m;
    }

    public void update() {
        int debuffs = 0;
        for(AbstractPower power : m.powers){
            if(power.type == AbstractPower.PowerType.DEBUFF && !power.ID.equals("Shackled")){
                debuffs++;
            }
        }
        if(debuffs < 2){
            addToBot(new MakeTempCardInDrawPileAction(new Dazed(), 1, true, true));
        }
        this.isDone = true;
    }
}
