package prismaticmod.cards;

import com.megacrit.cardcrawl.actions.defect.NewThunderStrikeAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import prismaticmod.util.CardStats;
import theprismatic.ThePrismatic;

public class ThunderStrike2 extends BaseCard {
    public static final String ID = makeID("Thunder Strike"); //makeID adds the mod ID, so the final ID will be something like "modID:MyCard"
    private static final CardStats info = new CardStats(
            ThePrismatic.Enums.Blue, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a base game character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ALL_ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 2;

    public ThunderStrike2() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        this.baseMagicNumber = 0;
        this.magicNumber = 0;
        this.tags.add(AbstractCard.CardTags.STRIKE);
        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it changes when upgraded.
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.baseMagicNumber = AbstractDungeon.actionManager.orbsChanneledThisCombat.size();
        this.magicNumber = this.baseMagicNumber;
        for (int i = 0; i < this.magicNumber; i++){
            addToBot(new NewThunderStrikeAction(this));
        }
    }

    public void applyPowers() {
        super.applyPowers();
        this.baseMagicNumber = AbstractDungeon.actionManager.orbsChanneledThisCombat.size();
        if(this.upgraded){
            if (this.baseMagicNumber == 1) {
                this.rawDescription = cardStrings.UPGRADE_DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
            } else if(this.baseMagicNumber > 0) {
                this.rawDescription = cardStrings.UPGRADE_DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[1];
            }
        } else {
            if (this.baseMagicNumber == 1) {
                this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
            } else if(this.baseMagicNumber > 0) {
                this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[1];
            }
        }
        initializeDescription();
    }
    public void onMoveToDiscard() {
        if(this.upgraded){
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        } else {
            this.rawDescription = cardStrings.DESCRIPTION;
        }
        initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        if(this.upgraded){
            if (this.baseMagicNumber == 1) {
                this.rawDescription = cardStrings.UPGRADE_DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
            } else if(this.baseMagicNumber > 0) {
                this.rawDescription = cardStrings.UPGRADE_DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[1];
            }
        } else {
            if (this.baseMagicNumber == 1) {
                this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
            } else if(this.baseMagicNumber > 0) {
                this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[1];
            }
        }
        initializeDescription();
    }
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.selfRetain = true;
            upgradeDamage(2);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
