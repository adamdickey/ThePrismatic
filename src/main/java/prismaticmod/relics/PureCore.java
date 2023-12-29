package prismaticmod.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rewards.RewardItem;
import theprismatic.ThePrismatic;
import static prismaticmod.BasicMod.makeID;

public class PureCore extends BaseRelic {
    private static final String NAME = "Pure Core"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.BOSS; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.SOLID; //The sound played when the relic is clicked.
    private boolean cardsReceived = true;

    public PureCore() {
        super(ID, NAME, ThePrismatic.Enums.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(BurningRing.ID);
    }



    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(BurningRing.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); i++) {
                if ((AbstractDungeon.player.relics.get(i)).relicId.equals(BurningRing.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
        this.cardsReceived = false;
    }
    public void update(){
        super.update();
        if (!this.cardsReceived && !AbstractDungeon.isScreenUp && this.isObtained) {
            AbstractDungeon.combatRewardScreen.open();
            AbstractDungeon.combatRewardScreen.rewards.clear();
            AbstractDungeon.combatRewardScreen.rewards.add(new RewardItem(
                    RelicLibrary.getRelic(BurningBlood.ID)));
            AbstractDungeon.combatRewardScreen.rewards.add(new RewardItem(
                    RelicLibrary.getRelic(SnakeRing.ID)));
            AbstractDungeon.combatRewardScreen.rewards.add(new RewardItem(
                    RelicLibrary.getRelic(CrackedCore.ID)));
            AbstractDungeon.combatRewardScreen.rewards.add(new RewardItem(
                    RelicLibrary.getRelic(PureWater.ID)));
            AbstractDungeon.combatRewardScreen.positionRewards();
            cardsReceived = true;
            (AbstractDungeon.getCurrRoom()).rewardPopOutTimer = 0.25F;
        }
    }
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
}