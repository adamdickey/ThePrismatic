package prismaticmod.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.stances.AbstractStance;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static prismaticmod.BasicMod.makeID;

public class Rushdown2Power extends BasePower{

    public static final String ID = makeID("Rushdown");

    public Rushdown2Power(int amount) {
        super(ID, PowerType.BUFF, false, player, player, 1, true);
        this.amount = amount;
    }
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + 2 + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[3] + 2 + DESCRIPTIONS[1];
        }
    }
    int stanceChanged = 0;
    public void atStartOfTurn(){
        stanceChanged = 0;
    }
    public void onChangeStance(AbstractStance oldStance, AbstractStance newStance) {
        if(stanceChanged < this.amount){
            addToBot(new DrawCardAction(2));
            stanceChanged += 1;
        }
    }

}
