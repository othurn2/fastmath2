package com.example.oliverthurn.fastmath2;

import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class InstructionsFrag extends DialogFragment {

    protected TextView instructions;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.activity_instructions_frag, container, false);
        instructions = (TextView)root.findViewById(R.id.instructionsTV);
        instructions.setTextSize(Game.SMALL_TEXT_SIZE);
        return root;


    }
}
