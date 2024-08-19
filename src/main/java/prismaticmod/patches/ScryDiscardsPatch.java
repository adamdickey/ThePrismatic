package prismaticmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;

import javassist.CannotCompileException;
import javassist.CtBehavior;
import prismaticmod.cards.*;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static theprismatic.ThePrismatic.Enums.Prismatic;

@SpirePatch2(clz = ScryAction.class, method = "update")
public class ScryDiscardsPatch {
    @SpireInsertPatch(locator = Locator.class)
    public static void Insert() {
        if(player.chosenClass == Prismatic){
            for(AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards){
                GameActionManager.incrementDiscard(false);
                if(c instanceof DeadlyPoison2 || c instanceof Reflex2 || c instanceof Tactician2 || c instanceof Weave2 || c instanceof Vigilance2 || c instanceof Eruption2 || c instanceof Sentinel2 || c instanceof Consume2 || c instanceof DodgeAndRoll2) {
                    c.triggerOnManualDiscard();
                }
            }
        }
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "clear");
            return LineFinder.findInOrder(ctMethodToPatch, methodCallMatcher);
        }
    }
}
