package prismaticmod.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theprismatic.ThePrismatic;

import static prismaticmod.BasicMod.makeID;

public class TeardropLocket2 extends BaseRelic {
    private static final String NAME = "Teardrop Locket"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.COMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    public TeardropLocket2() {
        super(ID, NAME, ThePrismatic.Enums.CARD_COLOR, RARITY, SOUND);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public void atBattleStart() {
        flash();
        addToTop(new ChangeStanceAction("Calm"));
        addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }
}