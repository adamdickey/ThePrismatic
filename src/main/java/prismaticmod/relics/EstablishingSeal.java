package prismaticmod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.EstablishmentPower;
import theprismatic.ThePrismatic;

import static prismaticmod.BasicMod.makeID;

public class EstablishingSeal extends BaseRelic {
    private static final String NAME = "Establishing Seal"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SHOP; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.SOLID; //The sound played when the relic is clicked.

    public EstablishingSeal() {
        super(ID, NAME, ThePrismatic.Enums.CARD_COLOR, RARITY, SOUND);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public void atBattleStart(){
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EstablishmentPower(AbstractDungeon.player, 1), 1));
    }
}