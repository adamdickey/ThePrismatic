package prismaticmod.relics;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theprismatic.ThePrismatic;

import java.util.ArrayList;

import static prismaticmod.BasicMod.makeID;

public class MummifiedHand2 extends BaseRelic {
    private static final String NAME = "Mummified Hand"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.BOSS; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.SOLID; //The sound played when the relic is clicked.
    public MummifiedHand2() {
        super(ID, NAME, ThePrismatic.Enums.CARD_COLOR, RARITY, SOUND);
    }
    boolean powerPlayed = false;

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public void atTurnStart() {
        powerPlayed = false;
    }
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.POWER) {
            if(!powerPlayed){
                addToBot(new DrawCardAction(1));
                powerPlayed = true;
            }
            flash();
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            ArrayList<AbstractCard> groupCopy = new ArrayList<>();
            for (AbstractCard abstractCard : AbstractDungeon.player.hand.group) {
                if (abstractCard.cost > 0 && abstractCard.costForTurn > 0 && !abstractCard.freeToPlayOnce) {
                    groupCopy.add(abstractCard);
                }
            }
            for (CardQueueItem i : AbstractDungeon.actionManager.cardQueue) {
                if (i.card != null) {
                    groupCopy.remove(i.card);
                }
            }
            AbstractCard c = null;
            if (!groupCopy.isEmpty()) {
                c = groupCopy.get(AbstractDungeon.cardRandomRng.random(0, groupCopy.size() - 1));
            }
            if (c != null) {
                c.setCostForTurn(0);
            }
        }
    }
}