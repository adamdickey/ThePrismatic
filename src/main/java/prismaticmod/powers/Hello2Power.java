package prismaticmod.powers;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static prismaticmod.BasicMod.makeID;

public class Hello2Power extends BasePower {

    public static final String ID = makeID("Hello World");

    public Hello2Power(int amount) {
        super(ID, PowerType.BUFF, false, player, player, amount, true);
        this.amount = amount;
    }

    public void updateDescription() {
        if (this.amount > 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    public void atStartOfTurn() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash();
            for (int i = 0; i < this.amount; i++)
                addToBot(new MakeTempCardInHandAction(AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy(), 1, false));
        }
    }
}