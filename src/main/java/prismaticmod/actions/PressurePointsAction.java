package prismaticmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.MarkPower;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class PressurePointsAction extends AbstractGameAction {
    AbstractCard card;
    public PressurePointsAction(AbstractCard callingCard) {
        this.card = callingCard;
    }

    public void update() {
        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
            if(mo.hasPower(MarkPower.POWER_ID)){
                addToBot(new DamageAction(mo, new DamageInfo(player, mo.getPower(MarkPower.POWER_ID).amount, DamageInfo.DamageType.HP_LOSS)));
            }
        }
        this.isDone = true;
    }
}
