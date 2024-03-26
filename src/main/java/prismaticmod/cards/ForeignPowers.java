package prismaticmod.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import prismaticmod.util.CardStats;
import theprismatic.ThePrismatic;

import java.util.ArrayList;
import java.util.Random;

public class ForeignPowers extends BaseCard {
    public static final String ID = makeID("Foreign Powers"); //makeID adds the mod ID, so the final ID will be something like "modID:MyCard"
    private static final CardStats info = new CardStats(
            ThePrismatic.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            4 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.
    public ForeignPowers() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        this.exhaust = true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(AbstractDungeon.player.chosenClass == ThePrismatic.Enums.Prismatic) {
            AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.POWER);

            //Red Power
            while (c.color != CardColor.RED && c.color != ThePrismatic.Enums.Red){
                c = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.POWER);
            }
            c.makeCopy();
            c.setCostForTurn(0);
            addToBot(new MakeTempCardInHandAction(c, true));

            //Green Power
            while (c.color != CardColor.GREEN && c.color != ThePrismatic.Enums.Green){
                c = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.POWER);
            }
            c.makeCopy();
            c.setCostForTurn(0);
            addToBot(new MakeTempCardInHandAction(c, true));

            //Blue Power
            while (c.color != CardColor.BLUE && c.color != ThePrismatic.Enums.Blue){
                c = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.POWER);
            }
            c.makeCopy();
            c.setCostForTurn(0);
            addToBot(new MakeTempCardInHandAction(c, true));

            //Purple Power
            while (c.color != CardColor.PURPLE && c.color != ThePrismatic.Enums.Purple){
                c = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.POWER);
            }
            c.makeCopy();
            c.setCostForTurn(0);
            addToBot(new MakeTempCardInHandAction(c, true));

        } else {

            Random rand = new Random();

            ArrayList<AbstractCard> redCards = CardLibrary.getCardList(CardLibrary.LibraryType.RED);
            ArrayList<AbstractCard> redPowers = new ArrayList<>();
            for (AbstractCard c : redCards) {
                if (c.type == CardType.POWER) {
                    redPowers.add(c);
                }
            }
            AbstractCard c = redPowers.get(rand.nextInt(redPowers.size()));
            c.makeCopy();
            c.setCostForTurn(0);
            addToBot(new MakeTempCardInHandAction(c, true));

            ArrayList<AbstractCard> greenCards = CardLibrary.getCardList(CardLibrary.LibraryType.GREEN);
            ArrayList<AbstractCard> greenPowers = new ArrayList<>();
            for (AbstractCard c2 : greenCards) {
                if (c2.type == CardType.POWER) {
                    greenPowers.add(c2);
                }
            }
            AbstractCard c2 = greenPowers.get(rand.nextInt(greenPowers.size()));
            c2.makeCopy();
            c2.setCostForTurn(0);
            addToBot(new MakeTempCardInHandAction(c2, true));

            ArrayList<AbstractCard> blueCards = CardLibrary.getCardList(CardLibrary.LibraryType.BLUE);
            ArrayList<AbstractCard> bluePowers = new ArrayList<>();
            for (AbstractCard c3 : blueCards) {
                if (c3.type == CardType.POWER) {
                    bluePowers.add(c3);
                }
            }
            AbstractCard c3 = bluePowers.get(rand.nextInt(bluePowers.size()));
            c3.makeCopy();
            c3.setCostForTurn(0);
            addToBot(new MakeTempCardInHandAction(c3, true));

            ArrayList<AbstractCard> purpleCards = CardLibrary.getCardList(CardLibrary.LibraryType.PURPLE);
            ArrayList<AbstractCard> purplePowers = new ArrayList<>();
            for (AbstractCard c4 : purpleCards) {
                if (c4.type == CardType.POWER) {
                    purplePowers.add(c4);
                }
            }
            AbstractCard c4 = purplePowers.get(rand.nextInt(purplePowers.size()));
            c4.makeCopy();
            c4.setCostForTurn(0);
            addToBot(new MakeTempCardInHandAction(c4, true));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(3);
        }
    }
}
