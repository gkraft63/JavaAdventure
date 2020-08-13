package misc;

public class Location_TownGate extends SuperLocation{
    public Location_TownGate(){
        name = "Town Gate";
        identifier = "townGate";
        mainText = "You are at the gate of the town. \nA guard is standing in front of you. \n\nWhat do you do?";
        c1 = "Talk to the guard";
        c2 = "Attack the guard";
        c3 = "Go South";
        c4 = "";
        c5 = "";
        p1 = "talkGuard";
        p2 = "attackGuard";
        p3 = "crossRoad";
        p4 = "";

      }
}
