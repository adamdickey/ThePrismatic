package prismaticmod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.FocusPower;
import theprismatic.ThePrismatic;

import static prismaticmod.BasicMod.makeID;

public class BurningRing extends BaseRelic {
    private static final String NAME = "Burning Ring"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.STARTER; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    public BurningRing() {
        super(ID, NAME, ThePrismatic.Enums.CARD_COLOR, RARITY, SOUND);
    }
    private static final int focus = 1;
    private static final int cards = 1;
    private static final int heal = 3;
    @Override
    public void atBattleStart() {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FocusPower(AbstractDungeon.player, 1), 1));
        addToBot(new DrawCardAction(AbstractDungeon.player, 1));
    }
    @Override
    public void onVictory() {
        flash();
        AbstractPlayer p = AbstractDungeon.player;
        addToTop(new RelicAboveCreatureAction(p, this));
        if (p.currentHealth > 0)
            p.heal(heal);
    }
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + cards +this.DESCRIPTIONS[1] + focus + this.DESCRIPTIONS[2] + heal + this.DESCRIPTIONS[3];
    }
}