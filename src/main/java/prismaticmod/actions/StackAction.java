package prismaticmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class StackAction extends AbstractGameAction {
    private final AbstractCard card;

    public StackAction(AbstractCard card) {
        this.card = card;
    }

    public void update() {
        tickDuration();
        if (this.isDone){
            if(card.upgraded){
                addToBot(new GainBlockAction(player, player.discardPile.size()+3));
            } else{
                addToBot(new GainBlockAction(player, player.discardPile.size()));
            }
        }
    }
}
