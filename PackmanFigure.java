import java.awt.*;
import java.awt.event.KeyEvent;

public class PackmanFigure {
    public int dir = 2;
    /*
    0 שמאלה
    1 למעלה
    2 ימינה
    3 למטה
    */
    public NewPanels currentPanel;
    public int crazyMode = 0;

    PackmanFigure(NewPanels currentPanel) {
        this.currentPanel = currentPanel;
    }
    public void setDir(int dir) {
        this.dir = dir;
    }
    public int getDir() {
        return this.dir;
    }
    public NewPanels getCurrentPanel(){
        //System.out.println("im here"+this.currentPanel.toString());
        return this.currentPanel;
    }
    public void setCurrentPanel(NewPanels currentPanel) {
        this.currentPanel = currentPanel;
    }
    /*public boolean changeLoc(int move, NewPanels p){
        System.out.println("#1: move="+move);
        boolean bool=false;
        if(move==0){
            this.setDir(0);
            if(this.getCurrentPanel().canBefore()) {
                this.currentPanel = this.getCurrentPanel().getBeforePanel();
                bool=true;
            }
        }
        System.out.println("#2 move="+move);
        if(move==1){
            this.setDir(1);
            if(this.getCurrentPanel().canUp()) {
                bool=true;
                this.currentPanel=this.getCurrentPanel().getUpPanel();
            }
        }
        System.out.println("#3 move="+move);
        if(move==2){
            this.setDir(2);
            if(this.getCurrentPanel().canNext()) {
                bool = true;
                this.currentPanel = this.getCurrentPanel().getNextPanel();
            }
        }
        System.out.println("#4 move="+move);
        if(move==3){
            //System.out.println(p.toString());
            System.out.println(this.getCurrentPanel().canDown()+" hey");
            this.dir=3;
            if(this.getCurrentPanel().canDown()) {
                bool=true;
                this.currentPanel = this.getCurrentPanel().getDownPanel();
            }
        }
        /*switch (move){
            case 0:
                this.setDir(0);
                if(this.getCurrentPanel().canBefore()) {
                    this.currentPanel = this.getCurrentPanel().getBeforePanel();
                    bool=true;
                }
                //return (this.getCurrentPanel().canBefore());
                break;
            case 1:
                this.setDir(1);
                if(this.getCurrentPanel().canUp()) {
                    bool=true;
                    this.currentPanel=this.getCurrentPanel().getUpPanel();
                }
                break;
                //return (this.getCurrentPanel().canUp());
            case 2:
                this.setDir(2);
                if(this.getCurrentPanel().canNext()) {
                    bool = true;
                    this.currentPanel = this.getCurrentPanel().getNextPanel();
                }
                break;
                //return (this.getCurrentPanel().canNext());
            case 3:
                System.out.println(this.getCurrentPanel().canDown()+" hey");
                this.dir=3;
                if(this.getCurrentPanel().canDown()) {
                    bool=true;
                    this.currentPanel = this.getCurrentPanel().getDownPanel();
                }
                //return (this.getCurrentPanel().canDown());
                break;
        }
        return bool;
    }*/
    @Override
    public String toString() {
        if(this.dir==0)
            return ("direction is left, currentPanel="+this.currentPanel.toString());
        if(this.dir==1)
            return ("direction is up, currentPanel="+this.currentPanel.toString());
        if(this.dir==2)
            return ("direction is right, currentPanel="+this.currentPanel.toString());
        return ("direction is down, currentPanel="+this.currentPanel.toString());
    }
}

