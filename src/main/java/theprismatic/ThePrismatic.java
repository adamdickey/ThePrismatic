package theprismatic;

import basemod.abstracts.CustomEnergyOrb;
import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.cards.blue.Stack;
import com.megacrit.cardcrawl.cards.colorless.*;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.cards.purple.*;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import prismaticmod.cards.*;
import prismaticmod.relics.BurningRing;

import java.util.*;

import static com.badlogic.gdx.math.MathUtils.random;
import static prismaticmod.BasicMod.characterPath;
import static prismaticmod.BasicMod.makeID;
import static theprismatic.ThePrismatic.Enums.*;

public class ThePrismatic extends CustomPlayer {
    //Stats
    public static final int ENERGY_PER_TURN = 3;
    public static final int MAX_HP = 74;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 2;

    //Strings
    private static final String ID = makeID("Prismatic"); //This should match whatever you have in the CharacterStrings.json file
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;
    private static final String[] orbPath = new String[]{"prismaticmod/images/character/energy orb/layer1.png",
            "prismaticmod/images/character/energy orb/layer2.png",
            "prismaticmod/images/character/energy orb/layer3.png",
            "prismaticmod/images/character/energy orb/layer4.png",
            "prismaticmod/images/character/energy orb/layer5.png",
            "prismaticmod/images/character/energy orb/layer6.png",
            "prismaticmod/images/character/energy orb/layer1d.png",
            "prismaticmod/images/character/energy orb/layer2d.png",
            "prismaticmod/images/character/energy orb/layer3d.png",
            "prismaticmod/images/character/energy orb/layer4d.png",
            "prismaticmod/images/character/energy orb/layer5d.png"};
    private static final String orbVfxPath = "prismaticmod/images/character/energy orb/vfx.png";

    //Image file paths
    private static final String SHOULDER_1 = characterPath("shoulder.png"); //Shoulder 1 and 2 are used at rest sites.
    private static final String SHOULDER_2 = characterPath("shoulder2.png");
    private static final String CORPSE = characterPath("corpse.png"); //Corpse is when you die.

    public static class Enums {
        //These are used to identify your character, as well as your character's card color.
        //Library color is basically the same as card color, but you need both because that's how the game was made.
        @SpireEnum
        public static AbstractPlayer.PlayerClass Prismatic;
        @SpireEnum(name = "CHARACTER_WHITE_COLOR") // These two MUST match. Change it to something unique for your character.
        public static AbstractCard.CardColor CARD_COLOR;
        @SpireEnum(name = "CHARACTER_WHITE_COLOR") @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;

