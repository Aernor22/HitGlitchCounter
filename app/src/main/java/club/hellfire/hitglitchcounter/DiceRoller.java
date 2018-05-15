package club.hellfire.hitglitchcounter;

import android.util.Log;

/**
 * Created by God on 2/20/2018.
 */

public class DiceRoller {
    private int qt;
    private int glitch;
    private int hit;
    private int total;
    private StringBuilder rolls;
    private int linecounter;
    public DiceRoller(){ }

    public void roll(int qt){
        this.linecounter = 0;
        this.setGlitch(0);
        this.setHit(0);
        this.setTotal(0);
        this.setRolls(new StringBuilder());
        this.qt=qt;
        int roll;

        for(int i=0;i<qt;i++){
            roll = (int) (Math.random() * 6) + 1;
            if(roll>=5){
                setHit(getHit() + 1);
            }else{
                if(roll==1){
                    setGlitch(getGlitch() + 1);
                }
            }
            Log.d("roll",String.valueOf(roll));
            if(i!=(qt-1)){ rolls.append(roll+",      ");}else{rolls.append(roll);}

            linecounter++;
            if(linecounter==6){linecounter=0; rolls.append("\n");}


            setTotal(getTotal()+roll);
        }
        Log.d("TOTAL",String.valueOf(total));
    }

    public int getGlitch() {
        return glitch;
    }

    public void setGlitch(int glitch) {
        this.glitch = glitch;
    }

    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public StringBuilder getRolls() {
        return rolls;
    }

    public void setRolls(StringBuilder rolls) {
        this.rolls = rolls;
    }
}
