package prismaticmod.powers;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static prismaticmod.BasicMod.makeID;

public class OnARollPower extends BasePower {

    public static final String ID = makeID("On a Roll");

    public OnARollPower(int amount) {
        super(ID, PowerType.BUFF, false, player, player, amount, true);
        this.amount = amount;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if(card.type == AbstractCard.CardType.ATTACK){
            addToBot(new ApplyPowerAction(player, player, new StrengthPower(player, this.amount)));
            addToBot(new ApplyPowerAction(player, player, new LoseStrengthPower(player, this.amount)));
            addToBot(new ApplyPowerAction(player, player, new DexterityPower(player, this.amount)));
            addToBot(new ApplyPowerAction(player, player, new LoseDexterityPower(player, this.amount)));
        }
    }
}