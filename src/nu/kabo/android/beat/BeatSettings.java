package nu.kabo.android.beat;

// Import Android Stuff
import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class BeatSettings extends PreferenceActivity
{
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    addPreferencesFromResource(R.xml.preferences);
    PreferenceManager.setDefaultValues(BeatSettings.this, R.xml.preferences, false);
  }
}
