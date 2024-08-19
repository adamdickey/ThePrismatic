package prismaticmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class StackAction extends AbstractGameAction {
    private final int block;
    private final int discardSize;

    public StackAction(int block, int discardSize) {
        this.block = block;
        this.discardSize = discardSize;
    }

    public void update() {
        tickDuration();
        if (this.isDone){
            addToBot(new GainBlockAction(player, block+player.discardPile.size()-discardSize));
        }
    }
}
