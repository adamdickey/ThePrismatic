package prismaticmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import prismaticmod.util.CardStats;
import theprismatic.ThePrismatic;

import java.util.ArrayList;
import java.util.Random;

public class StrikeForOne extends BaseCard {
    public static final String ID = makeID("Strike for One"); //makeID adds the mod ID, so the final ID will be something like "modID:MyCard"
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
    private static final int baseMagicNumber = 1;
    private static final int UPG_Number = 0;

    public StrikeForOne() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it changes when upgraded.
        setMagic(baseMagicNumber, UPG_Number);
        tags.add(CardTags.STRIKE);
        tags.add(CardTags.STARTER_STRIKE);
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        if (!p.discardPile.isEmpty()){
            ArrayList<AbstractCard> cards = new ArrayList<>();
            for (AbstractCard card : p.discardPile.group) {
                if (card.cost == 0 || card.freeToPlayOnce){
                    cards.add(card);
                }
            }
            Random rand = new Random();
            if(!cards.isEmpty()){
                for(int i = 0; i < magicNumber; i++) {
                    if(!cards.isEmpty()){
                        int index = rand.nextInt(cards.size());
                        AbstractCard randCard = cards.get(index);
                        addToBot(new DiscardToHandAction(randCard));
                        cards.remove(index);
                    }
                }
            }
        }
    }
}
