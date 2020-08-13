package ikraftsoftware.com;

public class VisibilityManager {
    UI ui;
    public VisibilityManager(UI userInterface){
        ui = userInterface;
    }
    public void showTitleScreen(){
        // Show Title Screen
        ui.titleNamePanel.setVisible(true);
        ui.startButtonPanel.setVisible(true);
        // Hide Game Scree stuff
        ui.mainTextPanel.setVisible(false);
        ui.choiceButtonPanel.setVisible(false);
        ui.playerPanel1.setVisible(false);
        ui.playerPanel2.setVisible(false);
    }
    public void hideTitleScreen(){
        // Hide Title Screen
        ui.titleNamePanel.setVisible(false);
        ui.startButtonPanel.setVisible(false);
        // Show Game Scree stuff
        ui.mainTextPanel.setVisible(true);
        ui.choiceButtonPanel.setVisible(true);
        ui.playerPanel1.setVisible(true);
        ui.playerPanel2.setVisible(true);

    }
}
