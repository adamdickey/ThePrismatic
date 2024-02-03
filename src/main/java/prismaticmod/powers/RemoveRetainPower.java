package prismaticmod.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.powers.RetainCardPower;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static prismaticmod.BasicMod.makeID;

public class RemoveRetainPower extends BasePower{

    public static final String ID = makeID("Lose Retain");

    public RemoveRetainPower(int amount) {
        super(ID, PowerType.DEBUFF, true, player, player, amount, true);
    }
    public void atEndOfRound() {
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, RetainCardPower.POWER_ID));
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        }
    }
}
