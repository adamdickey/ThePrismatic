package prismaticmod.powers;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.powers.watcher.FreeAttackPower;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static prismaticmod.BasicMod.makeID;

public class Accuracy2Power extends BasePower {

    public static final String ID = makeID("Accuracy");

    public Accuracy2Power(int amount) {
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

    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        if ((card.costForTurn == 0 || (card.freeToPlayOnce && card.cost != -1)) || player.hasPower(FreeAttackPower.POWER_ID)) {
            return super.atDamageGive(damage + this.amount, type, card);
        } else {
            return super.atDamageGive(damage, type, card);
        }
    }
}