package projetofragmento.cursoandroidkotlin.com.countries.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import projetofragmento.cursoandroidkotlin.com.countries.fragments.PaisesFragment;
import projetofragmento.cursoandroidkotlin.com.countries.fragments.VisitadosFragment;

/**
 * Created by Lupy on 07/03/2018.
 */

public class TabsAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private String[] abas = new String[]{"PAÍSES", "VISITADOS"};

    public TabsAdapter(FragmentManager fm, Context c) {
        super(fm);
        this.context = c;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;

        switch (position){

            case 0:
                fragment = new PaisesFragment();
                break;

            case 1:
                fragment = new VisitadosFragment();
                break;

        }

        return fragment;

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return  abas[position];
    }

    @Override
    public int getCount() {
        return abas.length;
    }
}
