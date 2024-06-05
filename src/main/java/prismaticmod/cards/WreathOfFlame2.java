package prismaticmod.cards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.vfx.combat.FlameBarrierEffect;
import prismaticmod.powers.FocusDownPower;
import prismaticmod.util.CardStats;
import theprismatic.ThePrismatic;

public class WreathOfFlame2 extends BaseCard {
    public static final String ID = makeID("Wreath of Flame"); //makeID adds the mod ID, so the final ID will be something like "modID:MyCard"
    private static final CardStats info = new CardStats(
            ThePrismatic.Enums.Purple, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.

    private static final int baseMagicNumber = 5;
    private static final int UPG_Number = 3;
    private static final int baseFocus = 3;
    private static final int UPG_Focus = 2;

    public WreathOfFlame2() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setMagic(baseMagicNumber, UPG_Number);
        setCustomVar("Focus", baseFocus, UPG_Focus);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (Settings.FAST_MODE) {
            addToBot(new VFXAction(p, new FlameBarrierEffect(p.hb.cX, p.hb.cY), 0.1F));
        } else {
            addToBot(new VFXAction(p, new FlameBarrierEffect(p.hb.cX, p.hb.cY), 0.5F));
        }
        addToBot(new ApplyPowerAction(p, p, new VigorPower(p, this.magicNumber), this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new FocusPower(p, customVar("Focus")), customVar("Focus")));
        addToBot(new ApplyPowerAction(p, p, new FocusDownPower(customVar("Focus")), customVar("Focus")));
    }
}
