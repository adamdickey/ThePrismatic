package prismaticmod.powers;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static prismaticmod.BasicMod.makeID;

public class WaveOfTheHand2Power extends BasePower {

    public static final String ID = makeID("Wave of the Hand");

    public WaveOfTheHand2Power(int amount) {
        super(ID, PowerType.BUFF, false, player, player, amount, true);
        this.amount = amount;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    public void onGainedBlock(float blockAmount) {
        if (blockAmount > 0.0F) {
            flash();
            for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters){
                addToBot(new ApplyPowerAction(mo, player, new WeakPower(mo, this.amount, false), this.amount, true, AbstractGameAction.AttackEffect.NONE));
                addToBot(new ApplyPowerAction(mo, player, new TargetedPower(mo, this.amount), this.amount, true, AbstractGameAction.AttackEffect.NONE));
            }
        }
    }
    public void atEndOfRound() {
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }
}