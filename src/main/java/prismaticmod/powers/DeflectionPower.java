package prismaticmod.powers;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static prismaticmod.BasicMod.makeID;

public class DeflectionPower extends BasePower {

    public static final String ID = makeID("Deflection");

    public DeflectionPower(int amount) {
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

    public float modifyBlock(float blockAmount, AbstractCard card) {
        if ((card.costForTurn == 0 || (card.freeToPlayOnce && card.cost != -1))) {
            return super.modifyBlock(blockAmount + this.amount);
        } else {
            return super.modifyBlock(blockAmount);
        }
    }
}