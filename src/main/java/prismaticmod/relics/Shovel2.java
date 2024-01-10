package prismaticmod.relics;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.Girya;
import com.megacrit.cardcrawl.relics.PeacePipe;
import com.megacrit.cardcrawl.relics.Shovel;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.ui.campfire.DigOption;
import theprismatic.ThePrismatic;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;

import static prismaticmod.BasicMod.makeID;

public class Shovel2 extends BaseRelic {
    private static final String NAME = "Shovel"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.UNCOMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.FLAT; //The sound played when the relic is clicked.

    public Shovel2() {
        super(ID, NAME, ThePrismatic.Enums.CARD_COLOR, RARITY, SOUND);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public boolean canSpawn() {
        if (AbstractDungeon.floorNum >= 48 && !Settings.isEndless)
            return false;
        int campfireRelicCount = 0;
        for (AbstractRelic r : AbstractDungeon.player.relics) {
            if (r instanceof PeacePipe || r instanceof Shovel || r instanceof Girya || r instanceof Shovel2)
                campfireRelicCount++;
        }
        return (campfireRelicCount < 2);
    }
    public void addCampfireOption(ArrayList<AbstractCampfireOption> options) {
        options.add(new DigOption());
    }
}