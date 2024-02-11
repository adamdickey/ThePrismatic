package prismaticmod.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import prismaticmod.util.CardStats;
import theprismatic.ThePrismatic;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class ConjureBlade2 extends BaseCard {
    public static final String ID = makeID("Conjure Blade"); //makeID adds the mod ID, so the final ID will be something like "modID:MyCard"
    private static final CardStats info = new CardStats(
            ThePrismatic.Enums.Purple, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            -1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.

    public ConjureBlade2() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        this.cardsToPreview = new Expunger2();
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded) {
            int effect = EnergyPanel.totalCount;
            if (this.energyOnUse != -1)
                effect = this.energyOnUse + 1;
            if (player.hasRelic("Chemical X")) {
                effect += 2;
                player.getRelic("Chemical X").flash();
            }
            Expunger2 c = new Expunger2();
            c.setX(effect);
            addToBot(new MakeTempCardInDrawPileAction(c, 1, true, true));
            if (!this.freeToPlayOnce)
                player.energy.use(EnergyPanel.totalCount);
            addToBot(new ScryAction(this.energyOnUse + 1));
        } else {
            int effect = EnergyPanel.totalCount;
            if (this.energyOnUse != -1)
                effect = this.energyOnUse;
            if (player.hasRelic("Chemical X")) {
                effect += 2;
                player.getRelic("Chemical X").flash();
            }
            Expunger2 c = new Expunger2();
            c.setX(effect);
            addToBot(new MakeTempCardInDrawPileAction(c, 1, true, true));
            if (!this.freeToPlayOnce)
                player.energy.use(EnergyPanel.totalCount);
            addToBot(new ScryAction(this.energyOnUse));
        }
    }
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
