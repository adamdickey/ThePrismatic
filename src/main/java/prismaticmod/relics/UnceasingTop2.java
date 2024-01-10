package prismaticmod.relics;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theprismatic.ThePrismatic;

import static prismaticmod.BasicMod.makeID;

public class UnceasingTop2 extends BaseRelic {
    private static final String NAME = "Unceasing Top"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.UNCOMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.
    private boolean canDraw = false;
    private boolean disabledUntilEndOfTurn = false;
    public UnceasingTop2() {
        super(ID, NAME, ThePrismatic.Enums.CARD_COLOR, RARITY, SOUND);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void atPreBattle() {
        this.canDraw = false;
    }
    public void atTurnStart() {
        this.canDraw = true;
        this.disabledUntilEndOfTurn = false;
    }
    public void disableUntilTurnEnds() {
        this.disabledUntilEndOfTurn = true;
    }

    public void onRefreshHand() {
        if (AbstractDungeon.actionManager.actions.isEmpty() && AbstractDungeon.player.hand.isEmpty() && !AbstractDungeon.actionManager.turnHasEnded && this.canDraw &&
                !AbstractDungeon.player.hasPower("No Draw") && !AbstractDungeon.isScreenUp)
            if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT && !this.disabledUntilEndOfTurn && (
                    !AbstractDungeon.player.discardPile.isEmpty() || !AbstractDungeon.player.drawPile.isEmpty())) {
                flash();
                addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                addToBot(new DrawCardAction(AbstractDungeon.player, 1));
            }
    }
}