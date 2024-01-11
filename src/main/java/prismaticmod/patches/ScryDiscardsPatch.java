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
import prismaticmod.cards.DeadlyPoison2;
import prismaticmod.cards.Reflex2;
import prismaticmod.cards.Tactician2;
import prismaticmod.cards.Weave2;

@SpirePatch2(clz = ScryAction.class, method = "update")
public class ScryDiscardsPatch {
    @SpireInsertPatch(locator = Locator.class)
    public static void Insert() {
        for(AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards){
            GameActionManager.incrementDiscard(false);
            if(c instanceof DeadlyPoison2 || c instanceof Reflex2 || c instanceof Tactician2 || c instanceof Weave2){
                c.triggerOnManualDiscard();
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
