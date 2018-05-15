package th.co.easygas.admin.easygas;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import th.co.easygas.admin.easygas.Adapters.GasQuantityAdapter;
import th.co.easygas.admin.easygas.Model.GasTank;

public class MainActivity extends AppCompatActivity {

    private ArrayList<GasTank> gasTanks;
    private AlertDialog activeDialog;
    private int pendingTanks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mMainToolbar);
        String title = "Captain'O";

        setTitleBar(title);

        mockGasTanks();

        mAmountTextView.setText(getString(R.string.tanks_amount, gasTanks.size()));

        GasQuantityAdapter adapter = new GasQuantityAdapter(gasTanks, this);
        mTankRV.setLayoutManager(new LinearLayoutManager(this));
        mTankRV.setHasFixedSize(true);
        mTankRV.setAdapter(adapter);

        //load pending tanks amount from server
        pendingTanks = 0;
        //set listener. if percentage changes, update percentage by gasTank.getBarController.setPercent(newPercentage);

    }

    private void orderConfirmDialog(final int amount) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.order_confirmation_title));
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_order_confirmation, null);
        TextView t = layout.findViewById(R.id.confirmation_tank_label);
        t.setText(getString(R.string.label_confirmation, amount));
        t = layout.findViewById(R.id.confirmation_tank_price_label);
        t.setText(getString(R.string.total_price, calculatePrice(amount)));
        layout.findViewById(R.id.button_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pendingTanks += amount;
                dismissDialog();
                orderSuccessDialog(amount);
            }
        });
        layout.findViewById(R.id.button_cancel_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
            }
        });
        builder.setView(layout);
        AlertDialog dialog = builder.create();
        dialog.show();
        activeDialog = dialog;
    }

    private void orderSuccessDialog(int amount) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_order_success, null);
        TextView t = layout.findViewById(R.id.order_success_label);
        t.setText(getString(R.string.order_success_label, amount));
        builder.setView(layout);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void orderDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_new_tank, null);
        TextView lowGasAmount = layout.findViewById(R.id.low_gas_amount_label);
        lowGasAmount.setText(getString(R.string.label_gas_low_tank_amount, getLowGasTankAmount()));
        final TextView price = layout.findViewById(R.id.price_label);
        price.setText("0");
        final EditText amountEditText = layout.findViewById(R.id.input_amount_tank_order);
        amountEditText.requestFocus();
        final InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        amountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int x = 0;
                if (s.length() > 0) x = calculatePrice(Integer.parseInt(s.toString()));
                price.setText(Integer.toString(x));
            }
        });
        layout.findViewById(R.id.button_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = 0;
                String s = amountEditText.getText().toString();
                if (s.length() != 0) x = Integer.parseInt(s);
                dismissDialog();
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                orderConfirmDialog(x);
            }
        });
        layout.findViewById(R.id.button_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                dismissDialog();
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });
        builder.setView(layout);
        AlertDialog dialog = builder.create();
        dialog.show();
        activeDialog = dialog;
    }

    private void orderPendingDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_order, null);
        TextView t = layout.findViewById(R.id.ordered_tank_label);
        t.setText(getString(R.string.label_ordered, pendingTanks));
        builder.setView(layout);
        AlertDialog dialog = builder.create();
        dialog.show();
        activeDialog = dialog;
    }

    private void setTitleBar(String title) {
        mTitleTextView.setText(title);
        Glide.with(this).load(R.drawable.mock_title_image).centerCrop().into(mTitleImageView);
    }

    private int getLowGasTankAmount() {
        int count = 0;
        for (GasTank g : gasTanks) {
            if (g.getTankPercentage() <= 20) count++;
        }
        return count;
    }

    private int calculatePrice(int amount) {
        return amount * 300;
    }

    private void dismissDialog() {
        if (activeDialog != null) {
            activeDialog.dismiss();
            activeDialog = null;
        }
    }

    private void mockGasTanks() {
        gasTanks = new ArrayList<>();
        GasTank g;
        g = new GasTank(1, 20);
        gasTanks.add(g);
        g = new GasTank(2, 100);
        gasTanks.add(g);
        g = new GasTank(3, 100);
        gasTanks.add(g);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new_tank:
                orderDialog();
                break;
            case R.id.action_view_order:
                orderPendingDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @BindView(R.id.main_toolbar)
    Toolbar mMainToolbar;
    @BindView(R.id.main_title)
    TextView mTitleTextView;
    @BindView(R.id.main_title_image)
    CircleImageView mTitleImageView;
    @BindView(R.id.tanks_rv)
    RecyclerView mTankRV;
    @BindView(R.id.label_tanks_amount)
    TextView mAmountTextView;

}
