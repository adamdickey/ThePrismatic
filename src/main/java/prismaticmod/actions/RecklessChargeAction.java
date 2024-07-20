package prismaticmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class RecklessChargeAction extends AbstractGameAction {
    private final AbstractMonster m;
    private final AbstractCard status;

    public RecklessChargeAction(AbstractMonster m, AbstractCard status) {
        this.m = m;
        this.status = status;
    }

    public void update() {
        int debuffs = 0;
        for(AbstractPower power : m.powers){
            if(power.type == AbstractPower.PowerType.DEBUFF && !power.ID.equals("Shackled")){
                debuffs++;
            }
        }
        if(debuffs < 1){
            addToBot(new MakeTempCardInDrawPileAction(status, 1, true, true));
        }
        this.isDone = true;
    }
}
