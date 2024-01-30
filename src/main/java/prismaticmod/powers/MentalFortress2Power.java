package prismaticmod.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.stances.AbstractStance;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static prismaticmod.BasicMod.makeID;

public class MentalFortress2Power extends BasePower {

    public static final String ID = makeID("Mental Fortress");
    public MentalFortress2Power(int amount) {
        super(ID, PowerType.DEBUFF, false, player, player, amount, true);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void onChangeStance(AbstractStance oldStance, AbstractStance newStance) {
        if (!oldStance.ID.equals(newStance.ID)) {
            flash();
            addToBot(new GainBlockAction(this.owner, this.owner, this.amount));
        }
    }
    public void onChannel(AbstractOrb orb) {
        flash();
        addToBot(new GainBlockAction(this.owner, this.owner, this.amount));
    }
}