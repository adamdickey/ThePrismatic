package prismaticmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import prismaticmod.powers.TargetedPower;
import prismaticmod.util.CardStats;
import theprismatic.ThePrismatic;

public class CripplingCloud2 extends BaseCard {
    public static final String ID = makeID("Crippling Cloud"); //makeID adds the mod ID, so the final ID will be something like "modID:MyCard"
    private static final CardStats info = new CardStats(
            ThePrismatic.Enums.Green, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ALL_ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.

    private static final int baseMagicNumber = 4;
    private static final int UPG_Number = 2;
    private static final int Weak_Amt = 2;

    public CripplingCloud2() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setMagic(baseMagicNumber, UPG_Number);
        this.exhaust = true;

    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
            addToBot(new ApplyPowerAction(mo, p, new PoisonPower(mo, p, this.magicNumber), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
            addToBot(new ApplyPowerAction(mo, p, new WeakPower(mo, Weak_Amt, false), Weak_Amt, true, AbstractGameAction.AttackEffect.NONE));
            addToBot(new ApplyPowerAction(mo, p, new TargetedPower(mo, Weak_Amt), Weak_Amt, true, AbstractGameAction.AttackEffect.NONE));
        }
    }
}
