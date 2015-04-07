package ivansued.com.weatherapp.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

import ivansued.com.weatherapp.R;

/**
 * Created by isued on 2/20/2015.
 */
public class AlertDialogFragment extends DialogFragment {

    public static AlertDialogFragment newInstance(String title, String message){
        AlertDialogFragment dialog = new AlertDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("message", message);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();

        String title = getArguments().getString("title");
        String message = getArguments().getString("message");

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(context.getString(R.string.error_ok_button), null);

        AlertDialog dialog = builder.create();
        return dialog;
    }
}
