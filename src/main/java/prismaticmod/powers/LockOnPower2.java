package prismaticmod.powers;

import basemod.patches.com.megacrit.cardcrawl.relics.AbstractRelic.RelicOutlineColor;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import prismaticmod.BasicMod;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static prismaticmod.BasicMod.makeID;

public class LockOnPower2 extends BasePower {

    public static final String ID = makeID("Lock-on");
    private static final int MULTI_STR = 50;

    public LockOnPower2(AbstractCreature owner, int amount) {
        super(ID, PowerType.DEBUFF, true, owner, player, amount, true);
    }
    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType type){
        if(type != DamageInfo.DamageType.NORMAL){
            damage = damage * 1.5F;
        }
        return damage;
    }
    public void atEndOfRound() {
        if (this.amount == 0) {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "ThePrismatic:Lock-on"));
        } else {
            addToBot(new ReducePowerAction(this.owner, this.owner, "ThePrismatic:Lock-on", 1));
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
}