package prismaticmod.powers;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.powers.watcher.FreeAttackPower;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static prismaticmod.BasicMod.makeID;

public class Accuracy2Power extends BasePower {

    public static final String ID = makeID("Accuracy");

    public Accuracy2Power(int amount) {
        super(ID, PowerType.BUFF, false, player, player, amount, true);
        this.amount = amount;
//        atDamageModify();
        //updateExisting0Costs();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        //updateExisting0Costs();
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        if ((card.costForTurn == 0 || (card.freeToPlayOnce && card.cost != -1)) || player.hasPower(FreeAttackPower.POWER_ID)) {
            return super.atDamageGive(damage + this.amount, type, card);
        } else {
            return super.atDamageGive(damage, type, card);
        }
    }
}
//    public float atDamageModify(float damage, AbstractCard c) {
//        if (c.costForTurn == 0 || (c.freeToPlayOnce && c.cost != -1))
//            return damage + 4.0F;
//        return damage;
//    }
//    private void updateExistingCards() {
//        for (AbstractCard c : player.hand.group) {
//            if (c.cost == 0 || c.freeToPlayOnce) {
//                c.baseDamage += this.amount;
//            }
//        }
//        for (AbstractCard c : player.drawPile.group) {
//            if (c.cost == 0 || c.freeToPlayOnce) {
//                c.baseDamage += this.amount;
//            }
//        }
//        for (AbstractCard c : player.discardPile.group) {
//            if (c.cost == 0 || c.freeToPlayOnce) {
//                c.baseDamage += this.amount;
//            }
//        }
//        for (AbstractCard c : player.exhaustPile.group) {
//            if (c.cost == 0 || c.freeToPlayOnce) {
//                c.baseDamage += this.amount;
//            }
//        }
//    }
//    @Override
//    public void onDrawOrDiscard() {
//        for (AbstractCard c : player.hand.group) {
//            if ((c.cost == 0 || c.freeToPlayOnce) && c.type == AbstractCard.CardType.ATTACK) {
//                int baseDamage = baseDamage(c);
//                c.baseDamage = baseDamage + this.amount;
//            } else {
//                c.baseDamage = baseDamage(c);
//            }
//        }
//    }
//    public int baseDamage(AbstractCard c){
//        if(!c.upgraded) {
//            if(c instanceof Blizzard || c instanceof Pummel){
//                return 2;
//            }
//            if(c instanceof BeamCell || c instanceof Claw ||
//                    c instanceof FlashOfSteel || c instanceof GoForTheEyes ||
//                    c instanceof JustLucky || c instanceof Neutralize ||
//                    c instanceof Neutralize2 || c instanceof RiddleWithHoles ||
//                    c instanceof SwordBoomerang || c instanceof Tantrum ||
//                    c instanceof Barrage2 || c instanceof Claw2 ||
//                    c instanceof GoForTheEyes2){
//                return 3;
//            }
//            if(c instanceof Barrage || c instanceof DaggerSpray ||
//                    c instanceof EndlessAgony || c instanceof Flechettes ||
//                    c instanceof FlurryOfBlows || c instanceof FlyingSleeves ||
//                    c instanceof Reaper || c instanceof Shiv ||
//                    c instanceof ThunderClap || c instanceof Weave){
//                return 4;
//            }
//            if(c instanceof Consecrate || c instanceof Dropkick ||
//                    c instanceof FTL || c instanceof HeelHook ||
//                    c instanceof IronWave || c instanceof Ragnarok ||
//                    c instanceof TalkToTheHand || c instanceof TwinStrike ||
//                    c instanceof Whirlwind || c instanceof Bane2 ||
//                    c instanceof Dropkick2){
//                return 5;
//            }
//            if(c instanceof Anger || c instanceof CarveReality ||
//                    c instanceof ColdSnap || c instanceof Finisher ||
//                    c instanceof PerfectedStrike || c instanceof PoisonedStab ||
//                    c instanceof Slice || c instanceof Strike_Red ||
//                    c instanceof Strike_Green || c instanceof Strike_Blue ||
//                    c instanceof Strike_Purple || c instanceof SweepingBeam ||
//                    c instanceof Choke2 || c instanceof CunningStrike ||
//                    c instanceof DivineStrike || c instanceof EmptyStrike ||
//                    c instanceof ExhaustingStrike || c instanceof FrostyStrike ||
//                    c instanceof InsightfulStrike || c instanceof LootingStrike ||
//                    c instanceof RetainingStrike || c instanceof ScrapingStrike ||
//                    c instanceof ScryingStrike || c instanceof StormyStrike ||
//                    c instanceof StrikeForOne || c instanceof TargetingStrike ||
//                    c instanceof ToxicStrike || c instanceof VulnerableStrike ||
//                    c instanceof WeakeningStrike){
//                return 6;
//            }
//            if(c instanceof BallLightning || c instanceof Bane ||
//                    c instanceof Bite || c instanceof BowlingBash ||
//                    c instanceof CompileDriver || c instanceof CutThroughFate ||
//                    c instanceof Eviscerate || c instanceof FiendFire ||
//                    c instanceof FollowUp || c instanceof RecklessCharge ||
//                    c instanceof RipAndTear || c instanceof Scrape ||
//                    c instanceof Skewer || c instanceof SuckerPunch ||
//                    c instanceof SwiftStrike ||c instanceof ThunderStrike ||
//                    c instanceof Scrape2){
//                return 7;
//            }
//            if(c instanceof Bash || c instanceof LockOn ||
//                    c instanceof Cleave || c instanceof CrushJoints ||
//                    c instanceof DramaticEntrance || c instanceof FearNoEvil ||
//                    c instanceof FlyingKnee || c instanceof GlassKnife ||
//                    c instanceof QuickSlash || c instanceof SashWhip){
//                return 8;
//            }
//            if(c instanceof DaggerThrow || c instanceof EmptyFist ||
//                    c instanceof Eruption || c instanceof Expunger ||
//                    c instanceof Headbutt || c instanceof PommelStrike ||
//                    c instanceof Rebound || c instanceof Wallop ||
//                    c instanceof Eruption2){
//                return 9;
//            }
//            if(c instanceof AllForOne || c instanceof AllOutAttack ||
//                    c instanceof Dash || c instanceof DoomAndGloom ||
//                    c instanceof Feed || c instanceof LessonLearned ||
//                    c instanceof Melter || c instanceof ReachHeaven){
//                return 10;
//            }
//            if(c instanceof Backstab || c instanceof CoreSurge){
//                return 11;
//            }
//            if(c instanceof Brilliance || c instanceof Choke ||
//                    c instanceof Clothesline || c instanceof Conclude ||
//                    c instanceof MasterfulStab || c instanceof SearingBlow ||
//                    c instanceof Smite || c instanceof SneakyStrike ||
//                    c instanceof WildStrike || c instanceof SneakyStrike2 ||
//                    c instanceof WildStrike2){
//                return 12;
//            }
//            if(c instanceof DieDieDie || c instanceof Uppercut){
//                return 13;
//            }
//            if(c instanceof Clash || c instanceof HeavyBlade ||
//                    c instanceof Unload){
//                return 14;
//            }
//            if(c instanceof Hemokinesis || c instanceof Predator ||
//                    c instanceof Streamline || c instanceof WheelKick ||
//                    c instanceof Streamline2){
//                return 15;
//            }
//            if(c instanceof SeverSoul || c instanceof SeverSoul2){
//                return 16;
//            }
//            if(c instanceof BloodForBlood){
//                return 18;
//            }
//            if(c instanceof Carnage || c instanceof HandOfGreed ||
//                    c instanceof SandsOfTime || c instanceof ThroughViolence){
//                return 20;
//            }
//            if(c instanceof Immolate){
//                return 21;
//            }
//            if(c instanceof MeteorStrike || c instanceof Sunder){
//                return 24;
//            }
//            if(c instanceof Hyperbeam){
//                return 26;
//            }
//            if(c instanceof SignatureMove){
//                return 30;
//            }
//            if(c instanceof Bludgeon){
//                return 32;
//            }
//            if(c instanceof GrandFinale){
//                return 50;
//            }
//        } else {
//            if(c instanceof Pummel){
//                return 2;
//            }
//            if(c instanceof SwordBoomerang || c instanceof Tantrum ||
//                    c instanceof Blizzard){
//                return 3;
//            }
//            if(c instanceof BeamCell || c instanceof GoForTheEyes ||
//                    c instanceof JustLucky || c instanceof Neutralize ||
//                    c instanceof RiddleWithHoles || c instanceof GoForTheEyes2 ||
//                    c instanceof Neutralize2){
//                return 4;
//            }
//            if(c instanceof Claw || c instanceof Reaper ||
//                    c instanceof Barrage2 || c instanceof Claw2){
//                return 5;
//            }
//            if(c instanceof Barrage || c instanceof DaggerSpray ||
//                    c instanceof EndlessAgony || c instanceof FlashOfSteel ||
//                    c instanceof Flechettes || c instanceof FlurryOfBlows ||
//                    c instanceof FlyingSleeves || c instanceof FTL ||
//                    c instanceof PerfectedStrike || c instanceof Ragnarok ||
//                    c instanceof Shiv || c instanceof Weave){
//                return 6;
//            }
//            if(c instanceof IronWave || c instanceof TalkToTheHand ||
//                    c instanceof ThunderClap || c instanceof Bane2){
//                return 7;
//            }
//            if(c instanceof Anger || c instanceof Bite ||
//                    c instanceof Consecrate || c instanceof Dropkick ||
//                    c instanceof Finisher || c instanceof HeelHook ||
//                    c instanceof PoisonedStab || c instanceof Whirlwind ||
//                    c instanceof Choke2 || c instanceof Dropkick2){
//                return 8;
//            }
//            if(c instanceof ColdSnap || c instanceof CutThroughFate ||
//                    c instanceof Eruption || c instanceof Eviscerate ||
//                    c instanceof RipAndTear || c instanceof Slice ||
//                    c instanceof Strike_Red || c instanceof Strike_Green ||
//                    c instanceof Strike_Blue || c instanceof Strike_Purple ||
//                    c instanceof SuckerPunch || c instanceof ThunderStrike ||
//                    c instanceof SweepingBeam || c instanceof CunningStrike ||
//                    c instanceof DivineStrike || c instanceof EmptyStrike ||
//                    c instanceof Eruption2 || c instanceof ExhaustingStrike ||
//                    c instanceof FrostyStrike || c instanceof InsightfulStrike ||
//                    c instanceof LootingStrike || c instanceof RetainingStrike ||
//                    c instanceof ScrapingStrike || c instanceof ScryingStrike ||
//                    c instanceof StormyStrike || c instanceof StrikeForOne ||
//                    c instanceof TargetingStrike || c instanceof ToxicStrike ||
//                    c instanceof VulnerableStrike || c instanceof WeakeningStrike){
//                return 9;
//            }
//            if(c instanceof BallLightning || c instanceof Bane ||
//                    c instanceof Bash || c instanceof BowlingBash ||
//                    c instanceof CarveReality || c instanceof CompileDriver ||
//                    c instanceof CrushJoints || c instanceof FiendFire ||
//                    c instanceof PommelStrike || c instanceof RecklessCharge ||
//                    c instanceof SashWhip || c instanceof Scrape ||
//                    c instanceof Skewer || c instanceof SwiftStrike ||
//                    c instanceof Scrape2){
//                return 10;
//            }
//            if(c instanceof LockOn || c instanceof Cleave ||
//                    c instanceof FearNoEvil || c instanceof FlyingKnee ||
//                    c instanceof FollowUp){
//                return 11;
//            }
//            if(c instanceof Choke || c instanceof DaggerThrow ||
//                    c instanceof DramaticEntrance || c instanceof Feed ||
//                    c instanceof GlassKnife || c instanceof Headbutt ||
//                    c instanceof QuickSlash || c instanceof Rebound ||
//                    c instanceof Wallop){
//                return 12;
//            }
//            if(c instanceof Dash || c instanceof LessonLearned ||
//                    c instanceof Uppercut){
//                return 13;
//            }
//            if(c instanceof AllForOne || c instanceof AllOutAttack ||
//                    c instanceof Clothesline || c instanceof DoomAndGloom ||
//                    c instanceof EmptyFist || c instanceof HeavyBlade ||
//                    c instanceof Melter){
//                return 14;
//            }
//            if(c instanceof Backstab || c instanceof CoreSurge ||
//                    c instanceof Expunger || c instanceof ReachHeaven){
//                return 15;
//            }
//            if(c instanceof Brilliance || c instanceof Conclude ||
//                    c instanceof MasterfulStab || c instanceof SearingBlow ||
//                    c instanceof Smite || c instanceof SneakyStrike ||
//                    c instanceof SneakyStrike2){
//                return 16;
//            }
//            if(c instanceof DieDieDie || c instanceof WildStrike ||
//                    c instanceof WildStrike2){
//                return 17;
//            }
//            if(c instanceof Clash || c instanceof Unload){
//                return 18;
//            }
//            if(c instanceof Hemokinesis || c instanceof Predator ||
//                    c instanceof Streamline || c instanceof WheelKick ||
//                    c instanceof Streamline2){
//                return 20;
//            }
//            if(c instanceof BloodForBlood || c instanceof SeverSoul ||
//                    c instanceof SeverSoul2){
//                return 22;
//            }
//            if(c instanceof HandOfGreed){
//                return 25;
//            }
//            if(c instanceof SandsOfTime){
//                return 26;
//            }
//            if(c instanceof Carnage || c instanceof Immolate){
//                return 28;
//            }
//            if(c instanceof MeteorStrike || c instanceof ThroughViolence){
//                return 30;
//            }
//            if(c instanceof Sunder){
//                return 32;
//            }
//            if(c instanceof Hyperbeam){
//                return 34;
//            }
//            if(c instanceof SignatureMove){
//                return 40;
//            }
//            if(c instanceof Bludgeon){
//                return 42;
//            }
//            if(c instanceof GrandFinale){
//                return 60;
//            }
//        }
//        if(c instanceof BodySlam){
//            return player.currentBlock;
//        }
//        if(c instanceof MindBlast || c instanceof MindBlast2){
//            return player.drawPile.size();
//        }
//        //Doesn't work correctly
//        if(c instanceof RitualDagger || c instanceof Rampage ||
//                c instanceof WindmillStrike) {
//            return c.baseDamage;
//        }
//        return 6;
//    }
//}
