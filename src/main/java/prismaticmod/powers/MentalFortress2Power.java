package prismaticmod.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.evacipated.cardcrawl.mod.stslib.patches.bothInterfaces.OnCreateCardInterface;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static prismaticmod.BasicMod.makeID;

public class MentalFortress2Power extends BasePower implements OnCreateCardInterface {

    public static final String ID = makeID("Mental Fortress");
    public MentalFortress2Power(int amount) {
        super(ID, PowerType.BUFF, false, player, player, amount, true);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
    boolean playerTurn = true;

    public void atStartOfTurn(){
        playerTurn = true;
    }

    public void atEndOfTurnPreEndTurnCards(boolean isPlayer){
        playerTurn = false;
    }

    public void onChangeStance(AbstractStance oldStance, AbstractStance newStance) {
        if (!oldStance.ID.equals(newStance.ID)) {
            flash();
            addToBot(new GainBlockAction(this.owner, this.owner, this.amount));
        }
    }

    @Override
    public void onCreateCard(AbstractCard abstractCard) {
        if(playerTurn){
            flash();
            addToBot(new GainBlockAction(this.owner, this.owner, this.amount));
        }
    }
}