        @SpireEnum(name = "Red Cards") // These two MUST match. Change it to something unique for your character.
        public static AbstractCard.CardColor Red;
        @SpireEnum(name = "Red Cards") @SuppressWarnings("unused")
        public static CardLibrary.LibraryType Red_Color;
        @SpireEnum(name = "Green Cards") // These two MUST match. Change it to something unique for your character.
        public static AbstractCard.CardColor Green;
        @SpireEnum(name = "Green Cards") @SuppressWarnings("unused")
        public static CardLibrary.LibraryType Green_Color;
        @SpireEnum(name = "Blue Cards") // These two MUST match. Change it to something unique for your character.
        public static AbstractCard.CardColor Blue;
        @SpireEnum(name = "Blue Cards") @SuppressWarnings("unused")
        public static CardLibrary.LibraryType Blue_Color;
        @SpireEnum(name = "Purple Cards") // These two MUST match. Change it to something unique for your character.
        public static AbstractCard.CardColor Purple;
        @SpireEnum(name = "Purple Cards") @SuppressWarnings("unused")
        public static CardLibrary.LibraryType Purple_Color;
        @SpireEnum(name = "Colorless Cards") // These two MUST match. Change it to something unique for your character.
        public static AbstractCard.CardColor Colorless;
        @SpireEnum(name = "Colorless Cards") @SuppressWarnings("unused")
        public static CardLibrary.LibraryType Colorless_Color;
    }
    public ArrayList<AbstractCard> getCardPool(ArrayList<AbstractCard> tmpPool) {
        CardLibrary.addCardsIntoPool(tmpPool, CARD_COLOR);
        tmpPool.add(new Anger());
        tmpPool.add(new Armaments());
        tmpPool.add(new Barricade());
        tmpPool.add(new BattleTrance());
        tmpPool.add(new Berserk2());
        tmpPool.add(new BloodForBlood());
        tmpPool.add(new Bloodletting());
        tmpPool.add(new Bludgeon());
        tmpPool.add(new BodySlam());
        tmpPool.add(new Brutality());
        tmpPool.add(new BurningPact());
        tmpPool.add(new Carnage());
        tmpPool.add(new Clash());
        tmpPool.add(new Cleave());
        tmpPool.add(new Clothesline2());
        //tmpPool.add(new Combust());
        tmpPool.add(new Corruption());
        tmpPool.add(new DarkEmbrace2());
        tmpPool.add(new DemonForm());
        tmpPool.add(new Disarm());
        tmpPool.add(new DoubleTap2());
        tmpPool.add(new Dropkick2());
        tmpPool.add(new DualWield());
        tmpPool.add(new Entrench2());
        //tmpPool.add(new Evolve());
        tmpPool.add(new Exhume());
        tmpPool.add(new Feed());
        tmpPool.add(new FeelNoPain());
        tmpPool.add(new FiendFire());
        tmpPool.add(new FieryEvolution()); //tmpPool.add(new FireBreathing());
        tmpPool.add(new FlameBarrier());
        tmpPool.add(new Flex());
        tmpPool.add(new GhostlyArmor());
        tmpPool.add(new Havoc2());
        tmpPool.add(new Headbutt());
        tmpPool.add(new HeavyBlade());
        tmpPool.add(new Hemokinesis());
        tmpPool.add(new Immolate());
        tmpPool.add(new Impervious());
        tmpPool.add(new InfernalBlade2());
        tmpPool.add(new Inflame());
        tmpPool.add(new Intimidate2());
        tmpPool.add(new IronWave());
        tmpPool.add(new Juggernaut2());
        tmpPool.add(new LimitBreak());
        //tmpPool.add(new Metallicize());
        tmpPool.add(new Offering());
        //tmpPool.add(new PerfectedStrike());
        tmpPool.add(new PommelStrike());
        tmpPool.add(new PowerThrough());
        tmpPool.add(new Pummel());
        tmpPool.add(new Rage());
        tmpPool.add(new Rampage());
        tmpPool.add(new Reaper());
        tmpPool.add(new RecklessCharge());
        tmpPool.add(new MetallicRupture());
        //tmpPool.add(new SearingBlow());
        tmpPool.add(new SecondWind());
        tmpPool.add(new SeeingRed());
        tmpPool.add(new Sentinel());
        tmpPool.add(new SeverSoul2());
        tmpPool.add(new Shockwave2());
        tmpPool.add(new ShrugItOff());
        tmpPool.add(new SpotWeakness());
        tmpPool.add(new SwordBoomerang());
        tmpPool.add(new ThunderClap2());
        tmpPool.add(new TrueGrit());
        tmpPool.add(new TwinStrike());
        tmpPool.add(new Uppercut2());
        tmpPool.add(new Warcry());
        tmpPool.add(new Whirlwind());
        tmpPool.add(new WildStrike2());
        tmpPool.add(new Accuracy2());
        tmpPool.add(new Acrobatics());
        tmpPool.add(new Adrenaline());
        tmpPool.add(new AfterImage());
        tmpPool.add(new Alchemize());
        tmpPool.add(new AllOutAttack());
        tmpPool.add(new AThousandCuts2());
        tmpPool.add(new Backflip());
        tmpPool.add(new Backstab());
        tmpPool.add(new Bane2());
        tmpPool.add(new BladeDance2());
        tmpPool.add(new Blur());
        tmpPool.add(new BouncingFlask());
        tmpPool.add(new BulletTime());
        tmpPool.add(new Burst2());
        tmpPool.add(new CalculatedGamble());
        //tmpPool.add(new Caltrops());
        tmpPool.add(new Catalyst2());
        tmpPool.add(new Choke2());
        tmpPool.add(new CloakAndDagger());
        tmpPool.add(new Concentrate());
        tmpPool.add(new CorpseExplosion());
        tmpPool.add(new CripplingPoison());
        tmpPool.add(new DaggerSpray());
        tmpPool.add(new DaggerThrow());
        tmpPool.add(new Dash());
        tmpPool.add(new DeadlyPoison2());
        //tmpPool.add(new Deflect());
        tmpPool.add(new DieDieDie());
        tmpPool.add(new Distraction2());
        tmpPool.add(new DodgeAndRoll());
        tmpPool.add(new Doppelganger2());
        tmpPool.add(new EndlessAgony());
        tmpPool.add(new Envenom2());
        tmpPool.add(new EscapePlan());
        tmpPool.add(new Eviscerate2());
        tmpPool.add(new Expertise());
        tmpPool.add(new Finisher());
        tmpPool.add(new Flechettes());
        tmpPool.add(new FlyingKnee());
        tmpPool.add(new Footwork());
        tmpPool.add(new GlassKnife());
        //tmpPool.add(new GrandFinale());
        //tmpPool.add(new HeelHook());
        tmpPool.add(new InfiniteBlades2());
        tmpPool.add(new LegSweep());
        tmpPool.add(new Malaise());
        //tmpPool.add(new MasterfulStab());
        tmpPool.add(new Neutralize2());
        tmpPool.add(new Nightmare());
        //tmpPool.add(new NoxiousFumes());
        tmpPool.add(new Outmaneuver());
        tmpPool.add(new PhantasmalKiller());
        tmpPool.add(new PiercingWail());
        tmpPool.add(new PoisonedStab());
        tmpPool.add(new Predator());
        tmpPool.add(new Prepared2());
        tmpPool.add(new QuickSlash());
        tmpPool.add(new Reflex2());
        tmpPool.add(new RiddleWithHoles());
        tmpPool.add(new Setup2());
        tmpPool.add(new Skewer());
        tmpPool.add(new Slice2());
        tmpPool.add(new SneakyStrike2());
        tmpPool.add(new StormOfSteel2());
        tmpPool.add(new SuckerPunch());
        tmpPool.add(new Tactician2());
        tmpPool.add(new Terror());
        tmpPool.add(new ToolsOfTheTrade2());
        tmpPool.add(new Unload());
        tmpPool.add(new WellLaidPlans());
        tmpPool.add(new WraithForm2());
        tmpPool.add(new Aggregate());
        tmpPool.add(new AllForOne());
        tmpPool.add(new Amplify2());
        tmpPool.add(new AutoShields());
        tmpPool.add(new BallLightning());
        tmpPool.add(new Barrage2());
        tmpPool.add(new BeamCell());
        tmpPool.add(new BiasedCognition());
        tmpPool.add(new Blizzard2());
        tmpPool.add(new BootSequence());
        tmpPool.add(new Buffer());
        tmpPool.add(new Capacitor2());
        tmpPool.add(new Chaos());
        tmpPool.add(new Chill());
        tmpPool.add(new Claw2());
        tmpPool.add(new ColdSnap());
        tmpPool.add(new CompileDriver2());
        tmpPool.add(new ConserveBattery());
        tmpPool.add(new Consume2());
        tmpPool.add(new Coolheaded());
        tmpPool.add(new CoreSurge());
        tmpPool.add(new CreativeAI());
        tmpPool.add(new Darkness2());
        tmpPool.add(new Defragment2());
        tmpPool.add(new DoomWillBloom()); //tmpPool.add(new DoomAndGloom());
        tmpPool.add(new DoubleEnergy());
        tmpPool.add(new Dualcast2());
        tmpPool.add(new EchoForm2());
        tmpPool.add(new Electrodynamics());
        tmpPool.add(new Fission2());
        tmpPool.add(new ForceField());
        tmpPool.add(new FTL());
        tmpPool.add(new Fusion());
        tmpPool.add(new GeneticAlgorithm());
        tmpPool.add(new Glacier());
        tmpPool.add(new GoForTheEyes2());
        tmpPool.add(new Heatsinks2());
        tmpPool.add(new HelloWorld2());
        tmpPool.add(new Hologram());
        //tmpPool.add(new Hyperbeam());
        tmpPool.add(new Leap());
        tmpPool.add(new Bullseye2()); //tmpPool.add(new LockOn());
        tmpPool.add(new Loop());
        tmpPool.add(new MachineLearning());
        tmpPool.add(new Melter());
        tmpPool.add(new MeteorStrike());
        //tmpPool.add(new MultiCast());
        tmpPool.add(new Overclock());
        tmpPool.add(new Rainbow2());
        tmpPool.add(new Reboot());
        tmpPool.add(new Rebound());
        //tmpPool.add(new Recursion());
        tmpPool.add(new Recycle());
        tmpPool.add(new ReinforcedBody());
        //tmpPool.add(new Reprogram());
        tmpPool.add(new RipAndTear());
        tmpPool.add(new Scrape2());
        tmpPool.add(new Seek());
        tmpPool.add(new SelfRepair());
        tmpPool.add(new Skim());
        tmpPool.add(new Stack());
        tmpPool.add(new StaticDischarge2());
        tmpPool.add(new SteamBarrier());
        tmpPool.add(new Storm2());
        tmpPool.add(new Streamline2());
        tmpPool.add(new Sunder());
        tmpPool.add(new SweepingBeam());
        tmpPool.add(new Tempest2());
        //tmpPool.add(new ThunderStrike());
        tmpPool.add(new Turbo());
        tmpPool.add(new Equilibrium());
        tmpPool.add(new WhiteNoise2());
        tmpPool.add(new Zap());
        //tmpPool.add(new Alpha());
        tmpPool.add(new BattleHymn());
        tmpPool.add(new Blasphemy());
        tmpPool.add(new BowlingBash());
        tmpPool.add(new Brilliance());
        tmpPool.add(new CarveReality());
        tmpPool.add(new Collect());
        tmpPool.add(new Conclude());
        //tmpPool.add(new ConjureBlade());
        tmpPool.add(new Consecrate());
        tmpPool.add(new Crescendo());
        tmpPool.add(new CrushJoints());
        tmpPool.add(new CutThroughFate());
        tmpPool.add(new DeceiveReality());
        tmpPool.add(new DeusExMachina());
        tmpPool.add(new DevaForm());
        tmpPool.add(new Devotion2());
        tmpPool.add(new EmptyBody());
        tmpPool.add(new EmptyFist());
        tmpPool.add(new EmptyMind());
        tmpPool.add(new Eruption2());
        tmpPool.add(new Establishment());
        tmpPool.add(new Evaluate());
        tmpPool.add(new Fasting2());
        tmpPool.add(new FearNoEvil());
        tmpPool.add(new FlurryOfBlows());
        tmpPool.add(new FlyingSleeves());
        tmpPool.add(new FollowUp2());
        //tmpPool.add(new ForeignInfluence());
        tmpPool.add(new Foresight());
        tmpPool.add(new Halt());
        tmpPool.add(new Indignation());
        tmpPool.add(new InnerPeace());
        //tmpPool.add(new Judgement());
        tmpPool.add(new JustLucky());
        tmpPool.add(new LessonLearned());
        tmpPool.add(new LikeWater2());
        tmpPool.add(new MasterReality2());
        tmpPool.add(new Meditate());
        //tmpPool.add(new MentalFortress());
        tmpPool.add(new Nirvana2());
        tmpPool.add(new Omniscience());
        tmpPool.add(new Perseverance2());
        tmpPool.add(new Pray2());
        tmpPool.add(new PressurePoints2());
        tmpPool.add(new Prostrate());
        tmpPool.add(new Protect());
        tmpPool.add(new Ragnarok());
        tmpPool.add(new ReachHeaven());
        tmpPool.add(new Rushdown2());
        tmpPool.add(new Sanctity());
        tmpPool.add(new SandsOfTime());
        tmpPool.add(new SashWhip());
        tmpPool.add(new Scrawl());
        tmpPool.add(new SignatureMove());
        tmpPool.add(new SimmeringFury());
        tmpPool.add(new SpiritShield());
        tmpPool.add(new Study2());
        tmpPool.add(new Swivel2());
        tmpPool.add(new TalkToTheHand());
        tmpPool.add(new Tantrum());
        tmpPool.add(new ThirdEye());
        tmpPool.add(new Tranquility());
        tmpPool.add(new Vault());
        tmpPool.add(new Vigilance2());
        tmpPool.add(new Wallop());
        tmpPool.add(new WaveOfTheHand());
        tmpPool.add(new Weave2());
        tmpPool.add(new WheelKick());
        tmpPool.add(new WindmillStrike());
        tmpPool.add(new Wish());
        tmpPool.add(new Worship());
        tmpPool.add(new WreathOfFlame());
        //tmpPool.add(new Apotheosis());
        tmpPool.add(new BandageUp());
        tmpPool.add(new Blind());
        tmpPool.add(new Chrysalis());
        tmpPool.add(new DarkShackles());
        tmpPool.add(new DeepBreath());
        tmpPool.add(new Discovery());
        tmpPool.add(new DramaticEntrance());
        tmpPool.add(new Enlightenment());
        tmpPool.add(new Finesse());
        tmpPool.add(new FlashOfSteel());
        //tmpPool.add(new Forethought());
        tmpPool.add(new GoodInstincts());
        tmpPool.add(new HandOfGreed());
        tmpPool.add(new Impatience());
        tmpPool.add(new JackOfAllTrades());
        tmpPool.add(new Madness());
        tmpPool.add(new Magnetism2());
        tmpPool.add(new MasterOfStrategy());
        //tmpPool.add(new Mayhem());
        tmpPool.add(new Metamorphosis());
        tmpPool.add(new MindBlast2());
        tmpPool.add(new Panacea());
        tmpPool.add(new Panache());
        tmpPool.add(new PanicButton());
        tmpPool.add(new Purity());
        tmpPool.add(new SadisticNature());
        tmpPool.add(new SecretTechnique());
        tmpPool.add(new SecretWeapon());
        tmpPool.add(new SwiftStrike());
        tmpPool.add(new TheBomb());
        //tmpPool.add(new ThinkingAhead());
        //tmpPool.add(new Transmutation());
        tmpPool.add(new Trip());
        tmpPool.add(new Violence());
        return tmpPool;
    }

