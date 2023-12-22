package prismaticmod.powers;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static prismaticmod.BasicMod.makeID;

public class ChimeraPower extends BasePower{

    public static final String ID = makeID("Chimera Form");

    public ChimeraPower() {
        super(ID, PowerType.BUFF, false, player, player, 1, true);
        this.amount = amount;
    }
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] +
                    this.amount + DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[3] +
                    this.amount + DESCRIPTIONS[5];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] +
                    this.amount + DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[4] +
                    this.amount + DESCRIPTIONS[5];
        }
    }
    public void atStartOfTurnPostDraw() {
        flash();
        addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, this.amount), this.amount));
        addToBot(new ApplyPowerAction(this.owner, this.owner, new DexterityPower(this.owner, this.amount), this.amount));
        addToBot(new IncreaseMaxOrbAction(this.amount));
        addToBot(new ApplyPowerAction(this.owner, this.owner, new MantraPower(this.owner, this.amount), this.amount));
    }
}
