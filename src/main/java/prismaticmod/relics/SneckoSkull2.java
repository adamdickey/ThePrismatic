package prismaticmod.relics;

import com.evacipated.cardcrawl.mod.stslib.relics.OnApplyPowerRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import theprismatic.ThePrismatic;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static prismaticmod.BasicMod.makeID;

public class SneckoSkull2 extends BaseRelic implements OnApplyPowerRelic {
    private static final String NAME = "Snecko Skull"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.COMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.FLAT; //The sound played when the relic is clicked.

    public SneckoSkull2() {
        super(ID, NAME, ThePrismatic.Enums.CARD_COLOR, RARITY, SOUND);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public boolean onApplyPower(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        return true;
    }
    @Override
    public int onApplyPowerStacks(AbstractPower power, AbstractCreature target, AbstractCreature source, int stackAmount) {
        if(power.type == AbstractPower.PowerType.DEBUFF && target != player && source == player
                && source != null && !target.hasPower(ArtifactPower.POWER_ID)){
            addToBot(new RelicAboveCreatureAction(player, this));
            if(power.amount > 0){
                power.amount++;
                stackAmount++;
            }
            if(power.amount < 0){
                power.amount--;
                stackAmount--;
            }
            power.updateDescription();
        }
        return OnApplyPowerRelic.super.onApplyPowerStacks(power, target, source, stackAmount);
    }
}