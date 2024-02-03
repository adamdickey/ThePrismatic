package prismaticmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.*;
import prismaticmod.util.GeneralUtils;
import prismaticmod.util.TextureLoader;

import static prismaticmod.BasicMod.makeID;

public class OnARollPower extends TwoAmountPower {
    public AbstractCreature source;

    public static String POWER_ID = makeID("On A Roll");
    private static final PowerStrings getPowerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = getPowerStrings.NAME;
    private static final String[] powerStrings = getPowerStrings.DESCRIPTIONS;

    public OnARollPower(AbstractCreature owner, int strengthAmount, int dexAmount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = dexAmount;
        this.source = owner;
        this.type = AbstractPower.PowerType.BUFF;
        this.isTurnBased = false;
        this.amount2 = strengthAmount;
        updateDescription();
        loadImage();
    }
    public void updateDescription() {
        this.description = powerStrings[0] + this.amount2 + powerStrings[1] +
                this.amount + powerStrings[2];
    }
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += 1;
        this.amount2 += stackAmount;
    }
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(card.type == AbstractCard.CardType.ATTACK){
            addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, this.amount2), this.amount2, true));
            addToBot(new ApplyPowerAction(this.owner, this.owner, new LoseStrengthPower(this.owner, this.amount2), this.amount2, true));
            addToBot(new ApplyPowerAction(this.owner, this.owner, new DexterityPower(this.owner, this.amount), this.amount, true));
            addToBot(new ApplyPowerAction(this.owner, this.owner, new LoseDexterityPower(this.owner, this.amount), this.amount, true));
        }
    }
    public void loadImage(){
        String unPrefixed = GeneralUtils.removePrefix(ID);
        Texture normalTexture = TextureLoader.getPowerTexture(unPrefixed);
        Texture hiDefImage = TextureLoader.getHiDefPowerTexture(unPrefixed);
        if (hiDefImage != null)
        {
            region128 = new TextureAtlas.AtlasRegion(hiDefImage, 0, 0, hiDefImage.getWidth(), hiDefImage.getHeight());
            if (normalTexture != null)
                region48 = new TextureAtlas.AtlasRegion(normalTexture, 0, 0, normalTexture.getWidth(), normalTexture.getHeight());
        }
        else if (normalTexture != null)
        {
            this.img = normalTexture;
            region48 = new TextureAtlas.AtlasRegion(normalTexture, 0, 0, normalTexture.getWidth(), normalTexture.getHeight());
        }
    }
}
