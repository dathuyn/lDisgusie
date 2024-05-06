package dev.acentra.disguise.utils;

import com.github.javafaker.Faker;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;

import java.security.SecureRandom;
import java.util.*;

public class NameUtils {
    private static final List<String> shortWords = Arrays.asList("About", "Active", "Admit", "Advise", "Again", "After", "Agent", "Alive", "Alone", "Beach", "Basket", "Basic", "Bath", "Battle", "Bean", "Beat", "Bed", "Become", "Begin", "Before", "Beer", "Behind", "Blade", "Black", "Blue", "Bomb", "Brush", "Build", "Bunch", "Button", "Biz", "Busy", "Box", "Boy", "Break", "Best", "Better", "Cake", "Camera", "Campus", "Cap", "Card", "Care", "Case", "Catch", "Center", "Chain", "Chair", "Chara", "Charge", "Chase", "Cheap", "Cheese", "Check", "Close", "Choose", "Christ", "Circle", "Dad", "Dance", "Dark", "Data", "Dead", "Defend", "Desert", "Desk", "Device", "Detect", "Dinner", "Direct", "Dirt", "Dirty", "Doctor", "Down", "Drama", "Draw", "Dream", "Drop", "Earth", "Eat", "Easy", "Editor", "Effect", "Eight", "Elect", "Effort", "Emote", "Enter", "Engine", "Enemy", "Empty", "Entry", "Error", "Enough", "Every", "Exact", "Eye", "Expert", "Face", "Fact", "Fade", "Fail", "Family", "Famous", "Farmer", "Father", "Fight", "Find", "Finger", "Fire", "First", "Fit", "Fix", "Fish", "Field", "Floor", "Focus", "Fly", "Forest", "Force", "Frame", "Uber");

    private static final List<String> longWords = Arrays.asList("Actually", "Aircraft", "Backbone", "Blooming", "Brightly", "Building", "Camellia", "Cardinal", "Careless", "Chemical", "Cheerful", "Civilian", "Daughter", "Demolish", "Detector", "Disaster", "Disposal", "Electron", "Elective", "Engaging", "Enormous", "Erection", "Evidence", "Exertion", "External", "Faithful", "Familiar", "Favorite", "Fearless", "Fixation", "Fragment", "Generous", "Grateful", "Grievous", "Hydrogen", "Horrible", "Ignorant", "Industry", "Majority", "Military", "Mountain", "Mythical", "Normally", "Numerous", "Organism", "Overview", "Pacifist", "Pentagon", "Perilous", "Physical", "Precious", "Prestige", "Puzzling", "Railroad", "Reckless");

    private static final List<String> conjuctions = Arrays.asList("The", "Da", "And", "Of", "By", "Is", "El", "Its", "MC", "GANGMEMBER", "xXx", "_", "__");

    private static final List<String> onlyNames = Arrays.asList("Ibirawyr", "Niniel", "Celahan", "Gwysien", "Figovudd", "Zathiel", "Adwiawyth", "Nydinia", "Laraeb", "Eowendasa", "Grendakin", "Werradia", "Cauth", "Umigolian", "Tardond", "Dwearia", "Yeiwyn", "Adraclya", "Zaev", "Thabeth", "Chuven", "Zaredon", "Bob", "Robert", "Johnny", "Joy", "Matthew", "Michael", "Jacob", "Joshua", "Daniel", "Christopher", "Andrew", "Ethan", "Joseph", "William", "Anthony", "David", "Alexander", "Nicholas", "Ryan", "Tyler", "James", "John", "Jonathan", "Noah", "Brandon", "Christian", "Dylan", "Samuel", "Benjamin", "Nathan", "Zachary", "Logan", "Justin", "Gabriel", "Emily", "Madison", "Emma", "Olivia", "Hannah", "Abigail", "Isabella", "Samantha", "Elizabeth", "Ashley", "Alexis", "Sarah", "Sophia", "Alyssa", "Grace", "Ava", "Taylor", "Brianna", "Lauren", "Chloe", "Natalie", "Kayla", "Jessica", "Anna", "Victoria", "Mia", "Hailey", "Sydney", "Jasmine");

