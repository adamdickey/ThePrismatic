package prismaticmod.powers;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.*;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static prismaticmod.BasicMod.makeID;

public class OnARollPower extends BasePower{

    public static final String ID = makeID("On A Roll");

    public OnARollPower(int amount) {
        super(ID, PowerType.BUFF, false, player, player, 1, true);
        this.amount = amount;
    }
    public void updateDescription() {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] +
                    this.amount + DESCRIPTIONS[2];
    }
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(card.type == AbstractCard.CardType.ATTACK){
            addToBot((AbstractGameAction)new ApplyPowerAction(this.owner, this.owner, (AbstractPower)new StrengthPower(this.owner, this.amount), this.amount));
            addToBot((AbstractGameAction)new ApplyPowerAction(this.owner, this.owner, (AbstractPower)new LoseStrengthPower(this.owner, this.amount), this.amount));
            addToBot((AbstractGameAction)new ApplyPowerAction(this.owner, this.owner, (AbstractPower)new DexterityPower(this.owner, this.amount), this.amount));
            addToBot((AbstractGameAction)new ApplyPowerAction(this.owner, this.owner, (AbstractPower)new LoseDexterityPower(this.owner, this.amount), this.amount));
        }
    }
}
