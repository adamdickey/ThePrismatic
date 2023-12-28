package prismaticmod.relics;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theprismatic.ThePrismatic;

import static prismaticmod.BasicMod.makeID;

public class BallOfCards extends BaseRelic {
    private static final String NAME = "Ball of Cards"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.UNCOMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    public BallOfCards() {
        super(ID, NAME, ThePrismatic.Enums.CARD_COLOR, RARITY, SOUND);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 1 + this.DESCRIPTIONS[1] + 7 + this.DESCRIPTIONS[2];
    }
    public void atTurnStart(){
        int cardDraw = (int) Math.floor((double) AbstractDungeon.player.drawPile.size() /7);
        addToBot(new DrawCardAction(AbstractDungeon.player, cardDraw));
    }
}