package prismaticmod.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import prismaticmod.powers.TargetedPower;


import static com.megacrit.cardcrawl.core.Settings.GameLanguage.*;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static prismaticmod.BasicMod.makeID;
import static theprismatic.ThePrismatic.Enums.Prismatic;

public class TargetingPotion extends BasePotion{
    public static final String ID = makeID("Targeting Potion");
    private static final int potionPotency = 5;
    private static final Color LIQUID_COLOR = CardHelper.getColor(0, 0, 255);
    private static final Color HYBRID_COLOR = CardHelper.getColor(0, 0, 255);
    private static final Color SPOTS_COLOR = null;
    public TargetingPotion(){
        super(ID, potionPotency, AbstractPotion.PotionRarity.UNCOMMON, AbstractPotion.PotionSize.H, LIQUID_COLOR, HYBRID_COLOR, SPOTS_COLOR);
        playerClass = Prismatic;
        isThrown = true;
        targetRequired = true;
    }
    @Override
    public void addAdditionalTips() {
        if(Settings.language == ENG){
            this.tips.add(new PowerTip("Target",
                    "Targeted creatures take #b50% more damage from non-Attack sources."));
        }
        else if(Settings.language == JPN){
            this.tips.add(new PowerTip("ターゲット",
                    "アタック以外で受けるダメージが #b50% 増加する。"));
        }
        else if(Settings.language == ZHS){
            this.tips.add(new PowerTip("目标",
                    "受目标影响的敌人受到的非攻击伤害增加 #b50% 。"));
        }
    }

    @Override
    public String getDescription() {
        if (AbstractDungeon.player == null || !AbstractDungeon.player.hasRelic("SacredBark")) {
            return potionStrings.DESCRIPTIONS[0] + potionPotency + potionStrings.DESCRIPTIONS[1];
        } else {
            return potionStrings.DESCRIPTIONS[0] + 2*potionPotency + potionStrings.DESCRIPTIONS[1];
        }
    }

    @Override
    public void use(AbstractCreature abstractCreature) {
        addToBot(new ApplyPowerAction(abstractCreature, player, new TargetedPower(abstractCreature, potency), potency));
    }
}
