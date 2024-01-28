package prismaticmod.relics;

import com.evacipated.cardcrawl.mod.stslib.relics.OnApplyPowerRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import prismaticmod.powers.TargetedPower;
import theprismatic.ThePrismatic;

import java.util.Objects;

import static prismaticmod.BasicMod.makeID;

public class ChampionBelt2 extends BaseRelic implements OnApplyPowerRelic {
    private static final String NAME = "Champion Belt"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.UNCOMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.FLAT; //The sound played when the relic is clicked.

    public ChampionBelt2() {
        super(ID, NAME, ThePrismatic.Enums.CARD_COLOR, RARITY, SOUND);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 1 + this.DESCRIPTIONS[1] + 1 + this.DESCRIPTIONS[2];
    }

    @Override
    public boolean onApplyPower(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        return true;
    }

    @Override
    public int onApplyPowerStacks(AbstractPower power, AbstractCreature target, AbstractCreature source, int stackAmount) {
        if(Objects.equals(power.ID, VulnerablePower.POWER_ID) && !target.hasPower(ArtifactPower.POWER_ID)){
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new ApplyPowerAction(target, source, new WeakPower(target, 1, false), 1));
            addToBot(new ApplyPowerAction(target, source, new TargetedPower(target, 1), 1));
        }
        return OnApplyPowerRelic.super.onApplyPowerStacks(power, target, source, stackAmount);
    }
}