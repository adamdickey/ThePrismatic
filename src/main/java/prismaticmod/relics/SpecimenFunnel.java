package prismaticmod.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerToRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import theprismatic.ThePrismatic;

import static basemod.BaseMod.logger;
import static prismaticmod.BasicMod.makeID;

public class SpecimenFunnel extends BaseRelic {
    private static final String NAME = "Specimen Funnel"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SHOP; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.HEAVY; //The sound played when the relic is clicked.

    public SpecimenFunnel() {
        super(ID, NAME, ThePrismatic.Enums.CARD_COLOR, RARITY, SOUND);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 4 + this.DESCRIPTIONS[1];
    }

    public void atBattleStart() {
        flash();
        for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
            if (!m.isDead && !m.isDying)
                addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new PoisonPower(m, AbstractDungeon.player, 4), 4));
        }
    }

    public void onMonsterDeath(AbstractMonster m) {
        if (m.hasPower("Poison")) {
            int amount = (m.getPower("Poison")).amount;
            if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                flash();
                addToBot(new RelicAboveCreatureAction(m, this));
                addToBot(new ApplyPowerToRandomEnemyAction(AbstractDungeon.player, new PoisonPower(null, AbstractDungeon.player, amount), amount, false, AbstractGameAction.AttackEffect.POISON));
            } else {
                logger.info("no target for the specimen");
            }
        }
    }
}