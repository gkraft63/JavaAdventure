package ikraftsoftware.com;

import misc.*;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import static ikraftsoftware.com.util.Rand.rand;

public class Story {

    Game game;
    UI ui;
    VisibilityManager vm;
    Player player = new Player();
    SuperMonster[] monsterArray = new SuperMonster[10];
    SuperMonster monster;
    int townGuardHealth;
    int currentMonster = 0;

    int[] healthAward = new int[10];
    int[] coinsAward = new int[10];
    public int healthNum = 0; // Keep track of your place in the array
    public int coinNum = 0; // Keep track of your place in the array

    SuperLocation[] locationArray = new SuperLocation[9]; // Need to be the same length
    int[] array = new int[9]; // Need to be the same length

    SuperLocation townGate;
    String currentLocation;
    Boolean gotRing;


    public Story(Game g, UI userInterface, VisibilityManager vManager) {
        game = g;
        ui = userInterface;
        vm = vManager;
    }

    public void defaultSetup() {
        player.hp = 20;
        player.coins = 100;
        ui.hpNumberLabel.setText("" + player.hp);
        ui.coinNumberLabel.setText("" + player.coins);
        player.currentWeapon = new Weapon_Knife();
        ui.weaponNameLabel.setText(player.currentWeapon.name);
        townGuardHealth = 100;
        gotRing = false;

        //Locations
        townGate = new Location_TownGate();
        currentLocation = "townGate";
        monsterArray[0] = new Monster_TownGuard();
        monsterArray[1] = new Monster_Gypsy();
        monsterArray[2] = new Monster_Hillary();
        monsterArray[3] = new Monster_SubwayBum();
        monsterArray[4] = new Monster_Ted();

        for (int i = 0; i < 5; i++) {  // 5 awards are random amounts (even negative) next 5 are set values
            healthAward[i] = rand(-20, 40);
            if (healthAward[i] == 0) healthAward[1] = 5;
        }
        for (int i = 0; i < 5; i++) { // 5 awards are random amounts (even negative) next 5 are set values
            coinsAward[i] = rand(10, 60);
            if (coinsAward[i] == 0) coinsAward[1] = 5;
        }
        // 5 awards are random amounts (even negative) next 5 are set values. Like below.
        healthAward[5] = 35;
        healthAward[6] = 35;
        healthAward[7] = 35;
        healthAward[8] = 35;
        healthAward[9] = 35;
        coinsAward[5] = 35;
        coinsAward[6] = 35;
        coinsAward[7] = 35;
        coinsAward[8] = 35;
        coinsAward[9] = 35;

        randomizeLocations(); // Place locations on grid and connect them North, South, East and West.
    }

