package prismaticmod.cards;

import com.megacrit.cardcrawl.actions.unique.MulticastAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import prismaticmod.patches.DrawToHandAction;
import prismaticmod.util.CardStats;
import theprismatic.ThePrismatic;

import java.util.ArrayList;
import java.util.Random;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class MultiCast2 extends BaseCard {
    public static final String ID = makeID("Multi-Cast"); //makeID adds the mod ID, so the final ID will be something like "modID:MyCard"
    private static final CardStats info = new CardStats(
            ThePrismatic.Enums.Blue, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            -1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.

    public MultiCast2() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        this.showEvokeValue = true;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MulticastAction(p, this.energyOnUse, this.upgraded, this.freeToPlayOnce));
        zeroCostToHand(p, this.energyOnUse, this.upgraded);
    }
    public void zeroCostToHand(AbstractPlayer p, int energyOnUse, boolean upgraded) {
        int effect = EnergyPanel.totalCount;
        if (!this.freeToPlayOnce)
            p.energy.use(EnergyPanel.totalCount);
        if (energyOnUse != -1)
            effect = energyOnUse;
        if (player.hasRelic("Chemical X")) {
            effect += 2;
            player.getRelic("Chemical X").flash();
        }
        if (upgraded)
            effect++;
        if (effect > 0) {
            if (!p.drawPile.isEmpty()) {
                ArrayList<AbstractCard> cards = new ArrayList<>();
                for (AbstractCard card : p.drawPile.group) {
                    if (card.cost == 0 || card.freeToPlayOnce) {
                        cards.add(card);
                    }
                }
                Random rand = new Random();
                if (!cards.isEmpty()) {
                    for (int i = 0; i < effect; i++) {
                        if (!cards.isEmpty()) {
                            int index = rand.nextInt(cards.size());
                            AbstractCard randCard = cards.get(index);
                            addToBot(new DrawToHandAction(randCard));
                        }
                    }
                }
            }
        }
    }
}
