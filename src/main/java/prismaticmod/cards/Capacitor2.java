package prismaticmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import prismaticmod.util.CardStats;
import theprismatic.ThePrismatic;

public class Capacitor2 extends BaseCard {
    public static final String ID = makeID("Capacitor"); //makeID adds the mod ID, so the final ID will be something like "modID:MyCard"
    private static final CardStats info = new CardStats(
            ThePrismatic.Enums.Blue, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.POWER, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public Capacitor2() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        //These will be used in the constructor. Technically you can just use the values directly,
        //but constants at the top of the file are easy to adjust.
        int baseMagicNumber = 2;
        int UPG_Number = 1;
        int baseDex = 1;
        int UPG_Dex = 1;
        setMagic(baseMagicNumber, UPG_Number);
        setCustomVar("dex", baseDex, UPG_Dex);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new IncreaseMaxOrbAction(magicNumber));
        addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, customVar("dex")), customVar("dex")));
    }
}
