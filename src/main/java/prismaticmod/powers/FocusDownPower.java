package prismaticmod.powers;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.powers.FocusPower;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static prismaticmod.BasicMod.makeID;

public class FocusDownPower extends BasePower {

    public static final String ID = makeID("Focus Down");

    public FocusDownPower(int amount) {
        super(ID, PowerType.DEBUFF, false, player, player, amount, true);
        this.amount = amount;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    public void atStartOfTurn(){
        addToBot(new ApplyPowerAction(player, player, new FocusPower(player, -this.amount), -this.amount));
        addToBot(new RemoveSpecificPowerAction(player, player, FocusDownPower.ID));
    }
}