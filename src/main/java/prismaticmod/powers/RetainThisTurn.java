package prismaticmod.powers;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.RetainCardsAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static prismaticmod.BasicMod.makeID;

public class RetainThisTurn extends BasePower {

    public static final String ID = makeID("Retain This Turn");

    public RetainThisTurn(int amount) {
        super(ID, PowerType.BUFF, false, player, player, amount, true);
        this.amount = amount;
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer && !AbstractDungeon.player.hand.isEmpty() && !AbstractDungeon.player.hasRelic("Runic Pyramid") &&
                !AbstractDungeon.player.hasPower("Equilibrium")){
            addToBot(new RetainCardsAction(this.owner, this.amount));
        }
        addToBot(new RemoveSpecificPowerAction(player, player, this));
    }
}