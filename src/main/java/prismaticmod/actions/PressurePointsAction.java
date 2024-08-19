package prismaticmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.MarkPower;

public class PressurePointsAction extends AbstractGameAction {
    AbstractCard card;
    public PressurePointsAction(AbstractCard callingCard) {
        this.card = callingCard;
    }

    public void update() {
        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
            if(mo.hasPower(MarkPower.POWER_ID)){
                addToBot(new LoseHPAction(mo, null, mo.getPower(MarkPower.POWER_ID).amount, AbstractGameAction.AttackEffect.FIRE));
            }
        }
        this.isDone = true;
    }
}
