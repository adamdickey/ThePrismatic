package prismaticmod.powers;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.powers.AbstractPower;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static prismaticmod.BasicMod.makeID;

public class StaticDischarge2Power extends BasePower{

    public static final String ID = makeID("Static Discharge");

    public StaticDischarge2Power(int amount) {
        super(ID, PowerType.BUFF, false, player, player, amount, true);
        this.amount = amount;
    }
    public void updateDescription() {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
    public boolean debuffed = false;
    public void atStartOfTurn() {
        debuffed = false;
    }
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.type == AbstractPower.PowerType.DEBUFF && !power.ID.equals("Shackled") && source == this.owner && target != this.owner &&
                !target.hasPower("Artifact") && !debuffed) {
            for(int i = 0; i < this.amount; i++){
                addToBot(new ChannelAction(new Lightning()));
            }
            debuffed = true;
        }
    }
}