    public void selectPosition(String nextPosition) {
        switch (nextPosition) {
            case "talkGuard":
                talkGuard();

                break;
            case "attackGuard":
                attackGuard();
                break;
            case "townGate":
                refreshDisplay(townGate);
                break;

            case "smallWoods":
                coinNum = 0;
                currentLocation = locationArray[array[0]].identifier;
                refreshDisplay(locationArray[array[0]]);
                break;

            case "swamp":
                currentLocation = locationArray[array[1]].identifier;
                refreshDisplay(locationArray[array[1]]);
                break;
            case "crossRoad":
                currentLocation = locationArray[array[2]].identifier;
                refreshDisplay(locationArray[array[2]]);
                break;

            case "theGrassyKnoll":
                currentLocation = locationArray[array[3]].identifier;
                refreshDisplay(locationArray[array[3]]);
                break;

            case "darkForest":
                currentLocation = locationArray[array[4]].identifier;
                refreshDisplay(locationArray[array[4]]);
                break;
            case "theFairfaxSpa":
                currentLocation = locationArray[array[5]].identifier;
                refreshDisplay(locationArray[array[5]]);
                healthNum = 5;
                break;
            case "tjMax":
                healthNum = 0;
                currentLocation = locationArray[array[6]].identifier;
                refreshDisplay(locationArray[array[6]]);
                break;
            case "theLake":
                currentLocation = locationArray[array[7]].identifier;
                refreshDisplay(locationArray[array[7]]);
                break;
            case "theBaseballPark":
                currentLocation = locationArray[array[8]].identifier;
                refreshDisplay(locationArray[array[8]]);
                healthNum = 1;
                if (healthAward[healthNum] != 0) {
                    healthAward[healthNum] = rand(-30,20);
                    if (healthAward[healthNum] == 0) healthAward[healthNum] = -20;
                } else ui.mainTextArea.setText(" Your at the baseball park with Dr Fauci.");
                if (healthAward[healthNum] < 0) ui.mainTextArea.setText(ui.mainTextArea.getText()+" Fauci gave you covid!");
               break;


            //Weapons
            case "pickUpSword":
                if (!player.currentWeapon.name.equals("Long Sword")) {
                    player.currentWeapon = new Weapon_LongSword();
                    processWeapon(player.currentWeapon, locationArray[array[3]], 20);
                }

                break;

            case "pickUpRifle":
                if (!player.currentWeapon.name.equals("Savage .306")) {
                    player.currentWeapon = new Weapon_Rifle();
                    player.currentWeapon.mainText = player.currentWeapon.mainText + "Your on the Grassy Knoll with a new Savage .30612!";
                    processWeapon(player.currentWeapon, locationArray[array[3]], 20);
                }
                break;
            // Awards
            case "coinAward":
                if (coinsAward[coinNum] != 0) {
                    processAward(coinsAward[coinNum], "coins");
                    coinsAward[coinNum] = 0;
                    System.out.println("Array coinNumber at " + healthNum);
                } else ui.mainTextArea.setText(ui.mainTextArea.getText() + " Award expired!");
                break;

            //Health awards
            case "healthAward":
                if (healthAward[healthNum] != 0) {
                    processAward(healthAward[healthNum], "health");
                    healthAward[healthNum] = 0;
                    System.out.println("Array healthNum at " + healthNum);
                } else ui.mainTextArea.setText(ui.mainTextArea.getText() + " Award expired!");
                break;


            //Fight a monster
            case "swampMonster":
                currentMonster = 2;
                pickAMonster(locationArray[array[1]], currentMonster);
                locationArray[array[1]].mainText = "Your in a nasty swamp with Dead Hillary Clinton";

                break;
            case "forestMonster":
                currentMonster = 1;
                pickAMonster(locationArray[array[4]], currentMonster);
                locationArray[array[4]].mainText = "Your in a dark forest with a dead Gypsy!";
                break;
            case "fightTed":
                currentMonster = 4;
                pickAMonster(locationArray[array[7]], 4);
                locationArray[array[7]].mainText = "Your by a foggy lake with a fat dead Ghost of Ted Kennedy";
                break;

             case "evaluateFight":
                evaluateFight();
                break;
            case "playerAttack":
                playerAttack();
                break;
            case "monsterAttack":
                monsterAttack();
                break;
            case "win":
                win(gotRing);
                break;
            case "lose":
                lose();
                break;
            case "ending":
                ending();
                break;
            case "toTitle":
                toTitle();
                break;
            case "temp":
                ui.mainTextArea.setText(ui.mainTextArea.getText() + " This direction is under construction!");
                break;
            case "blocked":
                ui.mainTextArea.setText(ui.mainTextArea.getText() + " This direction is blocked!");
                break;
            case "run":
                run();
                break;
            case "buy20Health":
                int result = JOptionPane.showConfirmDialog(null, "20 HP units cost 40 coins,\nare you sure you want to proceed?",
                        "alert", JOptionPane.OK_CANCEL_OPTION);

                if (result == 0) {
                    if (player.coins > 40) {
                        player.coins = player.coins - 40;
                        player.hp = player.hp + 20;
                        ui.mainTextArea.setText(ui.mainTextArea.getText() + " You purchased 20 health units!");
                        ui.hpNumberLabel.setText("" + player.hp);
                        ui.coinNumberLabel.setText("" + player.coins);
                    } else
                        ui.mainTextArea.setText(ui.mainTextArea.getText() + " Health units cost 2 coins each. You don't have enough to buy 20!");
                }
                break;

            case "buyAR":
                int result2 = JOptionPane.showConfirmDialog(null, "AR 15 cost 100 coins, proceed?",
                        "alert", JOptionPane.OK_CANCEL_OPTION);
                if (result2 == 0) {
                    if (player.coins > 100) {
                        player.coins = player.coins - 100;
                        if (!player.currentWeapon.name.equals("AR 15")) {
                            player.currentWeapon = new Weapon_AR();
                            ui.weaponNameLabel.setText("AR 15");
                            player.currentWeapon.mainText = player.currentWeapon.mainText + "You purchased a AR 15!";
                            ui.coinNumberLabel.setText("" + player.coins);
                            playSound("src/newweapon.wav");
                            ui.mainTextArea.setText(ui.mainTextArea.getText() + " Nice AR!");
                        }
                    } else ui.mainTextArea.setText(ui.mainTextArea.getText() + " Not enough coins to buy an AR 15! 100 needed");
                }
                break;
        }
    }

