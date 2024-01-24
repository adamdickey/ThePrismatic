package prismaticmod.relics;

import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theprismatic.ThePrismatic;

import static prismaticmod.BasicMod.makeID;

public class GoldenEye2 extends BaseRelic {
    private static final String NAME = "Golden Eye"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.RARE; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.HEAVY; //The sound played when the relic is clicked.

    public GoldenEye2() {
        super(ID, NAME, ThePrismatic.Enums.CARD_COLOR, RARITY, SOUND);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 1 + this.DESCRIPTIONS[1];
    }

    private boolean turn1 = true;

    public void atBattleStartPreDraw(){
        flash();
        addToBot(new ScryAction(1));
        addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        turn1 = true;
    }
    public void atTurnStart() {
        if (!turn1) {
            if (AbstractDungeon.player.drawPile.size() <= 0)
                addToTop(new EmptyDeckShuffleAction());
            flash();
            addToBot(new ScryAction(1));
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
        turn1 = false;
    }
}