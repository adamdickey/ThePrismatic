package prismaticmod.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import prismaticmod.powers.LockOn2Power;


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
        this.tips.add(new PowerTip("Lock-On+",
                "Lock-On+ targets receive #b50% more damage from non-Attack sources."));
    }

    @Override
    public String getDescription() {
        return potionStrings.DESCRIPTIONS[0] + potionPotency + potionStrings.DESCRIPTIONS[1];
    }

    @Override
    public void use(AbstractCreature abstractCreature) {
        addToBot(new ApplyPowerAction(abstractCreature, player, new LockOn2Power(abstractCreature, potency), potency));
    }
}
