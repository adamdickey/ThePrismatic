package prismaticmod.relics;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theprismatic.ThePrismatic;

import static prismaticmod.BasicMod.makeID;

public class RingOfKeys extends BaseRelic {
    private static final String NAME = "Ring of Keys"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SHOP; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.FLAT; //The sound played when the relic is clicked.

    public RingOfKeys() {
        super(ID, NAME, ThePrismatic.Enums.CARD_COLOR, RARITY, SOUND);
    }
    public void atBattleStart() {
        updateCounter();
        if (counter > 0) {
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new DrawCardAction(AbstractDungeon.player, counter));
        }
    }

    @Override
    public void onEquip() {
        updateCounter();
    }
    @Override
    public void onMasterDeckChange() {
        updateCounter();
    }
    void updateCounter() {
        counter = (int)AbstractDungeon.player.masterDeck.group.stream().filter(c -> c.isInnate).count();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
//Old Ring of Keys effect:
//    public String getUpdatedDescription() {
//        return this.DESCRIPTIONS[0] + 1 + this.DESCRIPTIONS[1] + 10 + this.DESCRIPTIONS[2];
//    }
//    public void atBattleStart(){
//        flash();
//        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
//        int cardDraw = (int) Math.floor((double) AbstractDungeon.player.drawPile.size() /10);
//        addToBot(new DrawCardAction(AbstractDungeon.player, cardDraw));
//    }
//}