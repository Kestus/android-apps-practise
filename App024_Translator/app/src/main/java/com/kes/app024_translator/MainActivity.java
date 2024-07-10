package com.kes.app024_translator;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;
import com.kes.app024_translator.models.Options;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerFrom;
    private Spinner spinnerTo;
    private EditText inputTextView;
    private TextView outputTextView;

    private Button translateBtn;
    private ImageView swapButton;

    private ProgressBar progressBar;

    public static final int REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        spinnerFrom = findViewById(R.id.spinner_from);
        spinnerTo = findViewById(R.id.spinner_to);
        inputTextView = findViewById(R.id.text_input);
        outputTextView = findViewById(R.id.text_output);

        translateBtn = findViewById(R.id.translate_button);
        swapButton = findViewById(R.id.swap_button);

        progressBar= findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

        ArrayAdapter optionsAdapter = new ArrayAdapter (
                this,
                R.layout.spinner_item,
                Options.getOptionsStrings()
        );

        spinnerFrom.setAdapter(optionsAdapter);
        spinnerTo.setAdapter(optionsAdapter);
        spinnerFrom.setSelection(0);
        spinnerTo.setSelection(1);

        spinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        swapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int positionFrom = spinnerFrom.getSelectedItemPosition();
                int positionTo = spinnerTo.getSelectedItemPosition();
                spinnerFrom.setSelection(positionTo, true);
                spinnerTo.setSelection(positionFrom, true);
            }
        });

        translateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outputTextView.setText("");
                int positionFrom = spinnerFrom.getSelectedItemPosition();
                int positionTo = spinnerTo.getSelectedItemPosition();

                if (inputTextView.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please Enter Text!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (positionFrom == positionTo) {
                    outputTextView.setText(inputTextView.getText().toString());
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                String inputText = inputTextView.getText().toString();

                String languageCodeFrom = Options.getLanguageCode(positionFrom);
                String languageCodeTo = Options.getLanguageCode(positionTo);

                TranslateText(inputText, languageCodeFrom, languageCodeTo);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void TranslateText(String inputText, String languageCodeFrom, String languageCodeTo) {
        TranslatorOptions options = new TranslatorOptions.Builder()
                        .setSourceLanguage(languageCodeFrom)
                        .setTargetLanguage(languageCodeTo)
                        .build();

        Translator translator = Translation.getClient(options);

        DownloadConditions conditions = new DownloadConditions.Builder().build();

        outputTextView.setText("Downloading translator...");
        translator.downloadModelIfNeeded(conditions).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                outputTextView.setText("Translating...");
                translator.translate(inputText).addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        outputTextView.setText(s);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Failed to translate", Toast.LENGTH_LONG).show();
                        outputTextView.setText("");
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Failed To Download translator!", Toast.LENGTH_LONG).show();
                outputTextView.setText("");
            }
        });
    }


}