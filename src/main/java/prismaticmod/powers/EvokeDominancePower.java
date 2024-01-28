package prismaticmod.powers;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static prismaticmod.BasicMod.makeID;

public class EvokeDominancePower extends BasePower {

    public static final String ID = makeID("Evoke Dominance");

    public EvokeDominancePower(int amount) {
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
    public void onEvokeOrb(AbstractOrb orb) {
        for(int i = 0; i < this.amount; i++){
            orb.onEvoke();
        }
    }
}