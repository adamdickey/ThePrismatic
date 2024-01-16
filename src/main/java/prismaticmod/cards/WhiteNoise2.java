package prismaticmod.cards;

import com.megacrit.cardcrawl.actions.unique.DiscoveryAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import prismaticmod.util.CardStats;

public class WhiteNoise2 extends BaseCard {
    public static final String ID = makeID("White Noise"); //makeID adds the mod ID, so the final ID will be something like "modID:MyCard"
    private static final CardStats info = new CardStats(
            CardColor.BLUE, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.


    public WhiteNoise2() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        this.exhaust = true;

    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DiscoveryAction(CardType.POWER, 1));
    }
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.exhaust = false;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
