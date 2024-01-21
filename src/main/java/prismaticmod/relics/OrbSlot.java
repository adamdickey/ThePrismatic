package prismaticmod.relics;

import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import theprismatic.ThePrismatic;

import static prismaticmod.BasicMod.makeID;

public class OrbSlot extends BaseRelic {
    private static final String NAME = "Orb Slot"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SPECIAL; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.FLAT; //The sound played when the relic is clicked.
    public OrbSlot() {
        super(ID, NAME, ThePrismatic.Enums.CARD_COLOR, RARITY, SOUND);
        this.counter = 1;
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public void atBattleStart(){
        flash();
        addToBot(new IncreaseMaxOrbAction(this.counter));
    }
}