    private void processAward(int awardValue, String awardType) {
        if (awardType.equals("coins")) {
            player.coins = player.coins + awardValue;
            ui.mainTextArea.setText(ui.mainTextArea.getText() + " You received " + awardValue + " coins...");
            ui.coinNumberLabel.setText("" + player.coins);
            if (awardValue > 0) playSound("src/chaching.wav");
            coinsAward[coinNum] = 0;

        } else {
            player.hp = player.hp + awardValue;
            ui.mainTextArea.setText(ui.mainTextArea.getText() + " You received " + awardValue + " in health...");
            ui.hpNumberLabel.setText("" + player.hp);
            if (awardValue > 0) playSound("src/health.wav");
            healthAward[healthNum] = 0;
          }

    }

    private void run() {
        player.hp = player.hp - 20;
        selectPosition(currentLocation);

    }

//            RyiSnow
//4 months ago
//    Yup you can make multiple story classes. In that case maybe you want to make a select position class which handles only selecting position thing then make other story-related classes (with those story-related methods in it).
//
//    For example, assuming talkGuard method is in Story1 class and crossRoad method is in Story 2 class,  the selectPosition would look like this:
//    public void selectPosition(String nextPosition){
//
//        switch(nextPosition){
//            case talkGuard: story1.talkGuard( );
//            case crossRoad: story2.crossRoad( );
//        }
//    }


    public void processWeapon(SuperWeapon w, SuperLocation l, int coinBonus) {

        if (!w.inUse) {
            ui.mainTextArea.setText("You have been awarded a " + w.name + "!\nYou location is " + l.name);
            ui.weaponNameLabel.setText(w.name);
            ui.choice1.setText(w.c1);
            player.coins = player.coins + coinBonus;
            ui.coinNumberLabel.setText("" + player.coins);
            playSound("src/newweapon.wav");
//                try {
//                    Thread.sleep(3 * 1000);
//                } catch (InterruptedException ie) {
//                    Thread.currentThread().interrupt();
//                }
            l.mainText = w.mainText;
            l.c1 = w.c1;
            l.p1 = w.p1;
            refreshDisplay(l);
        } else {
            ui.mainTextArea.setText("You already used " + w.name + "!");


            ui.weaponNameLabel.setText(player.currentWeapon.name);
            w.inUse = true;
        }
    }

    public void refreshDisplay(SuperLocation location) {
        if (player.hp < 1) lose();
        else {
            ui.mainTextArea.setText(location.mainText);
            ui.choice1.setText(location.c1);
            ui.choice2.setText(location.c2);
            ui.choice3.setText(location.c3);
            ui.choice4.setText(location.c4);
            ui.choice5.setText(location.c5);
            game.nextPosition1 = location.p1;
            game.nextPosition2 = location.p2;
            game.nextPosition3 = location.p3;
            game.nextPosition4 = location.p4;
            game.nextPosition5 = location.p5;
            ui.hpNumberLabel.setText("" + player.hp);
            ui.coinNumberLabel.setText("" + player.coins);
            ui.weaponNameLabel.setText(player.currentWeapon.name);

            //   ui.choice2.setVisible(true);
        }

    }

    public void townGate() {
        refreshDisplay(townGate);
    }


