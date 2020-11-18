package android.example.com.visualizerpreferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.pref_visualizer);

        //SharedPreferences
        SharedPreferences sharedPreferences=getPreferenceScreen().getSharedPreferences();
        PreferenceScreen preferenceScreen=getPreferenceScreen();
        int pref_count=preferenceScreen.getPreferenceCount();

        for (int i=0;i<pref_count;i++)
        {
            Preference preference=preferenceScreen.getPreference(i);
            Check_Preference_And_Set(sharedPreferences, preference);
        }
    }

    private void Check_Preference_And_Set(SharedPreferences sharedPreferences, Preference preference) {
        if(!(preference instanceof CheckBoxPreference))
        {
            String value=sharedPreferences.getString(preference.getKey(),"");
            Set_preference_summary(preference,value);
        }
    }


    private void Set_preference_summary(Preference preference,String value)
    {
        if(preference instanceof ListPreference)
        {
            ListPreference listPreference= (ListPreference) preference;
            int index_pref=listPreference.findIndexOfValue(value);
            if(index_pref>=0)
            {
                listPreference.setSummary(listPreference.getEntries()[index_pref]);
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference=findPreference(key);
        if(preference!=null){
            Check_Preference_And_Set(sharedPreferences,preference);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
