package prismaticmod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theprismatic.ThePrismatic;

import static prismaticmod.BasicMod.makeID;


public class Brimstone2 extends BaseRelic {
    private static final String NAME = "Brimstone"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.BOSS; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    private static final int STR_AMT = 2;

    private static final int ENEMY_STR_AMT = 1;

    public Brimstone2() {
        super(ID, NAME, ThePrismatic.Enums.CARD_COLOR, RARITY, SOUND);
    }

    public String getUpdatedDescription() {return this.DESCRIPTIONS[0] + STR_AMT + this.DESCRIPTIONS[1] + ENEMY_STR_AMT + this.DESCRIPTIONS[2];}

    public void atTurnStart() {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, STR_AMT), STR_AMT));
        for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters)
            addToTop(new ApplyPowerAction(m, m, new StrengthPower(m, ENEMY_STR_AMT), ENEMY_STR_AMT));
    }

}