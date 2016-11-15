package com.example.prorock.lab1;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

    int ratingDialogState = 0;
    // Default value                0
    // If "Cancel" pressed equals   1
    // If "Rate" pressed equals     2
    Context currentContext;

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
                ratingDialogState = 2;
                dismiss();
            break;
            case R.id.button_close:
                ratingDialogState = 1;
                dismiss();
            break;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (ratingDialogState == 2) {
            final ProgressDialog pd = new ProgressDialog(getActivity());
            pd.setTitle(R.string.rate_progress_title);
            pd.setMessage(getContext().getResources().getString(R.string.rate_progress_plsWait));
            pd.show();
            Handler h = new Handler();
            currentContext = getContext();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    pd.dismiss();
                    Toast.makeText(currentContext, R.string.toast_thanks, Toast.LENGTH_SHORT).show();
                }
            }, 1200);

            //Toast.makeText(getContext(), R.string.toast_thanks, Toast.LENGTH_SHORT).show();
        }

    }


/*
    private void rate_showToast()
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
*/

}
