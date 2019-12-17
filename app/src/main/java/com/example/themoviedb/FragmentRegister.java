package com.example.themoviedb;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class FragmentRegister extends Fragment {
    private static final String TAG = "MovieRegister";

    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextPasswordAgain;

    private CheckBox checkBoxShowPassword;

    private Button buttonRegister;
    private Button buttonAddProfilePicture;

    private ImageView imageViewPrfilePicture;

    private DatabaseHelper databaseHelper;

    public FragmentRegister() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        ((MainActivity) getActivity()).setBottomNavigationMenu(MainActivity.MENU_START);

        editTextUsername = view.findViewById(R.id.editText_Register_Username);
        editTextPassword = view.findViewById(R.id.editText_Register_Password);
        editTextPasswordAgain = view.findViewById(R.id.editText_Register_PasswordAgain);

        checkBoxShowPassword = view.findViewById(R.id.checkBox_Register_ShowPassword);

        buttonRegister = view.findViewById(R.id.button_Register_SignUp);
        buttonAddProfilePicture = view.findViewById(R.id.button_Register_AddProfilePicture);

        imageViewPrfilePicture = view.findViewById(R.id.imageView_Register_ProfilePicture);

        databaseHelper = new DatabaseHelper(getContext());

        if (savedInstanceState != null){
            editTextUsername.setText(savedInstanceState.getString("username"));
            checkBoxShowPassword.setChecked(savedInstanceState.getBoolean("checked"));
        }

        checkBoxShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    editTextPassword.setTransformationMethod(null);
                    editTextPasswordAgain.setTransformationMethod(null);
                }
                else{
                    editTextPassword.setTransformationMethod(new PasswordTransformationMethod());
                    editTextPasswordAgain.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextPassword.getText().toString().equals(editTextPasswordAgain.getText().toString())){
                    Profile profile = new Profile(
                            editTextUsername.getText().toString(),
                            editTextPassword.getText().toString(),
                            ((BitmapDrawable)imageViewPrfilePicture.getDrawable()).getBitmap()
                    );
                    databaseHelper.register(profile);
                    Toast.makeText(getContext(), "Register successful", Toast.LENGTH_LONG).show();

                    ((MainActivity) getActivity()).replaceFragment(MainActivity.FRAGMENT_HOME);
                }
                else{
                    Toast.makeText(getContext(), "Check password!", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("username", editTextUsername.getText().toString());
        outState.putBoolean("checked", checkBoxShowPassword.isChecked());
    }
}
