package prismaticmod.patches;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.AnimateOrbAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.actions.defect.EvokeWithoutRemovingOrbAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class MultichaosAction extends AbstractGameAction {
    private final boolean freeToPlayOnce;

    private final AbstractPlayer p;

    private final int energyOnUse;

    private final boolean upgraded;

    public MultichaosAction(AbstractPlayer p, int energyOnUse, boolean upgraded, boolean freeToPlayOnce) {
        this.p = p;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = AbstractGameAction.ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
        this.upgraded = upgraded;
        this.freeToPlayOnce = freeToPlayOnce;
    }

    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1)
            effect = this.energyOnUse;
        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }
        if (this.upgraded)
            effect++;
        if (effect > 0) {
            if (AbstractDungeon.player.hasOrb()) {
                for (int i = 0; i < effect - 1; i++)
                    addToBot(new EvokeWithoutRemovingOrbAction(1));
                addToBot(new AnimateOrbAction(1));
                addToBot(new EvokeOrbAction(1));
            }
            for (int i = 0; i < effect; i++) {
                addToBot(new ChannelAction(AbstractOrb.getRandomOrb(true)));
            }
            if (!this.freeToPlayOnce)
                this.p.energy.use(EnergyPanel.totalCount);
        }
        this.isDone = true;
    }
}
