package prismaticmod.powers;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static prismaticmod.BasicMod.makeID;

public class Evolve2Power extends BasePower {

    public static final String ID = makeID("Evolve");

    public Evolve2Power(int amount) {
        super(ID, PowerType.BUFF, false, player, player, amount, true);
        this.amount = amount;
    }

    public void updateDescription() {
        if(this.amount == 1){
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    public void onCardDraw(AbstractCard card) {
        if ((card.type == AbstractCard.CardType.STATUS || card.type == AbstractCard.CardType.CURSE) && !this.owner.hasPower("No Draw")) {
            flash();
            addToBot(new DrawCardAction(this.owner, this.amount));
        }
    }
}