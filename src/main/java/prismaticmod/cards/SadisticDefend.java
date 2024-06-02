package prismaticmod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import prismaticmod.util.CardStats;
import theprismatic.ThePrismatic;

import java.util.ArrayList;
import java.util.Collections;

public class SadisticDefend extends BaseCard {
    public static final String ID = makeID("Sadistic Defend"); //makeID adds the mod ID, so the final ID will be something like "modID:MyCard"
    private static final CardStats info = new CardStats(
            ThePrismatic.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.BASIC, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.

    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 3;
    private static final int Magic = 2;
    private static final int UPG_Magic = 0;

    public SadisticDefend() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setBlock(BLOCK, UPG_BLOCK); //Sets the card's damage and how much it changes when upgraded.
        setCustomVar("Magic", Magic, UPG_Magic);
        //tags.add(CardTags.STARTER_DEFEND);
        this.baseMagicNumber = 0;
        this.magicNumber = this.baseMagicNumber;
    }
    public void applyPowers() {
        int realBaseBlock = this.baseBlock;
        this.baseMagicNumber = findDebuffs();
        this.baseBlock += this.baseMagicNumber;
        super.applyPowers();
        this.baseBlock = realBaseBlock;
        this.isBlockModified = (this.block != this.baseBlock);
    }
    public void calculateCardDamage(AbstractMonster mo) {
        this.baseMagicNumber = findDebuffs();
        int realBaseBlock = this.baseBlock;
        this.baseBlock += this.baseMagicNumber;
        super.calculateCardDamage(mo);
        this.baseBlock = realBaseBlock;
        this.isBlockModified = (this.block != this.baseBlock);
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.block += this.magicNumber;
        calculateCardDamage(m);
        addToBot(new GainBlockAction(p, p, this.block));
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
        return customVar("Magic")* Collections.max(debuffs);
    }
}
