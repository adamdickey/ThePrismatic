package prismaticmod.powers;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static prismaticmod.BasicMod.makeID;

public class ScryNextTurnPower extends BasePower {

    public static final String ID = makeID("Next Turn Scry");

    public ScryNextTurnPower(int amount) {
        super(ID, PowerType.BUFF, true, player, player, amount, true);
        this.amount = amount;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount >= 999)
            this.amount = 999;
    }

    public void atStartOfTurn() {
        flash();
        addToBot(new ScryAction(this.amount));
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }
}