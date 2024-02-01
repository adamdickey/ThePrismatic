package prismaticmod.powers;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static prismaticmod.BasicMod.makeID;

public class Caltrops2Power extends BasePower  {

    public static final String ID = makeID("Caltrops");
    public Caltrops2Power(int amount) {
        super(ID, PowerType.BUFF, false, player, player, amount, true);
        updateDescription();
    }
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != this.owner) {
            flash();
            addToTop(new ApplyPowerAction(info.owner, player, new TargetedPower(info.owner, this.amount), this.amount));
        }
        return damageAmount;
    }
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
