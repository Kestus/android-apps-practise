package com.example.app016_contactmanager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.example.app016_contactmanager2.databinding.ActivityAddNewContactBinding;
import com.example.app016_contactmanager2.db.entities.Contact;

public class AddContactActivity extends AppCompatActivity {

    private ActivityAddNewContactBinding activityAddNewContactBinding;
    private AddContactActivityClickHandlers clickHandlers;
    Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_new_contact);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Data Binding Contact
        contact = new Contact();
        activityAddNewContactBinding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_add_new_contact
        );
        activityAddNewContactBinding.setContact(contact);

        clickHandlers = new AddContactActivityClickHandlers(getApplicationContext());
        activityAddNewContactBinding.setClickHandler(clickHandlers);


    }

    public class AddContactActivityClickHandlers {

        Context context;

        public AddContactActivityClickHandlers(Context context) {
            this.context = context;
        }

        public void onSaveClicked(View view) {
            if (contact.getName() == null) {
                Toast.makeText(context, "Name should not be empty!", Toast.LENGTH_SHORT).show();
            } else {
                Intent i = new Intent();
                i.putExtra("NAME", contact.getName());
                i.putExtra("EMAIL", contact.getEmail());

                setResult(RESULT_OK, i);
                finish();
            }
        }
    }
}