    public ThePrismatic() {
        super(NAMES[0], Enums.Prismatic,
                new CustomEnergyOrb(orbPath, orbVfxPath, null), //Energy OrbSlot
                new SpriterAnimation(characterPath("animation/default.scml"))); //Animation

        initializeClass(null,
                SHOULDER_2,
                SHOULDER_1,
                CORPSE,
                getLoadout(),
                20.0F, -20.0F, 200.0F, 250.0F, //Character hitbox. x y position, then width and height.
                new EnergyManager(ENERGY_PER_TURN));

        //Location for text bubbles. You can adjust it as necessary later. For most characters, these values are fine.
        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 220.0F * Settings.scale);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        //List of IDs of cards for your starting deck.
        //If you want multiple of the same card, you have to add it multiple times.
        retVal.add(Strike_Red.ID);
        retVal.add(Strike_Green.ID);
        retVal.add(Strike_Purple.ID);
        retVal.add(Defend_Green.ID);
        retVal.add(Defend_Blue.ID);
        retVal.add(Defend_Watcher.ID);
        retVal.add(Bash.ID);
        retVal.add(Survivor.ID);
        retVal.add(Zap.ID);
        retVal.add(getPrismaticCard().cardID);
        return retVal;
    }
    public AbstractCard getPrismaticCard(){
        ArrayList<AbstractCard> prismaticCards = new ArrayList<>();
        Collections.addAll(prismaticCards, new CunningStrike(), new DivineStrike(), new EmpoweringStrike(),
                new EmptyStrike(), new ExhaustingStrike(), new FrostyStrike(), new InsightfulStrike(),
                new LootingStrike(), new SadisticStrike(), new ScrapingStrike(), new ScryingStrike(),
                new StormyStrike(), new StrikeForOne(), new TargetingStrike(), new ToxicStrike(),
                new CunningDefend(), new DefendForOne(), new DivineDefend(), new EmpoweringDefend(),
                new EmptyDefend(), new ExhaustingDefend(), new FrostyDefend(), new InsightfulDefend(),
                new LootingDefend(), new SadisticDefend(), new ScrapingDefend(), new ScryingDefend(),
                new StormyDefend(), new TargetingDefend(), new ToxicDefend());
        //RetainingStrike.ID, RetainingDefend.ID
        Random rand = new Random();
        return prismaticCards.get(rand.nextInt(prismaticCards.size()));
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        //IDs of starting relics. You can have multiple, but one is recommended.
        retVal.add(BurningRing.ID);
        return retVal;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return getPrismaticCard();
    }

    /*- Below this is methods that you should *probably* adjust, but don't have to. -*/

    @Override
    public int getAscensionMaxHPLoss() {
        return 4; //Max hp reduction at ascension 14+
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        //These attack effects will be used when you attack the heart.
        return new AbstractGameAction.AttackEffect[] {
                AbstractGameAction.AttackEffect.SLASH_VERTICAL,
                AbstractGameAction.AttackEffect.SLASH_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY
        };
    }

    private final Color cardRenderColor = Color.LIGHT_GRAY.cpy(); //Used for some vfx on moving cards (sometimes) (maybe)
    private final Color cardTrailColor = Color.LIGHT_GRAY.cpy(); //Used for card trail vfx during gameplay.
    private final Color slashAttackColor = Color.LIGHT_GRAY.cpy(); //Used for a screen tint effect when you attack the heart.
    @Override
    public Color getCardRenderColor() {
        return cardRenderColor;
    }

    @Override
    public Color getCardTrailColor() {
        return cardTrailColor;
    }

    @Override
    public Color getSlashAttackColor() {
        return slashAttackColor;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        //Font used to display your current energy.
        //energyNumFontRed, Blue, Green, and Purple are used by the basegame characters.
        //It is possible to make your own, but not convenient.
        return FontHelper.energyNumFontRed;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        //This occurs when you click the character's button in the character select screen.
        //See SoundMaster for a full list of existing sound effects, or look at BaseMod's wiki for adding custom audio.
        CardCrawlGame.sound.playA("ATTACK_DAGGER_2", random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
    }
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        //Similar to doCharSelectScreenSelectEffect, but used for the Custom mode screen. No shaking.
        return "ATTACK_DAGGER_2";
    }

    //Don't adjust these four directly, adjust the contents of the CharacterStrings.json file.
    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }
    @Override
    public String getTitle(PlayerClass playerClass) {
        return NAMES[1];
    }
    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }
    @Override
    public String getVampireText() {
        return TEXT[2]; //Generally, the only difference in this text is how the vampires refer to the player.
    }

    /*- You shouldn't need to edit any of the following methods. -*/

    //This is used to display the character's information on the character selection screen.
    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                MAX_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this,
                getStartingRelics(), getStartingDeck(), false);
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return CARD_COLOR;
    }

    @Override
    public AbstractPlayer newInstance() {
        //Makes a new instance of your character class.
        return new ThePrismatic();
    }
}