    public void talkGuard() {
        ui.mainTextArea.setText("Guard: Hello stranger. I have never seen your face. \nI'm sorry but we cannot let a stranger enter D.C.\nwithout the Gold ring.");
        ui.choice1.setText(">");
        ui.choice2.setText("");
        ui.choice3.setText("");
        ui.choice4.setText("");
        ui.choice5.setText("");

        game.nextPosition1 = "townGate";
        game.nextPosition2 = "";
        game.nextPosition3 = "";
        game.nextPosition4 = "";
        game.nextPosition5 = "";
    }

//    public void generic(String showText, String c1, String c2, String c3, String c4,String c5, String p1, String p2, String p3, String p4,String p5) {
//        ui.hpNumberLabel.setText("" + player.hp);
//        ui.mainTextArea.setText(showText);
//        ui.choice1.setText(c1);
//        ui.choice2.setText(c2);
//        ui.choice3.setText(c3);
//        ui.choice4.setText(c4);
//        ui.choice4.setText(c5);
//        game.nextPosition1 = p1;
//        game.nextPosition2 = p2;
//        game.nextPosition3 = p3;
//        game.nextPosition4 = p4;
//        game.nextPosition4 = p5;
//    }


    public void attackGuard() {
        int i = new java.util.Random().nextInt(7);
        if (monsterArray[0].hp > 0) player.hp = player.hp - i;
        monsterArray[0].hp = monsterArray[0].hp - player.currentWeapon.damage;
        if (player.hp < 1) {
            lose();
        } else if (player.hp > 0 & monsterArray[0].hp > 0) {
            ui.mainTextArea.setText("Guard: Hey don't be stupid!\n\nGuard hits back!\nYou receive " + i + " in damage, Guard's health = " + monsterArray[0].hp);
            ui.hpNumberLabel.setText("" + player.hp);
            ui.choice1.setText("Attack again!");
            ui.choice2.setText("Go Back");
            ui.choice3.setText("");
            ui.choice4.setText("");
            ui.choice5.setText("");
            game.nextPosition1 = "attackGuard";
            game.nextPosition2 = "townGate";
            game.nextPosition3 = "";
            game.nextPosition4 = "";
            game.nextPosition5 = "";
        } else {
            monster = monsterArray[0];
            gotRing = true;
            win(gotRing);
        }

    }


    public void pickAMonster(SuperLocation loc, int i) {
        monster = monsterArray[i];
        loadMonsterLabels(loc, i); //Do this for a fight
        if (monster.hp > 0) evaluateFight();
        else ui.choice1.setText("Still Dead!");

    }

    public void evaluateFight() {
        if (monster.hp > 0) {
            ui.mainTextArea.setText("You encounter " + monster.name + "!\nIt's health = " + monster.hp + "\nIf you run you lose 20h units\nWhat do you want to do?");
            ui.choice1.setText("Attack");
            ui.choice2.setText("Run");
            ui.choice3.setText("");
            ui.choice4.setText("");
            ui.choice5.setText("");
            game.nextPosition1 = "playerAttack";
            game.nextPosition2 = "run";
        } else {
            ui.mainTextArea.setText("You killed " + monster.name);
            ui.choice1.setText(monsterArray[currentMonster].c1);
            ui.choice2.setText(monsterArray[currentMonster].c2);
            ui.choice3.setText(monsterArray[currentMonster].c3);
            ui.choice4.setText(monsterArray[currentMonster].c4);
            ui.choice5.setText(monsterArray[currentMonster].c5);
            game.nextPosition1 = monsterArray[currentMonster].p1;
            game.nextPosition2 = monsterArray[currentMonster].p2;
            game.nextPosition3 = monsterArray[currentMonster].p3;
            game.nextPosition4 = monsterArray[currentMonster].p4;
            game.nextPosition5 = monsterArray[currentMonster].p5;


        }

    }

