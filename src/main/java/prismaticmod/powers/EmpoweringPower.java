package prismaticmod.powers;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static prismaticmod.BasicMod.makeID;

public class EmpoweringPower extends BasePower {

    public static final String ID = makeID("Empowered");

    public EmpoweringPower(AbstractCreature owner, int amount) {
        super(ID, PowerType.BUFF, true, owner, player, amount, true);
        this.amount = amount;
        updateExistingPowers();
    }

    public void updateDescription() {
        if(this.amount == 1){
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        updateExistingPowers();
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.POWER && !card.purgeOnUse && this.amount > 0) {
            flash();
            this.amount--;
            updateDescription();
            if (this.amount == 0){
                addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, EmpoweringPower.ID));
                unUpdateExistingPowers(card);
            }
        }
    }
    public void atStartOfTurn(){
        addToBot(new RemoveSpecificPowerAction(player, player, this));
        unUpdateExistingPowers();
    }
    private void updateExistingPowers() {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.type == AbstractCard.CardType.POWER && !c.isCostModified && c.costForTurn != 0) {
                c.costForTurn--;
                c.isCostModified = true;
            }
        }
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c.type == AbstractCard.CardType.POWER && !c.isCostModified && c.costForTurn != 0) {
                c.costForTurn--;
                c.isCostModified = true;
            }
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c.type == AbstractCard.CardType.POWER && !c.isCostModified && c.costForTurn != 0) {
                c.costForTurn--;
                c.isCostModified = true;
            }
        }
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (c.type == AbstractCard.CardType.POWER && !c.isCostModified && c.costForTurn != 0) {
                c.costForTurn--;
                c.isCostModified = true;
            }
        }
    }

    public void onDrawOrDiscard() {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.type == AbstractCard.CardType.POWER && !c.isCostModified && c.costForTurn != 0) {
                c.costForTurn--;
                c.isCostModified = true;
            }
        }
    }
    private void unUpdateExistingPowers(AbstractCard powerPlayed) {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.type == AbstractCard.CardType.POWER && c.isCostModified && c != powerPlayed) {
                c.costForTurn++;
                c.isCostModified = false;
            }
        }
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c.type == AbstractCard.CardType.POWER && c.isCostModified) {
                c.costForTurn++;
                c.isCostModified = false;
            }
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c.type == AbstractCard.CardType.POWER && c.isCostModified) {
                c.costForTurn++;
                c.isCostModified = false;
            }
        }
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (c.type == AbstractCard.CardType.POWER && c.isCostModified) {
                c.costForTurn++;
                c.isCostModified = false;
            }
        }
    }
    private void unUpdateExistingPowers() {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.type == AbstractCard.CardType.POWER && c.isCostModified) {
                c.costForTurn++;
                c.isCostModified = false;
            }
        }
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c.type == AbstractCard.CardType.POWER && c.isCostModified) {
                c.costForTurn++;
                c.isCostModified = false;
            }
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c.type == AbstractCard.CardType.POWER && c.isCostModified) {
                c.costForTurn++;
                c.isCostModified = false;
            }
        }
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (c.type == AbstractCard.CardType.POWER && c.isCostModified) {
                c.costForTurn++;
                c.isCostModified = false;
            }
        }
    }
}