package prismaticmod.cards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.watcher.MarkPower;
import com.megacrit.cardcrawl.vfx.combat.PressurePointEffect;
import prismaticmod.powers.LockOn2Power;
import prismaticmod.util.CardStats;

public class PressurePoints2 extends BaseCard {
    public static final String ID = makeID("Pressure Points"); //makeID adds the mod ID, so the final ID will be something like "modID:MyCard"
    private static final CardStats info = new CardStats(
            CardColor.PURPLE, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.
    private static final int baseMagicNumber = 6;
    private static final int UPG_Number = 2;
    private static final int baseLockOn = 1;
    private static final int UPG_LockOn = 1;

    public PressurePoints2() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setMagic(baseMagicNumber, UPG_Number);
        setCustomVar("LockOn", baseLockOn, UPG_LockOn);
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new MarkPower(m, magicNumber), magicNumber));
        addToBot(new ApplyPowerAction(m, p, new LockOn2Power(m, customVar("LockOn")), customVar("LockOn")));
        pressurePointsAction(p, m);
    }
    private void pressurePointsAction(AbstractPlayer p, AbstractMonster m){
        if (m != null) {
            addToBot(new VFXAction(new PressurePointEffect(m.hb.cX, m.hb.cY)));
        }
        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
            if(mo == m) {
                if (mo.hasPower(MarkPower.POWER_ID)) {
                    if (!mo.hasPower(ArtifactPower.POWER_ID)) {
                        addToBot(new DamageAction(mo, new DamageInfo(p, mo.getPower(MarkPower.POWER_ID).amount + magicNumber, DamageInfo.DamageType.HP_LOSS)));
                    } else {
                        addToBot(new DamageAction(mo, new DamageInfo(p, mo.getPower(MarkPower.POWER_ID).amount, DamageInfo.DamageType.HP_LOSS)));
                    }
                } else {
                    if (!mo.hasPower(ArtifactPower.POWER_ID)) {
                        addToBot(new DamageAction(mo, new DamageInfo(p, magicNumber, DamageInfo.DamageType.HP_LOSS)));
                    }
                }
            }
            else{
                addToBot(new DamageAction(mo, new DamageInfo(p, mo.getPower(MarkPower.POWER_ID).amount, DamageInfo.DamageType.HP_LOSS)));
            }
        }
    }
}
