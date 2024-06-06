package prismaticmod.powers;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.orbs.Lightning;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static prismaticmod.BasicMod.makeID;

public class Storm2Power extends BasePower {

    public static final String ID = makeID("Storm");

    public Storm2Power(AbstractPlayer p, int amount) {
        super(ID, PowerType.BUFF, false, player, p, amount, true);
        this.amount = amount;
    }

    public void updateDescription() {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    int cardsDiscarded = 0;
    boolean endOfTurn = false;
    @Override
    public void onInitialApplication() {
        cardsDiscarded = GameActionManager.totalDiscardedThisTurn;
    }
    public void atStartOfTurn(){
        cardsDiscarded = 0;
        endOfTurn = false;
    }
    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        endOfTurn = true;
    }
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if(GameActionManager.totalDiscardedThisTurn > cardsDiscarded && !endOfTurn){
            flash();
            for (int i = 0; i < this.amount; i++){
                addToBot(new ChannelAction(new Lightning()));
            }
        }
        cardsDiscarded = GameActionManager.totalDiscardedThisTurn;
    }
    public void atStartOfTurnPostDraw() {
        if(GameActionManager.totalDiscardedThisTurn > cardsDiscarded && !endOfTurn){
            flash();
            for (int i = 0; i < this.amount; i++){
                addToBot(new ChannelAction(new Lightning()));
            }
        }
        cardsDiscarded = GameActionManager.totalDiscardedThisTurn;
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.POWER && this.amount > 0) {
            flash();
            for (int i = 0; i < this.amount; i++)
                addToBot(new ChannelAction(new Lightning()));
        }
    }
}