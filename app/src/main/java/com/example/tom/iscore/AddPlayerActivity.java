package com.example.tom.iscore;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class AddPlayerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    //Player attribute variables set to default values
    String _playerName = "";
    int _playerAge = 0;
    String _playerGender = "";
    String _playerHand = "";

    EditText playerNameText;
    EditText PlayerAgeText;

    DBHandler db = new DBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);
        //Force to open portrait
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Find the button and TextViews so they can be edited
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
                Boolean playerAdded = false; //Default value can only be updated if data saved correctly

                _playerName = String.valueOf(playerNameText.getText());

                try
                {
                    //Read the age in as a integer
                    _playerAge = Integer.parseInt(playerAgeText.getText().toString());
                } catch (NumberFormatException numForEx) {
                    /*
                    This will only run if an age is not set.
                    Error due to trying to convert "" to int.
                    Set age to -1 so that it is caught by validation and user has to add info
                     */
                    _playerAge = -1;

                }

                //_playerHand is set above
                //_playerHand is set whenever a value is added to it.

                //generate a small message to tell user if save worked or not.
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                CharSequence msg;

                /*
                Validate inputs for Age >0 & <200.
                Others are either unvalidated or predefined input only
                Check that no values are default
                 */
                if((_playerAge > 0) &&
                        (_playerAge < 200) &&
                        !(_playerName.equals("")) &&
                        !(_playerHand.equals("")) &&
                        !(_playerGender.equals("")))
                {
                    //.addPlayer returns true if the player was written successfully and false if not
                    playerAdded = db.addPlayer(_playerName, _playerAge, _playerGender, _playerHand);



                    if (playerAdded)
                    {
                        /*
                        Display a success message to the user and exit the screen back to the SelectPlayerActivity
                         */
                        msg = "Player saved.";
                        Toast toast = Toast.makeText(context, msg, duration);
                        toast.show(); //Display message
                        finish(); //Exit screen
                        return;
                    }
                    else
                    {
                        /*
                        Display an error message if something went wrong with the save
                         */
                        msg = "Something went wrong, check player details.";
                        duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, msg, duration);
                        toast.show(); //Display message
                    }

                }
                else
                {
                    /*
                    Display an error message is one or more of the inputs is invalid
                     */
                    msg = "Something went wrong, check player details.";
                    duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, msg, duration);
                    toast.show(); //display message
                }

            }
        });
    }


    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        /*
        Function to set value to _playerGender when an option is selected
         */

        //From the position selected get the String value of the option selected
        _playerGender = (String) parent.getItemAtPosition(pos);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        /*
        Function to set the player gender to a default if no option is selected.
         */
        _playerGender = "";
    }

    public void onRadioButtonClicked(View view) {
        /*
        Function to handle clicks on a radio button and update the player hand based on the
        radio button currently selected.
         */

        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.setRightHand: //Right hand
                if (checked) //Check that this button is checked
                    _playerHand = "Right";
                break;
            case R.id.setLeftHand: //Left hand
                if (checked) //Check that this button is checked
                    _playerHand = "Left";
                break;
            default: //If neither
                _playerHand = "";
                break;
        }
    }
}
