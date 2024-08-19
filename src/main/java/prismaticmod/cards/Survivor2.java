package prismaticmod.cards;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import prismaticmod.actions.HandSelectAction;
import prismaticmod.util.CardStats;
import theprismatic.ThePrismatic;

public class Survivor2 extends BaseCard {
    public static final String ID = makeID("Survivor"); //makeID adds the mod ID, so the final ID will be something like "modID:MyCard"
    private static final CardStats info = new CardStats(
            ThePrismatic.Enums.Green, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.

    private static final int BLOCK = 8;
    private static final int UPG_BLOCK = 3;
    private static final int baseMagicNumber = 1;
    private static final int UPG_Number = 0;

    public Survivor2() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(baseMagicNumber, UPG_Number);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
        addToBot(new HandSelectAction(1, c -> true, list -> {
            for (AbstractCard c : list) {
                if (c.type == CardType.CURSE || c.type == CardType.STATUS) {
                    GameActionManager.incrementDiscard(false);
                    p.hand.moveToExhaustPile(c);
                    addToBot(new DrawCardAction(magicNumber));
                    continue;
                }
                p.hand.moveToDiscardPile(c);
                c.triggerOnManualDiscard();
                GameActionManager.incrementDiscard(false);
            }
            list.clear();
        }, null, "Discard", false, false, false));
    }
}
