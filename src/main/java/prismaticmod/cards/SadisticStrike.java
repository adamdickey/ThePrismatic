package prismaticmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import prismaticmod.util.CardStats;
import theprismatic.ThePrismatic;

import java.util.ArrayList;
import java.util.Collections;

public class SadisticStrike extends BaseCard {
    public static final String ID = makeID("Sadistic Strike"); //makeID adds the mod ID, so the final ID will be something like "modID:MyCard"
    private static final CardStats info = new CardStats(
            ThePrismatic.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.BASIC, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 3;
    private static final int Magic = 2;
    private static final int UPG_Magic = 0;

    public SadisticStrike() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it changes when upgraded.
        setCustomVar("Magic", Magic, UPG_Magic);
        tags.add(CardTags.STRIKE);
        //tags.add(CardTags.STARTER_STRIKE);
        this.baseMagicNumber = 0;
        this.magicNumber = this.baseMagicNumber;
    }
    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        this.baseMagicNumber = findDebuffs();
        this.baseDamage += this.baseMagicNumber;
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = (this.damage != this.baseDamage);
    }
    public void calculateCardDamage(AbstractMonster mo) {
        this.baseMagicNumber = findDebuffs();
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.baseMagicNumber;
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = (this.damage != this.baseDamage);
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.damage += this.magicNumber;
        calculateCardDamage(m);
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }
    private int findDebuffs(){
        ArrayList<Integer> debuffs = new ArrayList<>(5);
        int monsterDebuffs = 0;
        for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
            for(AbstractPower p : m.powers){
                if(p.type == AbstractPower.PowerType.DEBUFF && !p.ID.equals("Shackled")){
                    monsterDebuffs++;
                }
            }
            debuffs.add(monsterDebuffs);
            monsterDebuffs = 0;
        }
        return customVar("Magic")*Collections.max(debuffs);
    }
}