    private static final List<String> japaneseNames = Arrays.asList("Ai", "Aya", "Ayako", "Itsuki", "Eita", "Eiko", "Kanta", "Kaito", "Kenta", "Kento", "Kouki", "Kouta", "Kouya", "Kou", "Keito", "Keita", "Saya", "Sayako", "Sara", "Sizuki", "Sizuko", "Sizuno", "Sizuya", "Suzuka", "Suzuki", "Suzuko", "Sumi", "Seiya", "Souta", "Souya", "Sou", "Taichi", "Takuya", "Tatsuki", "Chitose", "Tutomu", "Tumuya", "Tetsuya", "Tetsuto", "Tekuto", "Touya", "Tomi", "Nami", "Nao", "Neo", "Notomi", "Haruya", "Harumi", "Haruto", "Hitomi", "Hitoshi", "Fuuta", "Fuyuki", "Fuuto", "Mami", "Maya", "Mai", "Masaya", "Masahiro", "Masato", "Misaki", "Mitsuki", "Mutsuki", "Mei", "Yae", "Yuuto", "Yuuta", "Yuuya", "Youta", "Youki");

    private static final SecureRandom random = new SecureRandom();

    private static final Map<String, String> rankPrefixes = new HashMap<>();

    static {
        rankPrefixes.put("Premium", "&8&l[&2Premium&8&l]&2 ");
        rankPrefixes.put("Hero", "&8&l[&3Hero&8&l]&3 ");
        rankPrefixes.put("Elite", "&8&l[&aElite&8&l]&a ");
        rankPrefixes.put("Supreme", "&8&l[&6Supreme&8&l]&6 ");
        rankPrefixes.put("Acentra", "&8&l[&eAcentra&8&l]&e ");
        rankPrefixes.put("NoRank", "&7");
    }

    public static String getPlayerRankPrefix(String rank) {
        if (rankPrefixes.containsKey(rank)) {
            return rankPrefixes.get(rank);
        } else {
            return "&7";
        }
    }
    public static String getPlayerRankPrefix(UUID uuid) {
        LuckPerms luckPerms = LuckPermsProvider.get();

        User user = luckPerms.getUserManager().getUser(uuid);

        //luckPerms.getGroupManager().getGroup("")

        return user != null ? user.getCachedData().getMetaData().getPrefix() : "";
    }

    public static String getPlayerSuffix(UUID uuid) {
        LuckPerms luckPerms = LuckPermsProvider.get();

        User user = luckPerms.getUserManager().getUser(uuid);

        //luckPerms.getGroupManager().
        return user != null ? user.getCachedData().getMetaData().getSuffix() : "";
    }

    public static String generate() {
        String fakeName = new Faker().name().firstName() + random.nextInt(9999);
        if(Bukkit.getPlayer(fakeName) == null)
            return fakeName;
        return generate();
    }

    public static String generate(NicknamePattern pattern) {
        Random random = new Random();
        String disguiseNickname = null;

        switch (pattern) {
            case NameWithNumbers:
                String name = getRandomElement(onlyNames, random);
                String numberPart = random.nextInt(9999) + "";
                disguiseNickname = name + (name.length() <= 10 && chance(50.0) ? "_" : "") + numberPart;
                break;

            case TwoShortsWithConjunction:
                pattern = NicknamePattern.JapaneseNameWithBirth;
                // Fall through to the next case

            case JapaneseNameWithBirth:
                String japaneseName = getRandomElement(japaneseNames, random);
                String birth = (random.nextInt(12) + 1) + "" + (random.nextInt(30) + 1);
                disguiseNickname = japaneseName + (chance(50.0) ? "_" : "") + birth;
                break;

            case LongWithNumbers:
                String longName = getRandomElement(longWords, random);
                String numberPartLong = random.nextInt(9999) + "";
                disguiseNickname = longName + (chance(50.0) ? "_" : "") + numberPartLong;
                break;

            case ShortWithConjunction:
                String conjunction = getRandomElement(conjuctions, random);
                String shortName = getRandomElement(shortWords, random);
                disguiseNickname = conjunction + (chance(50.0) ? "_" : "") + shortName;
                break;

            case ShortAndLong:
                String shortName2 = getRandomElement(shortWords, random);
                String longName2 = getRandomElement(longWords, random);
                disguiseNickname = shortName2 + longName2;
                break;
        }

        if (chance(50.0) && disguiseNickname != null) {
            disguiseNickname = disguiseNickname.toLowerCase();
        }

        return disguiseNickname;
    }

    private static boolean chance(double percent) {
        return Math.random() < percent / 100.0;
    }

    private static String getRandomElement(List<String> list, Random random) {
        return list.get(random.nextInt(list.size()));
    }

    public enum NicknamePattern {
        ShortAndLong,
        NameWithNumbers,
        LongWithNumbers,
        ShortWithConjunction,
        JapaneseNameWithBirth,
        TwoShortsWithConjunction
    }
}