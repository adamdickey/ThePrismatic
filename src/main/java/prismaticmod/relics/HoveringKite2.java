package prismaticmod.relics;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theprismatic.ThePrismatic;

import static prismaticmod.BasicMod.makeID;

public class HoveringKite2 extends BaseRelic {
    private static final String NAME = "Hovering Kite"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.BOSS; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.

    public HoveringKite2() {
        super(ID, NAME, ThePrismatic.Enums.CARD_COLOR, RARITY, SOUND);
    }
    private boolean triggeredThisTurn = false;

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void atTurnStart() {
        this.triggeredThisTurn = false;
    }

    public void onManualDiscard() {
        if (!this.triggeredThisTurn) {
            this.triggeredThisTurn = true;
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new GainEnergyAction(1));
        }
    }
}