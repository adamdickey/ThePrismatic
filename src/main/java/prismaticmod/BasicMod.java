package prismaticmod;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import basemod.devcommands.relic.Relic;
import basemod.devcommands.relic.RelicRemove;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import prismaticmod.cards.BaseCard;
import prismaticmod.util.GeneralUtils;
import prismaticmod.util.KeywordInfo;
import prismaticmod.util.TextureLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.Patcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.scannotation.AnnotationDB;
import theprismatic.ThePrismatic;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static theprismatic.ThePrismatic.Enums.CARD_COLOR;

@SpireInitializer
public class BasicMod implements
        EditRelicsSubscriber,
        EditCardsSubscriber,
        EditCharactersSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        PostInitializeSubscriber {
    public static ModInfo info;
    public static String modID; //Edit your pom.xml to change this
    static { loadModInfo(); }
    public static final Logger logger = LogManager.getLogger(modID); //Used to output to the console.
    private static final String resourcesFolder = "prismaticmod";

    private static final String BG_ATTACK = characterPath("cardback/bg_attack.png");
    private static final String BG_ATTACK_P = characterPath("cardback/bg_attack_p.png");
    private static final String BG_SKILL = characterPath("cardback/bg_skill.png");
    private static final String BG_SKILL_P = characterPath("cardback/bg_skill_p.png");
    private static final String BG_POWER = characterPath("cardback/bg_power.png");
    private static final String BG_POWER_P = characterPath("cardback/bg_power_p.png");
    private static final String ENERGY_ORB = characterPath("cardback/energy_orb.png");
    private static final String ENERGY_ORB_P = characterPath("cardback/energy_orb_p.png");
    private static final String SMALL_ORB = characterPath("cardback/small_orb.png");
    private static final Color cardColor = new Color(255f/255f, 255f/255f, 255f/255f, 1f);
    //red, green, blue, alpha. alpha is transparency, which should just be 1.
    private static final String CHAR_SELECT_BUTTON = characterPath("select/button.png");
    private static final String CHAR_SELECT_PORTRAIT = characterPath("select/portrait.png");

    //This is used to prefix the IDs of various objects like cards and relics,
    //to avoid conflicts between different mods using the same name for things.
    public static String makeID(String id) {
        return modID + ":" + id;
    }

    //This will be called by ModTheSpire because of the @SpireInitializer annotation at the top of the class.
    public static void initialize() {
        new BasicMod();

        BaseMod.addColor(CARD_COLOR, cardColor,
                BG_ATTACK, BG_SKILL, BG_POWER, ENERGY_ORB,
                BG_ATTACK_P, BG_SKILL_P, BG_POWER_P, ENERGY_ORB_P,
                SMALL_ORB);

    }

    public BasicMod() {
        BaseMod.subscribe(this); //This will make BaseMod trigger all the subscribers at their appropriate times.
        logger.info(modID + " subscribed to BaseMod.");
    }

    @Override
    public void receivePostInitialize() {
        //This loads the image used as an icon in the in-game mods menu.
        Texture badgeTexture = TextureLoader.getTexture(imagePath("badge.png"));
        //Set up the mod information displayed in the in-game mods menu.
        //The information used is taken from your pom.xml file.
        BaseMod.registerModBadge(badgeTexture, info.Name, GeneralUtils.arrToString(info.Authors), info.Description, null);
    }

    /*----------Localization----------*/

    //This is used to load the appropriate localization files based on language.
    private static String getLangString()
    {
        return Settings.language.name().toLowerCase();
    }
    private static final String defaultLanguage = "eng";

    public static final Map<String, KeywordInfo> keywords = new HashMap<>();

    @Override
    public void receiveEditStrings() {
        /*
            First, load the default localization.
            Then, if the current language is different, attempt to load localization for that language.
            This results in the default localization being used for anything that might be missing.
            The same process is used to load keywords slightly below.
        */
        loadLocalization(defaultLanguage); //no exception catching for default localization; you better have at least one that works.
        if (!defaultLanguage.equals(getLangString())) {
            try {
                loadLocalization(getLangString());
            }
            catch (GdxRuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadLocalization(String lang) {
        //While this does load every type of localization, most of these files are just outlines so that you can see how they're formatted.
        //Feel free to comment out/delete any that you don't end up using.
        BaseMod.loadCustomStringsFile(CardStrings.class,
                localizationPath(lang, "CardStrings.json"));
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                localizationPath(lang, "CharacterStrings.json"));
        BaseMod.loadCustomStringsFile(EventStrings.class,
                localizationPath(lang, "EventStrings.json"));
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                localizationPath(lang, "OrbStrings.json"));
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                localizationPath(lang, "PotionStrings.json"));
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                localizationPath(lang, "PowerStrings.json"));
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                localizationPath(lang, "RelicStrings.json"));
        BaseMod.loadCustomStringsFile(UIStrings.class,
                localizationPath(lang, "UIStrings.json"));
    }

    @Override
    public void receiveEditKeywords()
    {
        Gson gson = new Gson();
        String json = Gdx.files.internal(localizationPath(defaultLanguage, "Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
        KeywordInfo[] keywords = gson.fromJson(json, KeywordInfo[].class);
        for (KeywordInfo keyword : keywords) {
            keyword.prep();
            registerKeyword(keyword);
        }

        if (!defaultLanguage.equals(getLangString())) {
            try
            {
                json = Gdx.files.internal(localizationPath(getLangString(), "Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
                keywords = gson.fromJson(json, KeywordInfo[].class);
                for (KeywordInfo keyword : keywords) {
                    keyword.prep();
                    registerKeyword(keyword);
                }
            }
            catch (Exception e)
            {
                logger.warn(modID + " does not support " + getLangString() + " keywords.");
            }
        }
    }

    private void registerKeyword(KeywordInfo info) {
        BaseMod.addKeyword(modID.toLowerCase(), info.PROPER_NAME, info.NAMES, info.DESCRIPTION);
        if (!info.ID.isEmpty())
        {
            keywords.put(info.ID, info);
        }
    }

    //These methods are used to generate the correct filepaths to various parts of the resources folder.
    public static String localizationPath(String lang, String file) {
        return resourcesFolder + "/localization/" + lang + "/" + file;
    }

    public static String imagePath(String file) {
        return resourcesFolder + "/images/" + file;
    }
    public static String characterPath(String file) {
        return resourcesFolder + "/images/character/" + file;
    }
    public static String powerPath(String file) {
        return resourcesFolder + "/images/powers/" + file;
    }
    public static String relicPath(String file) {
        return resourcesFolder + "/images/relics/" + file;
    }


    //This determines the mod's ID based on information stored by ModTheSpire.
    private static void loadModInfo() {
        Optional<ModInfo> infos = Arrays.stream(Loader.MODINFOS).filter((modInfo)->{
            AnnotationDB annotationDB = Patcher.annotationDBMap.get(modInfo.jarURL);
            if (annotationDB == null)
                return false;
            Set<String> initializers = annotationDB.getAnnotationIndex().getOrDefault(SpireInitializer.class.getName(), Collections.emptySet());
            return initializers.contains(BasicMod.class.getName());
        }).findFirst();
        if (infos.isPresent()) {
            info = infos.get();
            modID = info.ID;
        }
        else {
            throw new RuntimeException("Failed to determine mod info/ID based on initializer.");
        }
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new ThePrismatic(),
                CHAR_SELECT_BUTTON, CHAR_SELECT_PORTRAIT, ThePrismatic.Enums.Prismatic);
    }

    @Override
    public void receiveEditCards() {
        new AutoAdd(modID) //Loads files from this mod
                .packageFilter(BaseCard.class) //In the same package as this class
                .setDefaultSeen(true) //And marks them as seen in the compendium
                .cards(); //Adds the cards
    }
    public void receiveEditRelics()
    {
        // This finds and adds all relics inheriting from CustomRelic that are in the same package
        // as MyRelic, keeping all as unseen except those annotated with @AutoAdd.Seen
        new AutoAdd(modID)
                .any(CustomRelic.class, (info, relic) -> {
                    BaseMod.addRelicToCustomPool(relic, CARD_COLOR);
                    UnlockTracker.markRelicAsSeen(relic.relicId);
                });
        BaseMod.removeRelicFromCustomPool(new Abacus(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new Akabeko(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new Anchor(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new AncientTeaSet(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new ArtOfWar(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new Astrolabe(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new BagOfMarbles(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new BagOfPreparation(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new BirdFacedUrn(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new BlackStar(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new BloodVial(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new BloodyIdol(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new BlueCandle(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new Boot(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new BottledFlame(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new BottledLightning(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new BottledTornado(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new BronzeScales(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new BustedCrown(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new Calipers(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new CallingBell(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new CaptainsWheel(), CARD_COLOR);
        //BaseMod.removeRelicFromCustomPool(new Cauldron(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new CentennialPuzzle(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new CeramicFish(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new ChemicalX(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new ClockworkSouvenir(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new CoffeeDripper(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new Courier(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new CultistMask(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new CursedKey(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new DarkstonePeriapt(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new DeadBranch(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new DollysMirror(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new DreamCatcher(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new DuVuDoll(), CARD_COLOR);
        //BaseMod.removeRelicFromCustomPool(new Ectoplasm(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new EmptyCage(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new Enchiridion(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new EternalFeather(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new FaceOfCleric(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new FossilizedHelix(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new FrozenEgg2(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new FrozenEye(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new FusionHammer(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new GamblingChip(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new Ginger(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new Girya(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new GoldenIdol(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new GremlinHorn(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new GremlinMask(), CARD_COLOR);
        //BaseMod.removeRelicFromCustomPool(new HandDrill(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new HappyFlower(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new HornCleat(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new IceCream(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new IncenseBurner(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new InkBottle(), CARD_COLOR);
        //BaseMod.removeRelicFromCustomPool(new JuzuBracelet(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new Kunai(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new Lantern(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new LetterOpener(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new LizardTail(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new Mango(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new MarkOfTheBloom(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new Matryoshka(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new MawBank(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new MealTicket(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new MeatOnTheBone(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new MedicalKit(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new MembershipCard(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new MercuryHourglass(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new MoltenEgg2(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new MummifiedHand(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new MutagenicStrength(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new Necronomicon(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new NeowsLament(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new NilrysCodex(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new NlothsGift(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new NlothsMask(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new Nunchaku(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new OddlySmoothStone(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new OddMushroom(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new OldCoin(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new Omamori(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new OrangePellets(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new Orichalcum(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new OrnamentalFan(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new Orrery(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new PandorasBox(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new Pantograph(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new PeacePipe(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new Pear(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new PenNib(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new PhilosopherStone(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new Pocketwatch(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new PotionBelt(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new PrayerWheel(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new PreservedInsect(), CARD_COLOR);
        //BaseMod.removeRelicFromCustomPool(new PrismaticShard(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new QuestionCard(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new RedMask(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new RegalPillow(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new RunicDome(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new RunicPyramid(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new SacredBark(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new Shovel(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new Shuriken(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new SingingBowl(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new SlaversCollar(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new Sling(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new SmilingMask(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new SneckoEye(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new Sozu(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new SpiritPoop(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new SsserpentHead(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new StoneCalendar(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new StrangeSpoon(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new Strawberry(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new StrikeDummy(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new Sundial(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new ThreadAndNeedle(), CARD_COLOR);
        //BaseMod.removeRelicFromCustomPool(new TinyChest(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new TinyHouse(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new Toolbox(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new Torii(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new ToxicEgg2(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new ToyOrnithopter(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new TungstenRod(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new Turnip(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new UnceasingTop(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new Vajra(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new VelvetChoker(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new Waffle(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new WarPaint(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new WarpedTongs(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new Whetstone(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new WhiteBeast(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new WingBoots(), CARD_COLOR);

        BaseMod.removeRelicFromCustomPool(new HoveringKite(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new NinjaScroll(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new PaperCrane(), CARD_COLOR);
        //BaseMod.removeRelicFromCustomPool(new RingOfTheSerpent(), CARD_COLOR);
        //BaseMod.removeRelicFromCustomPool(new SnakeRing(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new SneckoSkull(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new TheSpecimen(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new Tingsha(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new ToughBandages(), CARD_COLOR);
        //BaseMod.removeRelicFromCustomPool(new TwistedFunnel(), CARD_COLOR);
        //BaseMod.removeRelicFromCustomPool(new WristBlade(), CARD_COLOR);
        //BaseMod.removeRelicFromCustomPool(new BlackBlood(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new Brimstone(), CARD_COLOR);
        //BaseMod.removeRelicFromCustomPool(new BurningBlood(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new ChampionsBelt(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new CharonsAshes(), CARD_COLOR);
        //BaseMod.removeRelicFromCustomPool(new MagicFlower(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new MarkOfPain(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new PaperFrog(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new RedSkull(), CARD_COLOR);
        //BaseMod.removeRelicFromCustomPool(new RunicCube(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new SelfFormingClay(), CARD_COLOR);
        //BaseMod.removeRelicFromCustomPool(new CrackedCore(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new DataDisk(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new EmotionChip(), CARD_COLOR);
        //BaseMod.removeRelicFromCustomPool(new FrozenCore(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new GoldPlatedCables(), CARD_COLOR);
        //BaseMod.removeRelicFromCustomPool(new Inserter(), CARD_COLOR);
        //BaseMod.removeRelicFromCustomPool(new NuclearBattery(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new RunicCapacitor(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new SymbioticVirus(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new CloakClasp(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new Damaru(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new GoldenEye(), CARD_COLOR);
        //BaseMod.removeRelicFromCustomPool(new HolyWater(), CARD_COLOR);
        //BaseMod.removeRelicFromCustomPool(new Melange(), CARD_COLOR);
        //BaseMod.removeRelicFromCustomPool(new PureWater(), CARD_COLOR);
        //BaseMod.removeRelicFromCustomPool(new VioletLotus(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new TeardropLocket(), CARD_COLOR);
        BaseMod.removeRelicFromCustomPool(new Duality(), CARD_COLOR);
    }
}
