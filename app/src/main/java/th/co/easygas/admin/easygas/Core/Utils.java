package th.co.easygas.admin.easygas.Core;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import th.co.easygas.admin.easygas.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class Utils {

    private static AlertDialog dialog;

    public static AlertDialog createLoadDialog(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.loading_layout, null);
        TextView tvLoading = layout.findViewById(R.id.loadingTextView);
        tvLoading.setText(message);
        builder.setView(layout);
        dialog = builder.create();
        Log.d("createdDialog", message);
        dialog.show();
        return dialog;
    }

    public static void dismissLoadDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

}
