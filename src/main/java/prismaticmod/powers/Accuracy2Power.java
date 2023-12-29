package prismaticmod.powers;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static prismaticmod.BasicMod.makeID;

public class Accuracy2Power extends BasePower{

    public static final String ID = makeID("Accuracy");

    public Accuracy2Power(int amount) {
        super(ID, PowerType.BUFF, false, player, player, amount, true);
        this.amount = amount;
        updateExistingCards();
    }
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
    private void updateExistingCards() {
        for (AbstractCard c : player.hand.group) {
            if (c.cost == 0 || c.freeToPlayOnce) {
                c.baseDamage += this.amount;
            }
        }
        for (AbstractCard c : player.drawPile.group) {
            if (c.cost == 0 || c.freeToPlayOnce) {
                c.baseDamage += this.amount;
            }
        }
        for (AbstractCard c : player.discardPile.group) {
            if (c.cost == 0 || c.freeToPlayOnce) {
                c.baseDamage += this.amount;
            }
        }
        for (AbstractCard c : player.exhaustPile.group) {
            if (c.cost == 0 || c.freeToPlayOnce) {
                c.baseDamage += this.amount;
            }
        }
    }
    @Override
    public void onDrawOrDiscard() {
        for (AbstractCard c : player.hand.group) {
            if (c.cost == 0 || c.freeToPlayOnce) {
                int baseDamage = baseDamage(c);
                c.baseDamage = baseDamage + this.amount;
            }
        }
    }
    public int baseDamage(AbstractCard c){
        if(c instanceof com.megacrit.cardcrawl.cards.purple.JustLucky){
            return c.baseDamage;
        }
        return 4;
    }
}
