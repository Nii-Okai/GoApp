package com.wedidsystem.goapp.Activities;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.wedidsystem.goapp.R;

public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new MainSettingsFragment()).commit();
    }

 public static class MainSettingsFragment extends PreferenceFragment {
        @Override
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            bindSummaryValue(findPreference("key_password"));
            bindSummaryValue(findPreference("key_email"));
            bindSummaryValue(findPreference("key_email_providers"));
            //bindSummaryValue(findPreference("key_music_quality"));

            Preference myPref = findPreference("key_send_feedback");
            myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    sendFeedback(getActivity());
                    return true;
                }
            });

        }

     public static void sendFeedback(Context context) {
         String body = null;
         try {
             body = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
             body = "";
         } catch (PackageManager.NameNotFoundException e) {
         }
         Intent intent = new Intent(Intent.ACTION_SEND);
         intent.setType("message/rfc822");
         intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"ascorbicrose@gmail.com"});
         intent.putExtra(Intent.EXTRA_SUBJECT, "Send Feedback");
         intent.putExtra(Intent.EXTRA_TEXT, body);
         context.startActivity(Intent.createChooser(intent, context.getString(R.string.choose_email_client)));
     }
 }

private static void bindSummaryValue(Preference preference){
    preference.setOnPreferenceChangeListener(listener);
    listener.onPreferenceChange(preference, PreferenceManager.getDefaultSharedPreferences(preference.getContext())
            .getString(preference.getKey(), ""));
}

private static Preference.OnPreferenceChangeListener listener = new Preference.OnPreferenceChangeListener() {
    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String stringValue = newValue.toString();
        if (preference instanceof ListPreference){
            ListPreference listPreference = (ListPreference)preference;
            int index = listPreference.findIndexOfValue(stringValue);
            preference.setSummary(index >= 0 ? listPreference.getEntries()[index] : null);
        }else if (preference instanceof EditTextPreference){
            preference.setSummary(stringValue);
        }
        return true;
    }
};

}
