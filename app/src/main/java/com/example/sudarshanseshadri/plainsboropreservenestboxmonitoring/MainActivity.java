package com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {



    /**
     * A dummy authentication store containing known user names.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "robin27", "finch33"
    };

    boolean successLogIn;
    //connected to internet or not
    boolean connected;
    boolean gotResult;


    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mIDView;
    private View mProgressView;
    private View mLoginFormView;
    private ImageView logo;
    SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set up the login form.
        mIDView = (EditText) findViewById(R.id.id_editText_IDEntry);
        logo=findViewById(R.id.id_imageView_audobonLogo);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        Button mIDSignInButton = (Button) findViewById(R.id.id_button_logIn);
        mIDSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin(mIDView.getText().toString());
            }
        });

        //check the SharedPrefs to see if a userID has already been stored there.
        mPrefs=getSharedPreferences("PlainsboroPrefs", Context.MODE_PRIVATE);
        String storedID;
        storedID = mPrefs.getString("userID", "");
        if (!storedID.equals("")&&storedID!=null)
        {
            attemptLogin(storedID.toString());
        }





    }




    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid ID, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin(String ID) {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mIDView.setError(null);

        // Store values at the time of the login attempt.

        boolean cancel = false;
        View focusView = null;



        // Check for a valid ID address.
        if (TextUtils.isEmpty(ID)) {
            mIDView.setError(getString(R.string.error_field_required));
            focusView = mIDView;
            cancel = true;
        } else if (!isIDValid(ID)) {
            mIDView.setError(getString(R.string.error_invalid_ID));
            focusView = mIDView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(ID);
            mAuthTask.execute((Void) null);
        }
    }



    private boolean isIDValid(String ID) {
        //TODO: Replace this with your own logic
        return true;
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);

            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            logo.setVisibility(show ? View.GONE : View.VISIBLE);

            logo.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });



            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only ID addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary ID addresses first. Note that there won't be
                // a primary ID address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> IDs = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            IDs.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }




    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mID;


        public UserLoginTask(String ID) {
            mID = ID;
        }


        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.


            try {

                //copy paste for firebase, change this



                DocumentReference docRef = FirebaseFirestore.getInstance().collection("users").document(mID);

                // asynchronously retrieve the document
                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        connected=true;
                        gotResult=true;
                        if (documentSnapshot.exists()) {
                            successLogIn=true;

                        }
                        else
                        {
                            successLogIn=false;
                        }
                    }
                });

                docRef.get().addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        gotResult=true;
                        connected=false;
                        successLogIn=false;
                    }
                });


                while (!gotResult)
                {
                    Thread.sleep(1);
                }
                gotResult=false;




                return successLogIn;


            }//try

            catch (Exception e) {
                return successLogIn;
            }


            //replace these with firestore
//            for (String credential : DUMMY_CREDENTIALS) {
//
//                if (credential.equals(mID)) {
//                    // Account exists, return true.
//                    return true;
//                }
//
//
//            }


        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {

                //first, save the successful ID to sharedPrefs.
                //OH BOY HOW FUN
                //r/verbal irony
                //is that a real subreddit?
                //nope. there is situational!

                String successfulID = mID;

                mPrefs=getSharedPreferences("PlainsboroPrefs", Context.MODE_PRIVATE);

                SharedPreferences.Editor prefsEditor = mPrefs.edit();

                prefsEditor.putString("userID", successfulID);
                prefsEditor.commit();





                //go to next activity

                Intent intent = new Intent(MainActivity.this, MainNavActivity.class);
                startActivity(intent);





            }
            else
            {
                if (connected==true){
                    mIDView.setError(getString(R.string.try_again));
                    mIDView.requestFocus();
                }
                else
                {
                    mIDView.setError("Please connect to the Internet and try again");
                    mIDView.requestFocus();
                    connected=true;
                }

            }

        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }


    }
}

