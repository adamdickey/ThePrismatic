package prismaticmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class BaneAction extends AbstractGameAction {
    private final AbstractMonster m;
    private final int magicNumber;



    public BaneAction(AbstractMonster m, int magicNumber) {
        this.m = m;
        this.magicNumber = magicNumber;
    }

    public void update() {
        int debuffs = 0;
        for(AbstractPower power : m.powers){
            if(power.type == AbstractPower.PowerType.DEBUFF && !power.ID.equals("Shackled")){
                debuffs++;
            }
        }
        if(debuffs >= 2){
            addToBot(new ApplyPowerAction(m, player, new PoisonPower(m, player, magicNumber)));
        }
        this.isDone = true;
    }
}
