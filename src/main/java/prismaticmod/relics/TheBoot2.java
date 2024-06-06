package prismaticmod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.PoisonPower;
import theprismatic.ThePrismatic;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static prismaticmod.BasicMod.makeID;

public class TheBoot2 extends BaseRelic {
    private static final String NAME = "The Boot"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.COMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.FLAT; //The sound played when the relic is clicked.

    public TheBoot2() {
        super(ID, NAME, ThePrismatic.Enums.CARD_COLOR, RARITY, SOUND);
    }
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (damageAmount < 5 && damageAmount > 0 && target != player && info.type == DamageInfo.DamageType.NORMAL) {
            flash();
            addToTop(new ApplyPowerAction(target, player, new PoisonPower(target, player, (5-damageAmount)), (5-damageAmount), true));
        }
    }
}