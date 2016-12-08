package com.example.tom.iscore;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

public class AddPlayerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String _playerName;
    int _playerAge;
    String _playerGender;
    String _playerHand;

    EditText playerNameText;
    EditText PlayerAgeText;

    DBHandler db = new DBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);
        //Force to open portrait
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Button saveButton = (Button) findViewById(R.id.SaveButton);
        final EditText playerNameText = (EditText) findViewById(R.id.inputPlayerName);
        final EditText playerAgeText = (EditText) findViewById(R.id.inputPlayerAge);


        //Add gender options to the spinner
        Spinner genderSpinner = (Spinner) findViewById(R.id.inputPlayerGender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.GenderOptions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);
        genderSpinner.setOnItemSelectedListener(this);






        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _playerName = String.valueOf(playerNameText.getText());
                _playerAge = Integer.parseInt(playerAgeText.getText().toString());
                //_playerHand is set above
                //_playerHand is set whenever a value is added to it.

                /*
                Validate inputs for Age.
                Others are either unvalidatable or predefined input only
                 */
                if((_playerAge > 0) && (_playerAge < 200))
                {
                    //.addPlayer returns true if the player was written succesfully and false if not
                    Boolean playerAdded = db.addPlayer(_playerName, _playerAge, _playerGender, _playerHand);

                    //generate a small message to tell user if save worked or not.
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    CharSequence msg;

                    if (playerAdded)
                    {
                        msg = "Player saved.";
                        Toast toast = Toast.makeText(context, msg, duration);
                        toast.show();
                        finish();
                        return;
                    }
                    else
                    {
                        msg = "Somethig went wrong, check player details.";
                        duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, msg, duration);
                        toast.show();
                    }

                }

            }
        });
    }


    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        /*
        Function to set value to _playerGender when an option is selected
         */
        _playerGender = (String) parent.getItemAtPosition(pos);

    }

    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.setRightHand:
                if (checked)
                    _playerHand = "Right";
                break;
            case R.id.setLeftHand:
                if (checked)
                    _playerHand = "Left";
                break;
        }
    }
}
