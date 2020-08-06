package ikraftsoftware.com;

import misc.Location_TownGate;
import misc.SuperLocation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game {
    ChoiceHandler cHandler = new ChoiceHandler();
    UI ui = new UI();
    VisibilityManager vm = new VisibilityManager(ui);
    Story story = new Story(this,ui,vm);
    String nextPosition1,nextPosition2,nextPosition3,nextPosition4,nextPosition5;


    public Game() {
         this.ui.createUI(cHandler);
         story.defaultSetup();
         vm.showTitleScreen();
    }

    public class ChoiceHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
         String yourChoice = e.getActionCommand();
         switch (yourChoice){
             case "start":
                 vm.hideTitleScreen();
                 story.townGate();
                  break;
             case "c1":story.selectPosition(nextPosition1);
                 story.playSound("src/click.wav");
             break;
             case "c2": story.selectPosition(nextPosition2);
                 story.playSound("src/click.wav");
             break;
             case "c3": story.selectPosition(nextPosition3);
                 story.playSound("src/click.wav");
             break;
             case "c4": story.selectPosition(nextPosition4);
                 story.playSound("src/click.wav");
             break;
             case "c5": story.selectPosition(nextPosition5);
                 story.playSound("src/click.wav");
                 break;
         }
        }
    }
}
