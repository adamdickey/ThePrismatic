package prismaticmod.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.stances.AbstractStance;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static prismaticmod.BasicMod.makeID;

public class MentalFortress2Power extends BasePower {

    public static final String ID = makeID("Mental Fortress");
    public MentalFortress2Power(int amount) {
        super(ID, PowerType.BUFF, false, player, player, amount, true);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void onChangeStance(AbstractStance oldStance, AbstractStance newStance) {
        if (!oldStance.ID.equals(newStance.ID)) {
            flash();
            addToBot(new GainBlockAction(this.owner, this.owner, this.amount));
        }
    }

    public void onUseCard(AbstractCard card, UseCardAction action){
        if (card.type == AbstractCard.CardType.POWER && this.amount > 0) {
            flash();
            addToBot(new GainBlockAction(this.owner, this.owner, this.amount));
        }
    }
}