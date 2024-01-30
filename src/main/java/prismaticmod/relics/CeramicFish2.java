package prismaticmod.relics;

import com.evacipated.cardcrawl.mod.stslib.relics.OnSkipCardRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import theprismatic.ThePrismatic;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static prismaticmod.BasicMod.makeID;

public class CeramicFish2 extends BaseRelic implements OnSkipCardRelic {
    private static final String NAME = "Ceramic Fish"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.COMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.FLAT; //The sound played when the relic is clicked.
    private static final int goldCount = 20;

    public CeramicFish2() {
        super(ID, NAME, ThePrismatic.Enums.CARD_COLOR, RARITY, SOUND);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + goldCount + this.DESCRIPTIONS[1];
    }

    public void onObtainCard(AbstractCard c) {
        if(!this.usedUp){
            player.gainGold(goldCount);
        }
    }

    public void setCounter(int setCounter) {
        this.counter = setCounter;
        if (setCounter == -2) {
            usedUp();
            this.counter = -2;
        }
    }

    @Override
    public void onSkipSingingBowl(RewardItem rewardItem) {
        if (!this.usedUp) {
            flash();
            setCounter(-2);
        }
    }

    @Override
    public void onSkipCard(RewardItem rewardItem) {
        if (!this.usedUp) {
            flash();
            setCounter(-2);
        }
    }
    public boolean canSpawn() {
        return ((Settings.isEndless || AbstractDungeon.floorNum <= 48) &&
                !(AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.rooms.ShopRoom));
    }
}