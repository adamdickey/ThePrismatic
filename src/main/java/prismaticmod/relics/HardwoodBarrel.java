package prismaticmod.relics;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import theprismatic.ThePrismatic;

import static prismaticmod.BasicMod.makeID;

public class HardwoodBarrel extends BaseRelic {
    private static final String NAME = "Hardwood Barrel"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.BOSS; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    public HardwoodBarrel() {
        super(ID, NAME, ThePrismatic.Enums.CARD_COLOR, RARITY, SOUND);
    }
    private boolean emptiedDrawPile = false;
    public void atPreBattle() {
        emptiedDrawPile = false;
    }
    public void onShuffle(){
        emptiedDrawPile = true;
    }
    public void atTurnStart(){
        if(!emptiedDrawPile){
            addToTop(new GainEnergyAction(1));
        }
    }
    public String getUpdatedDescription(){
        return this.DESCRIPTIONS[0];
    }
}