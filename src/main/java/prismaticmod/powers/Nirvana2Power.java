package prismaticmod.powers;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static prismaticmod.BasicMod.makeID;

public class Nirvana2Power extends BasePower {

    public static final String ID = makeID("Nirvana");

    public Nirvana2Power(int amount) {
        super(ID, PowerType.BUFF, false, player, player, amount, true);
        this.amount = amount;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
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
    public void onDrawOrDiscard() {
        if(GameActionManager.totalDiscardedThisTurn > cardsDiscarded && !endOfTurn){
            for(int i = 0; i < GameActionManager.totalDiscardedThisTurn - cardsDiscarded; i++){
                flash();
                addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.amount));
            }
        }
        cardsDiscarded = GameActionManager.totalDiscardedThisTurn;
    }
}