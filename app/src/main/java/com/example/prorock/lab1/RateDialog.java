package com.example.prorock.lab1;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class RateDialog extends android.support.v4.app.DialogFragment implements OnClickListener {


    public RateDialog() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle(R.string.rate_this_app);

        View v= inflater.inflate(R.layout.activity_rate, null);
        v.findViewById(R.id.button_close).setOnClickListener(this);
        v.findViewById(R.id.button_rate).setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.button_rate:
                rate_onClick();
                //dismiss();
            break;
            case R.id.button_close:
                dismiss();
            break;
        }

    }

    private void rate_onClick()
    {
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), R.string.toast_thanks, Toast.LENGTH_SHORT).show();
                dismiss();
            }
        }, 1200);
    }

}
