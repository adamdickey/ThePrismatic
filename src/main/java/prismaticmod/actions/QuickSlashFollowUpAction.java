package prismaticmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.watcher.FreeAttackPower;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class QuickSlashFollowUpAction extends AbstractGameAction {
    public void update() {
        tickDuration();
        if (this.isDone)
            for (AbstractCard c : DrawCardAction.drawnCards) {
                if (c.costForTurn == 0 || c.freeToPlayOnce || (player.hasPower(FreeAttackPower.POWER_ID) && c.type == AbstractCard.CardType.ATTACK)) {
                    addToBot(new DrawCardAction(player, 1));
                    break;
                }
            }
    }
}
