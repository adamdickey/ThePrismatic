package prismaticmod.powers;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static prismaticmod.BasicMod.makeID;

public class Brutality2Power extends BasePower {

    public static final String ID = makeID("Brutality");

    public Brutality2Power(int amount) {
        super(ID, PowerType.BUFF, false, player, player, amount, true);
        this.amount = amount;
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        } else {
            this.description = DESCRIPTIONS[3] + this.amount + DESCRIPTIONS[4] + this.amount + DESCRIPTIONS[5];
        }
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }
    public void atStartOfTurn() {
        flash();
        addToBot(new LoseHPAction(this.owner, this.owner, this.amount));
        addToBot(new DrawCardAction(this.owner, this.amount*debuffedMonsters()));
    }
    public int debuffedMonsters(){
        int debuffs = 0;
        for(AbstractMonster m : AbstractDungeon.getMonsters().monsters){
            for(AbstractPower power : m.powers) {
                if (power.type == AbstractPower.PowerType.DEBUFF && !power.ID.equals("Shackled")) {
                    debuffs++;
                    break;
                }
            }
        }
        return debuffs;
    }
}