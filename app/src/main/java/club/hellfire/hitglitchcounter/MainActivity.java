package club.hellfire.hitglitchcounter;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends Activity {
    private  EditText edtQt;
    private TextView lblHitQt;
    private TextView lblGlitchQt;

    private TextView lblPastDie;
    private TextView lblPastHit;
    private TextView lblPastGlitch;

    private DiceRoller dr;
    private Boolean firstTime;
    private int pastQT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        pastQT = 0;
        dr = new DiceRoller();
        firstTime = true;
        edtQt = (EditText)findViewById(R.id.diceqt);
        lblHitQt = (TextView)findViewById(R.id.lblhitsresult);
        lblGlitchQt = (TextView)findViewById(R.id.lblglitchresult);

        lblPastDie = (TextView)findViewById(R.id.lblPastQt);
        lblPastHit = (TextView)findViewById(R.id.lblPastHit);
        lblPastGlitch = (TextView)findViewById(R.id.lblPastGlitch);

        Button btnRoll = (Button)findViewById(R.id.btnRoll);
        btnRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(edtQt.getText().toString().isEmpty()){
                        throw new Exception("Please insert a number on Dice quantity.");
                    }else{
                            int qt= Integer.valueOf(edtQt.getText().toString());
                            if(qt>0){
                                if(!firstTime){
                                    String aux = "Dice: "+ String.valueOf(pastQT);
                                    lblPastDie.setText(aux);
                                    aux = "Hit: "+lblHitQt.getText();
                                    lblPastHit.setText(aux);
                                    aux = "Glitch: "+lblGlitchQt.getText();
                                    lblPastGlitch.setText(aux);
                                }
                                pastQT=qt;
                                dr.roll(qt);
                                lblHitQt.setText(String.valueOf(dr.getHit()));
                                lblGlitchQt.setText(String.valueOf(dr.getGlitch()));
                                firstTime = false;
                            }else{
                                throw new Exception("The value should be bigger than 0");
                            }
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
