package prismaticmod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static prismaticmod.BasicMod.makeID;

public class EmpoweredPower extends BasePower{

    public static final String ID = makeID("Empowered");

    public EmpoweredPower(int amount) {
        super(ID, PowerType.BUFF, false, player, player, 1, true);
        this.amount = amount;
    }
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
    }
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.POWER) {
            addToBot(new MakeTempCardInHandAction(card, 1));
            addToBot(new ApplyPowerAction(this.owner, this.owner, new EmpoweredPower(-1), -1));
            if (this.amount == 1){
                addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, EmpoweredPower.ID));
            }
        }
    }
}
