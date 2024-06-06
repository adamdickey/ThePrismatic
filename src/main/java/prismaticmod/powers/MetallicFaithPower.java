package prismaticmod.powers;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static prismaticmod.BasicMod.makeID;

public class MetallicFaithPower extends BasePower {

    public static final String ID = makeID("Metallic Faith");

    public MetallicFaithPower(int amount) {
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

    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        if(player.hasPower(MantraPower.POWER_ID)){
            flash();
            addToBot(new GainBlockAction(this.owner, this.owner, this.amount*player.getPower(MantraPower.POWER_ID).amount));
        }
    }
}