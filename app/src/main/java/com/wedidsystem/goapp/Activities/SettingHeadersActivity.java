package com.wedidsystem.goapp.Activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

import com.wedidsystem.goapp.R;

import java.util.List;

import androidx.annotation.Nullable;

public class SettingHeadersActivity extends PreferenceActivity {
    @Override
    public void onBuildHeaders(List<Header> target) {
        super.onBuildHeaders(target);
        loadHeadersFromResource(R.xml.setting_headers, target);
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName)
                || SettingHeadersFragment.class.getName().equals(fragmentName);
    }

    public static class SettingHeadersFragment extends PreferenceFragment{
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            String settings = getArguments().getString("header");
            if ("pref_faq".equals(settings)){
                addPreferencesFromResource(R.xml.pref_faq);
            }else if ("pref_privacy_policy".equals(settings)){
                addPreferencesFromResource(R.xml.pref_privacy_policy);
            }else if ("pref_terms".equals(settings)){
                addPreferencesFromResource(R.xml.pref_terms);
            }
        }
    }
}
