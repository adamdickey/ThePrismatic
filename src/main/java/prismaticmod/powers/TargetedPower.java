package prismaticmod.powers;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseBlockPower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.powers.PoisonPower;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static prismaticmod.BasicMod.makeID;

public class TargetedPower extends BasePower implements OnLoseBlockPower, HealthBarRenderPower {

    public static final String ID = makeID("Targeted");
    private static final int MULTI_STR = 50;

    private static int explosivePotion = 0;

    public TargetedPower(AbstractCreature owner, int amount) {
        super(ID, PowerType.DEBUFF, true, owner, player, amount, true);
        loadRegion("lockon");
    }
    @Override
    public int onLoseBlock(DamageInfo info, int damageAmount){
        if(info.type != DamageInfo.DamageType.NORMAL || explosivePotion > 0){
            info.isModified = true;
            if(explosivePotion > 0){
                explosivePotion--;
            }
            return this.onAttackedToChangeDamage(info, (int) (damageAmount*1.5));
        }
        return damageAmount;
    }
    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if((info.type != DamageInfo.DamageType.NORMAL || explosivePotion > 0) && !info.isModified){
            if(explosivePotion > 0){
                explosivePotion--;
            }
            return (int)(damageAmount*1.5);
        }
        return damageAmount;
    }

    public void atEndOfRound() {
        if (this.amount == 0) {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, TargetedPower.ID));
        } else {
            addToBot(new ReducePowerAction(this.owner, this.owner, TargetedPower.ID, 1));
        }
    }

    public void updateDescription() {
        if (this.owner != null)
            if (this.amount == 1) {
                this.description = DESCRIPTIONS[0] + MULTI_STR + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
            } else {
                this.description = DESCRIPTIONS[0] + MULTI_STR + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[3];
            }
    }
    @Override
    public int getHealthBarAmount() {
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (m.hasPower(PoisonPower.POWER_ID) && m.hasPower(TargetedPower.ID) && !m.hasPower(IntangiblePower.POWER_ID)) {
                return (int) (m.getPower(PoisonPower.POWER_ID).amount * 0.5);
            }
        }
        return 0;
    }

    @Override
    public Color getColor() {
        return Color.valueOf("78c13c00");
    }

    public static void explosivePotionUsed(){
        explosivePotion = AbstractDungeon.getCurrRoom().monsters.monsters.size();
    }
}