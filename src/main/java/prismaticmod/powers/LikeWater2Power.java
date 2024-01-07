package prismaticmod.powers;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static prismaticmod.BasicMod.makeID;

public class LikeWater2Power extends BasePower {

    public static final String ID = makeID("Like Water");

    public LikeWater2Power(int amount) {
        super(ID, PowerType.BUFF, false, player, player, amount, true);
        this.amount = amount;
        loadRegion("like_water");
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
    private boolean hasFrostOrb = false;
    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        if (isPlayer) {
            AbstractPlayer p = (AbstractPlayer)this.owner;
            for(AbstractOrb o : p.orbs){
                if(o instanceof com.megacrit.cardcrawl.orbs.Frost){
                    hasFrostOrb = true;
                    break;
                } else {
                    hasFrostOrb = false;
                }
            }
            if (p.stance.ID.equals("Calm") || hasFrostOrb) {
                flash();
                addToBot(new GainBlockAction(this.owner, this.owner, this.amount));
            }
        }
    }
}