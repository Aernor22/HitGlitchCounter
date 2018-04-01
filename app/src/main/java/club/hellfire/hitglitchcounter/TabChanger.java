package club.hellfire.hitglitchcounter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabChanger extends FragmentStatePagerAdapter {
    private AddRoll addRoll = new AddRoll();
    private VSRoll vSRoll = new VSRoll();
    private SpellsList spellsList = new SpellsList();
    //private Config_Supplemental_Feed config_supplemental_feed = new Config_Supplemental_Feed();
    //private Config_Colors config_colors = new Config_Colors();

    private Bundle b = new Bundle();        //puts the user data in a bundle

    public TabChanger(FragmentManager fm){
        super(fm);

    }

    @Override
    public int getCount(){ //the total number of pages available
        return 3;
    }

    public Fragment refreshItem(int position){
        switch (position){
            case 0:{
                AddRoll addRoll = new AddRoll();
                addRoll.setArguments(b);
                return addRoll;
            }
            case 1:{
                VSRoll vsRoll= new VSRoll();
                vsRoll.setArguments(b);
                return vsRoll;

            }
            case 2:{
                SpellsList spellsList = new SpellsList();
                spellsList.setArguments(b);
                return spellsList;
            }
        }
        return null;
    }

    /**
     * @param position is what item is required.
     * @return the fragment associated with the position.
     * Returns null if the position is invalid (less or bigger than the pages available)
     */
    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:{
                return addRoll;
            }
            case 1:{
                return vSRoll;
            }
            case 2:{
                return spellsList;
            }
        }
        return null;
    }
}
