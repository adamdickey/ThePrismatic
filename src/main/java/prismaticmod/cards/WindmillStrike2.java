package prismaticmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EquilibriumPower;
import com.megacrit.cardcrawl.relics.RunicPyramid;
import prismaticmod.util.CardStats;
import theprismatic.ThePrismatic;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class WindmillStrike2 extends BaseCard {
    public static final String ID = makeID("Windmill Strike"); //makeID adds the mod ID, so the final ID will be something like "modID:MyCard"
    private static final CardStats info = new CardStats(
            ThePrismatic.Enums.Purple, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a base game character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 2;
    private static final int baseMagicNumber = 2;
    private static final int UPG_Number = 1;
    int cardsRetained = 0;
    boolean retained = false;

    public WindmillStrike2() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it changes when upgraded.
        setMagic(baseMagicNumber, UPG_Number);
        this.tags.add(AbstractCard.CardTags.STRIKE);
        this.selfRetain = true;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }
    public void triggerOnEndOfTurnForPlayingCard() {
        retained = true;
        cardsRetained = 0;
        if(player.hasRelic(RunicPyramid.ID)){
            for(AbstractCard c : player.hand.group){
                if((c.retain || c.selfRetain) || (player.hasPower(EquilibriumPower.POWER_ID) && !c.isEthereal)){
                    cardsRetained++;
                }
            }
        }
    }
    public void atTurnStart(){
        if(retained){
            if(cardsRetained != 0){
                upgradeDamage(magicNumber*cardsRetained);
            } else {
                upgradeDamage(magicNumber*player.hand.group.size());
            }
            retained = false;
        }
    }
}
