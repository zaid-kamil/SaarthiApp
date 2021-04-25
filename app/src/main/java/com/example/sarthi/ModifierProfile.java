package com.example.sarthi;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class ModifierProfile extends AppCompatActivity {

    FirebaseAuth mAuth = null;
    DatabaseReference mDatabase;
    DatePickerDialog.OnDateSetListener mDateListener;
    EditText nom;
    EditText email;
    EditText phone;
    EditText voiture;
    EditText sexe;
    EditText nationalité;
    EditText ambiance;
    EditText cigarette;
    EditText discussion;
    EditText dateNaissance;
    Button enregistrer;
    EditText propos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifierprofile_layout);

        mAuth = FirebaseAuth.getInstance();


        Toolbar myToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Modify Your Profile");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String UserUid = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference("users").child(UserUid);

        sexe = findViewById(R.id.sex);
        nationalité = findViewById(R.id.nationalité);
        ambiance = findViewById(R.id.atmosphere);
        cigarette = findViewById(R.id.cigarette);
        discussion = findViewById(R.id.discussion);
        dateNaissance = findViewById(R.id.birthday);
        nom = findViewById(R.id.etUsername);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.telephone);
        propos = findViewById(R.id.apropos);
        voiture = findViewById(R.id.vehicle);


        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user oldone = dataSnapshot.getValue(user.class);
                nom.setText(oldone.getName());
                email.setText(oldone.getEmail());
                phone.setText(oldone.getPhone());
                dateNaissance.setText(oldone.getDateNaissance());
                sexe.setText(oldone.getSexe());
                nationalité.setText(oldone.getNationalité());
                voiture.setText(oldone.getVoiture());
                ambiance.setText(oldone.getAmbiance());
                cigarette.setText(oldone.getCigarette());
                discussion.setText(oldone.getDiscussion());
                propos.setText(oldone.getPropos());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        final CharSequence[] sex = {"Male", "Female"};
        final CharSequence[] mNation = new String[]{"Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegowina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory", "Brunei Darussalam", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo", "Congo, the Democratic Republic of the", "Cook Islands", "Costa Rica", "Cote d'Ivoire", "Croatia (Hrvatska)", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands (Malvinas)", "Faroe Islands", "Fiji", "Finland", "France", "France Metropolitan", "French Guiana", "French Polynesia", "French Southern Territories", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Heard and Mc Donald Islands", "Holy See (Vatican City State)", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran (Islamic Republic of)", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea, Democratic People's Republic of", "Korea, Republic of", "Kuwait", "Kyrgyzstan", "Lao, People's Democratic Republic", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libyan Arab Jamahiriya", "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia, The Former Yugoslav Republic of", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia, Federated States of", "Moldova, Republic of", "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn", "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russian Federation", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone", "Singapore", "Slovakia (Slovak Republic)", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Georgia and the South Sandwich Islands", "Spain", "Sri Lanka", "St. Helena", "St. Pierre and Miquelon", "Sudan", "Suriname", "Svalbard and Jan Mayen Islands", "Swaziland", "Sweden", "Switzerland", "Syrian Arab Republic", "Taiwan, Province of China", "Tajikistan", "Tanzania, United Republic of", "Thailand", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "United States Minor Outlying Islands", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "Virgin Islands (British)", "Virgin Islands (U.S.)", "Wallis and Futuna Islands", "Western Sahara", "Yemen", "Yugoslavia", "Zambia", "Zimbabwe", "Palestine"};

        final CharSequence[] mAmiance = {"I like listening to music", "I like to listen to the news", "I like calm in the car"};
        final CharSequence[] mCigarette = {"The Cigarette doesn't bother me", "The Cigarette does bother me"};
        final CharSequence[] mDiscussion = {"I like to discuss", "I chat from time to time", "I am rather discreet in the car"};


        sexe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(sex, "I am :", sexe);
            }
        });
        nationalité.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(mNation, "My nationality:", nationalité);
            }
        });
        ambiance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(mAmiance, "Atmosphere in the car:", ambiance);
            }
        });
        cigarette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(mCigarette, "Cigarette :", cigarette);
            }
        });
        discussion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(mDiscussion, "Discussions in the car :", discussion);
            }
        });
        dateNaissance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(ModifierProfile.this,
                        R.style.CustomDatePickerDialog,
                        mDateListener,
                        year, month, day);

                dialog.getDatePicker().setMaxDate(cal.getTimeInMillis());

                dialog.show();
            }
        });


        mDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String monthlet = "";
                switch (month) {
                    case 1:
                        monthlet = "Jan";
                        break;
                    case 2:
                        monthlet = "Feb";
                        break;
                    case 3:
                        monthlet = "Mar";
                        break;
                    case 4:
                        monthlet = "Apr";
                        break;
                    case 5:
                        monthlet = "May";
                        break;
                    case 6:
                        monthlet = "Jun";
                        break;
                    case 7:
                        monthlet = "July";
                        break;
                    case 8:
                        monthlet = "Aug";
                        break;
                    case 9:
                        monthlet = "Sep";
                        break;
                    case 10:
                        monthlet = "Oct";
                        break;
                    case 11:
                        monthlet = "Nov";
                        break;
                    case 12:
                        monthlet = "Dec";
                        break;
                }
                String date = dayOfMonth + " - " + monthlet + " - " + year;
                dateNaissance.setText(date);
            }
        };

        enregistrer = findViewById(R.id.register);
        enregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nom.getText().toString().equals("") && !email.getText().toString().equals("") && !phone.getText().toString().equals("")
                        && !dateNaissance.getText().toString().equals("")) {
                    user newOne = new user(UserUid, nom.getText().toString(), email.getText().toString(), phone.getText().toString(), nationalité.getText().toString(),
                            dateNaissance.getText().toString(), sexe.getText().toString(), voiture.getText().toString(), ambiance.getText().toString(),
                            cigarette.getText().toString(), discussion.getText().toString(), propos.getText().toString());

                    mDatabase.setValue(newOne);

                    Intent goToProfile = new Intent(ModifierProfile.this, Profile.class);
                    startActivity(goToProfile);
                } else {
                    Toast.makeText(ModifierProfile.this, "Fill all the details ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void showDialog(final CharSequence[] mList, String title, final EditText mEdit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ModifierProfile.this);
        builder.setTitle(title);
        // final CharSequence[] mSexe = {"un Homme","une Femme"};
        builder.setItems(mList, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mEdit.setText(mList[which].toString());
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
