package com.im.logicsimulator;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class MainDriver extends AppCompatActivity {
    CurrentGame currentGame;
    MainView mainView;
    DisplayInfo display;
    ArrayList<String> saveNames;
    String selectedSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        display = new DisplayInfo(getWindowManager());
        setContentView(R.layout.activity_main);

        mainView = findViewById(R.id.mainView);
        mainView.setDisplay(display);
        currentGame = mainView.getCurrentGame();
        saveNames = new ArrayList<>();
        saveNames.addAll(Arrays.asList(fileList()));

        Button addNOR = findViewById(R.id.addNOR);
        Button addAnd = findViewById(R.id.addAnd);
        Button addOr = findViewById(R.id.addOr);
        Button addNot = findViewById(R.id.addNot);
        Button addXNOR = findViewById(R.id.addXNOR);
        Button addNAND = findViewById(R.id.addNAND);
        Button addXOR = findViewById(R.id.addXOR);
        Button addSwitch = findViewById(R.id.addSwitch);
        Button addLight = findViewById(R.id.addLight);
        Button moveButton = findViewById(R.id.movebutton);
        Button deleteButton = findViewById(R.id.deletebutton);
        Button saveButton = findViewById(R.id.testSave);
        Button loadButton = findViewById(R.id.loadButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainDriver.this);
                //Can only close save menu from pressing back button
                builder.setCancelable(false);
                builder.setTitle("Enter your desired save name");
                // Set up the input
                final EditText input = new EditText(MainDriver.this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                // Set up the buttons
                builder.setNegativeButton(Html.fromHtml("Save"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try{
                            String entered_name = input.getText().toString();
                            if(entered_name.trim().equals("")){
                                Toast.makeText(getApplicationContext(), "Please enter a non empty save name", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                saveNames.add(input.getText().toString());
                                FileOutputStream file = openFileOutput(input.getText().toString(), Context.MODE_PRIVATE);
                                ObjectOutputStream out = new ObjectOutputStream(file);
                                out.writeObject(currentGame.getUsedTools());
                                out.writeObject(currentGame.getWires());
                                file.close();
                                out.close();
                            }
                        }catch (IOException i){
                            Log.d("Serialize: ", "Objects not serialized");
                        }
                    }
                });
                builder.setPositiveButton(Html.fromHtml("Back"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainDriver.this);
                builderSingle.setCancelable(false);
                builderSingle.setTitle("Select a save to load");

                builderSingle.setSingleChoiceItems(saveNames.toArray(new String[0]), -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedSave = saveNames.get(which);
                    }
                });

                builderSingle.setNeutralButton("Load", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try{
                            FileInputStream file = openFileInput(selectedSave);
                            ObjectInputStream in = new ObjectInputStream(file);
                            currentGame.setUsedTools((HashSet)in.readObject());
                            currentGame.setWires((HashSet)in.readObject());
                            in.close();
                            file.close();
                            selectedSave = "";
                        }catch (IOException i){
                            Log.d("Deserialize: ", "Objects not deserialized");
                        }
                        catch (ClassNotFoundException i){
                            Log.d("Deserialize: ", "Objects not deserialized");
                        }
                    }
                });

                builderSingle.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteFile(selectedSave);
                        saveNames.remove(selectedSave);
                        selectedSave = "";
                    }
                });

                builderSingle.setPositiveButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        selectedSave = "";
                    }
                });

               builderSingle.show();
            }
        });

        addXOR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Adding XNOR Gate. Touch grid and drag to move ", Toast.LENGTH_SHORT).show();
                mainView.addTool(getResources(), R.drawable.xorgate);
            }
        });

        addNOR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Adding XNOR Gate. Touch grid and drag to move ", Toast.LENGTH_SHORT).show();
                mainView.addTool(getResources(), R.drawable.norgate);
            }
        });

        addXNOR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Adding XNOR Gate. Touch grid and drag to move ", Toast.LENGTH_SHORT).show();
                mainView.addTool(getResources(), R.drawable.xnorgate);
            }
        });
        addNAND.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Adding NAND Gate. Touch grid and drag to move ", Toast.LENGTH_SHORT).show();
                mainView.addTool(getResources(), R.drawable.nandgate);
            }
        });
        addAnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Adding AND Gate. Touch grid and drag to move ", Toast.LENGTH_SHORT).show();
                mainView.addTool(getResources(), R.drawable.andgate);
            }
        });
        addOr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Adding OR Gate. Touch grid and drag to move. ", Toast.LENGTH_SHORT).show();
                mainView.addTool(getResources(), R.drawable.orgate);
            }
        });
        addNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Adding NOT Gate. Touch grid and drag to move.", Toast.LENGTH_SHORT).show();
                mainView.addTool(getResources(), R.drawable.notgate);
            }
        });
        addSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Adding SWITCH. Touch grid and drag to move. ", Toast.LENGTH_SHORT).show();
                mainView.addTool(getResources(), R.drawable.offswitch);
            }
        });
        addLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Adding LIGHT BULB. Touch grid and drag to move.", Toast.LENGTH_SHORT).show();
                mainView.addTool(getResources(), R.drawable.lightbulboff);
            }
        });

        moveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Touch and drag and item to move.", Toast.LENGTH_SHORT).show();
                mainView.moveTool();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Touch an item to remove it.", Toast.LENGTH_SHORT).show();
                mainView.deletePressed();
            }
        });
    }

}