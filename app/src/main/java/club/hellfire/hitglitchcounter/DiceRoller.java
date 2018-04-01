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
    public DiceRoller(){ }

    public void roll(int qt){
        this.setGlitch(0);
        this.setHit(0);
        this.setTotal(0);
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
            Log.d("ROLL",String.valueOf(roll));
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
}
