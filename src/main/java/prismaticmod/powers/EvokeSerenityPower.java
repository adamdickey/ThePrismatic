package prismaticmod.powers;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static prismaticmod.BasicMod.makeID;

public class EvokeSerenityPower extends BasePower {

    public static final String ID = makeID("Evoke Serenity");

    public EvokeSerenityPower(int amount) {
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
    public void onEvokeOrb(AbstractOrb orb) {
        addToBot(new GainBlockAction(player, this.amount));
    }
}