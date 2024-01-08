package prismaticmod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.powers.FocusPower;
import theprismatic.ThePrismatic;

import static prismaticmod.BasicMod.makeID;

public class ViralDisk extends BaseRelic {
    private static final String NAME = "Viral Disk"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.UNCOMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.FLAT; //The sound played when the relic is clicked.

    public ViralDisk() {
        super(ID, NAME, ThePrismatic.Enums.CARD_COLOR, RARITY, SOUND);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 1 + this.DESCRIPTIONS[1] + 1 + this.DESCRIPTIONS[2];
    }
    public void atBattleStartPreDraw(){
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FocusPower(AbstractDungeon.player, 1), 1));
        addToBot(new ChannelAction(new Dark()));
    }
}