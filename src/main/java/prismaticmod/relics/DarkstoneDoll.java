package prismaticmod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theprismatic.ThePrismatic;

import static prismaticmod.BasicMod.makeID;

public class DarkstoneDoll extends BaseRelic {
    private static final String NAME = "Darkstone Doll"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.UNCOMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.SOLID; //The sound played when the relic is clicked.
    private static final int HP_AMT = 6; //Darkstone

    public String getUpdatedDescription() {return this.DESCRIPTIONS[0] + 6 + this.DESCRIPTIONS[1] + this.DESCRIPTIONS[2] + 1 + this.DESCRIPTIONS[3];}
    public DarkstoneDoll() {
        super(ID, NAME, ThePrismatic.Enums.CARD_COLOR, RARITY, SOUND);
    }
    //Darkstone Precipate:
    public void onObtainCard(AbstractCard card) {
        if (card.color == AbstractCard.CardColor.CURSE) {
            AbstractDungeon.player.increaseMaxHp(HP_AMT, true);
        }
    }

    //Duvu doll:

    public void onMasterDeckChange() {
        this.counter = 0;
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.type == AbstractCard.CardType.CURSE)
                this.counter++;
        }
    }

    public void onEquip() {
        this.counter = 0;
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.type == AbstractCard.CardType.CURSE)
                this.counter++;
        }
    }

    public void atBattleStart() {
        if (this.counter > 0) {
            flash();
            addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, this.counter), this.counter));
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }

}