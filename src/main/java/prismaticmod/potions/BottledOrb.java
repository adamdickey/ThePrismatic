package prismaticmod.potions;

import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import prismaticmod.relics.OrbSlot;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static prismaticmod.BasicMod.makeID;
import static theprismatic.ThePrismatic.Enums.Prismatic;

public class BottledOrb extends BasePotion{
    public static final String ID = makeID("Bottled Orb");
    public BottledOrb(){
        super(ID, 1, PotionRarity.UNCOMMON, PotionSize.SPHERE, PotionColor.WHITE);
        playerClass = Prismatic;
    }

    @Override
    public String getDescription() {
        if (AbstractDungeon.player == null || !AbstractDungeon.player.hasRelic("SacredBark")) {
            return potionStrings.DESCRIPTIONS[0] + 1 + potionStrings.DESCRIPTIONS[1];
        } else {
            return potionStrings.DESCRIPTIONS[0] + 2 + potionStrings.DESCRIPTIONS[2];
        }
    }
    public boolean canUse() {
        if (AbstractDungeon.actionManager.turnHasEnded &&
                (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT){
            return false;
        }
        if ((AbstractDungeon.getCurrRoom()).event != null &&
                (AbstractDungeon.getCurrRoom()).event instanceof com.megacrit.cardcrawl.events.shrines.WeMeetAgain){
            return false;
        }
        return true;
    }

    @Override
    public void use(AbstractCreature abstractCreature) {
        if (AbstractDungeon.player == null || AbstractDungeon.player.hasRelic("SacredBark")){
            if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT){
                addToBot(new IncreaseMaxOrbAction(1));
            }
            if(!player.hasRelic(OrbSlot.ID)){
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, new OrbSlot());
            } else {
                player.getRelic(OrbSlot.ID).counter++;
            }
        }
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT){
            addToBot(new IncreaseMaxOrbAction(1));
        }
        if(!player.hasRelic(OrbSlot.ID)){
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, new OrbSlot());
        } else {
            player.getRelic(OrbSlot.ID).counter++;
        }
    }
}
