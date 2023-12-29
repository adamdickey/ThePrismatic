package prismaticmod.powers;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static prismaticmod.BasicMod.makeID;
public class CombustivePower extends BasePower{

    public static final String ID = makeID("Combustive Fumes");
    private int hpLoss;
    public CombustivePower(int amount, int hpLoss) {
        super(ID, PowerType.BUFF, false, player, player, amount, true);
        this.hpLoss = hpLoss;
        updateDescription();
    }
    public void atStartOfTurnPostDraw() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash();
            addToBot(new LoseHPAction(this.owner, this.owner, this.hpLoss, AbstractGameAction.AttackEffect.FIRE));
            for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
                if (!m.isDead && !m.isDying)
                    addToBot(new ApplyPowerAction(m, this.owner, new PoisonPower(m, this.owner, this.amount), this.amount));
            }
        }
    }
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        this.hpLoss++;
    }
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.hpLoss + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    }
}