    public void playerAttack() {
        int monsterDamage = new java.util.Random().nextInt(player.currentWeapon.damage);
        monster.hp = monster.hp - monsterDamage;
        if (monster.hp < 1) monster.hp = 0;
        ui.mainTextArea.setText("You attack " + monster.name + "  " + monsterDamage + " damage.\n" +
                "It's health = " + monster.hp);
        ui.choice1.setText(">");
        ui.choice2.setText("");
        ui.choice3.setText("");
        ui.choice4.setText("");
        ui.choice5.setText("");
        if (monster.hp > 0) {
            game.nextPosition1 = "monsterAttack";
            game.nextPosition2 = "";
            game.nextPosition3 = "";
            game.nextPosition4 = "";
            game.nextPosition5 = "";
        } else {
            game.nextPosition1 = "win";
            game.nextPosition2 = "";
            game.nextPosition3 = "";
            game.nextPosition4 = "";
            game.nextPosition5 = "";
        }

    }

    public void monsterAttack() {
        int playerDamage = new java.util.Random().nextInt(monster.attack);
        player.hp = player.hp - playerDamage;
        ui.mainTextArea.setText(monster.attackMessage + "\nYou received " + playerDamage + " in damage!");
        ui.hpNumberLabel.setText("" + player.hp);
        ui.choice1.setText("Attack");
        ui.choice2.setText("");
        ui.choice3.setText("");
        ui.choice4.setText("");
        ui.choice5.setText("");
        if (player.hp > 0) {
            game.nextPosition1 = "playerAttack";
        } else {
            player.hp = 0;
            ui.hpNumberLabel.setText("" + player.hp);
            lose();
        }
        game.nextPosition2 = "";
        game.nextPosition3 = "";
        game.nextPosition4 = "";
        game.nextPosition5 = "";
    }

    public void win(Boolean gotRing) {


        if (gotRing) {
            ui.mainTextArea.setText("You've defeated the " + monster.name + " and it dropped a gold ring!\nProceed to D.C!");
            ui.choice1.setText("Enter the crappy city!");
            ui.choice2.setText("");
            ui.choice3.setText("");
            ui.choice4.setText("");
            ui.choice5.setText("");
            game.nextPosition1 = "toTitle";
            game.nextPosition2 = "";
            game.nextPosition3 = "";
            game.nextPosition4 = "";
            game.nextPosition5 = "";
        } else {
            playSound("src/monsterdie.wav");
            ui.mainTextArea.setText("You've defeated the " + monster.name + "!\nYou get " + monster.healthBonus + " health and " + monster.coinBonus + " coins!");
            player.hp = player.hp + monster.healthBonus;
            player.coins = player.coins + monster.coinBonus;
            ui.choice1.setText("Killed");
            ui.choice2.setText(monsterArray[currentMonster].c2);
            ui.choice3.setText(monsterArray[currentMonster].c3);
            ui.choice4.setText(monsterArray[currentMonster].c4);
            ui.choice5.setText(monsterArray[currentMonster].c5);
            game.nextPosition1 = "";
            game.nextPosition2 = monsterArray[currentMonster].p2;
            game.nextPosition3 = monsterArray[currentMonster].p3;
            game.nextPosition4 = monsterArray[currentMonster].p4;
            game.nextPosition5 = monsterArray[currentMonster].p5;
        }
    }

    public void lose() {
        ui.mainTextArea.setText("Your DEAD GAME OVER!");
        ui.choice1.setText("To Welcome Screen");
        ui.choice2.setText("");
        ui.choice3.setText("");
        ui.choice4.setText("");
        game.nextPosition1 = "toTitle";
        game.nextPosition2 = "";
        game.nextPosition3 = "";
        game.nextPosition4 = "";
        game.nextPosition5 = "";
    }

    public void ending() {
        ui.mainTextArea.setText("Guard oh you killed that " + monster.name + "! Welcome to our town!\n\n<THE END>");
        ui.choice1.setVisible(false);
        ui.choice2.setVisible(false);
        ui.choice3.setVisible(false);
        ui.choice4.setVisible(false);
        ui.choice5.setVisible(false);
    }

    public void toTitle() {
        defaultSetup();
        vm.showTitleScreen();
    }

