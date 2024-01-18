package prismaticmod.cards;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import prismaticmod.util.CardStats;
import theprismatic.ThePrismatic;

public class DeusExMachina2 extends BaseCard {
    public static final String ID = makeID("Deus Ex Machina"); //makeID adds the mod ID, so the final ID will be something like "modID:MyCard"
    private static final CardStats info = new CardStats(
            ThePrismatic.Enums.Purple, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            -2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public DeusExMachina2() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        //These will be used in the constructor. Technically you can just use the values directly,
        //but constants at the top of the file are easy to adjust.
        int baseMagicNumber = 2;
        int UPG_Number = 1;
        setMagic(baseMagicNumber, UPG_Number);
    }
    @Override
    public void triggerWhenDrawn() {
        addToTop(new MakeTempCardInHandAction(new Miracle(), magicNumber));
        addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
    }
    public void use(AbstractPlayer p, AbstractMonster m){}
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }
}
