package prismaticmod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.watcher.FreeAttackPower;
import theprismatic.ThePrismatic;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static prismaticmod.BasicMod.makeID;

public class WristBlade2 extends BaseRelic {
    private static final String NAME = "Wrist Blade"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.BOSS; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.FLAT; //The sound played when the relic is clicked.

    public WristBlade2() {
        super(ID, NAME, ThePrismatic.Enums.CARD_COLOR, RARITY, SOUND);
    }
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 4 + this.DESCRIPTIONS[1] + 1 + this.DESCRIPTIONS[2];
    }
    @Override
    public float atDamageModify(float damage, AbstractCard c) {
        if (c.costForTurn == 0 || (c.freeToPlayOnce && c.cost != -1) || player.hasPower(FreeAttackPower.POWER_ID)){
            return damage + 4.0F;
        }
        return damage;
    }
    private boolean zeroCost;
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        zeroCost = c.costForTurn == 0 || (c.freeToPlayOnce && c.cost != -1) || player.hasPower(FreeAttackPower.POWER_ID);
    }
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (damageAmount > 0 && target != player && info.type == DamageInfo.DamageType.NORMAL && !zeroCost) {
            flash();
            addToTop(new ApplyPowerAction(target, player, new PoisonPower(target, player, 1), 1, true));
        }
    }
}