    public void playSound(String fileName) {
        // open the sound file as a Java input stream
        String gongFile = fileName;
        InputStream in = null;
        try {
            in = new FileInputStream(gongFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // create an audiostream from the inputstream
        AudioStream audioStream = null;
        try {
            audioStream = new AudioStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // play the audio clip with the audioplayer class
        AudioPlayer.player.start(audioStream);
    }

    public void loadMonsterLabels(SuperLocation loc, int i) {
        monsterArray[i].c1 = loc.c1;
        monsterArray[i].c2 = loc.c2;
        monsterArray[i].c3 = loc.c3;
        monsterArray[i].c4 = loc.c4;
        monsterArray[i].c5 = loc.c5;
        monsterArray[i].p1 = loc.p1;
        monsterArray[i].p2 = loc.p2;
        monsterArray[i].p3 = loc.p3;
        monsterArray[i].p4 = loc.p4;
        monsterArray[i].p5 = loc.p5;

    }

    public void randomizeLocations() {

        Random rgen = new Random();

        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }

        for (int i = 0; i < array.length; i++) {
            int randomPosition = rgen.nextInt(array.length);
            int temp = array[i];
            array[i] = array[randomPosition];
            array[randomPosition] = temp;
        }

        if (array[2] != 2) {
            int placeHolder = 0;
            for (int i = 0; i < array.length; i++) {
                if (array[i] == 2) placeHolder = i;
            }
            array[placeHolder] = array[2];
            array[2] = 2;

        }
        for (int i = 0; i < locationArray.length; i++) System.out.println(array[i]);

        // Use these whenever you need to access the case locations example "theGrassyKnoll" =locationArray[array[3]].identifier
        locationArray[array[0]] = new Location_SmallWoods();
        locationArray[array[1]] = new Location_Swamp();
        locationArray[array[2]] = new Location_CrossRoad();
        locationArray[array[3]] = new Location_GrassyKnoll();
        locationArray[array[4]] = new Location_DarkForest();
        locationArray[array[5]] = new Location_HealthSpa();
        locationArray[array[6]] = new Location_TJMax();
        locationArray[array[7]] = new Location_TheLake();
        locationArray[array[8]] = new Location_Baseball();

        //First row of map grid
        locationArray[0].p2 = "blocked";
        locationArray[0].p3 = locationArray[6].identifier;
        locationArray[0].p4 = locationArray[1].identifier;
        locationArray[0].c5 = "Buy 20 HP Units";
        locationArray[0].p5 = "buy20Health"; //0

        locationArray[1].p2 = "blocked";
        locationArray[1].p3 = locationArray[7].identifier;
        locationArray[1].p4 = locationArray[2].identifier;
        locationArray[1].p5 = locationArray[0].identifier; // 1

        locationArray[2].p2 = "townGate";
        locationArray[2].p3 = locationArray[8].identifier;
        locationArray[2].p4 = locationArray[3].identifier;
        locationArray[2].p5 = locationArray[1].identifier;//2

        locationArray[3].p2 = "blocked";
        locationArray[3].p3 = "temp";
        locationArray[3].p4 = locationArray[4].identifier;
        locationArray[3].p5 = locationArray[2].identifier;//3

        locationArray[4].p2 = "blocked";
        locationArray[4].p3 = "temp";
        locationArray[4].p4 = locationArray[5].identifier;
        locationArray[4].p5 = locationArray[3].identifier;//4

        locationArray[5].p2 = "blocked";
        locationArray[5].p3 = "temp";
        locationArray[5].c4 = "Buy 20 HP Units";
        locationArray[5].p4 = "buy20Health";
        locationArray[5].p5 = locationArray[4].identifier;//5
        // Second Row of map grid
        locationArray[6].p2 = locationArray[0].identifier;
        locationArray[6].p3 = "temp";
        locationArray[6].p4 = locationArray[7].identifier;
        locationArray[6].c5 = "Buy AR15";
        locationArray[6].p5 = "buyAR";//5

        locationArray[7].p2 = locationArray[1].identifier;
        locationArray[7].p3 = "temp";
        locationArray[7].p4 = locationArray[8].identifier;;
        locationArray[7].p5 =  locationArray[6].identifier;

        locationArray[8].p2 = locationArray[2].identifier;
        locationArray[8].p3 = "temp";
        locationArray[8].p4 = "temp";
        locationArray[8].p5 =  locationArray[7].identifier;

    }


}  // the end


// JOptionPane.showMessageDialog(null, "" );

