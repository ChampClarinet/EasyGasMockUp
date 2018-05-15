package th.co.easygas.admin.easygas;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import th.co.easygas.admin.easygas.Model.GasTank;

import static th.co.easygas.admin.easygas.Core.Utils.dismissLoadDialog;

public class GasTankActivity extends AppCompatActivity {

    private static final String TAG = GasTankActivity.class.getSimpleName();

    private GasTank gasTank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_tank);
        ButterKnife.bind(this);

        Bundle args = getIntent().getExtras();
        gasTank = (GasTank) args.getSerializable(getString(R.string.model_name_gas_tank));
        dismissLoadDialog();

        //uncomment it and set style to NoActionBar while using collapse xml
        /*setSupportActionBar(mGasToolbar);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    mCollapsingToolbarLayout.setTitle(getString(R.string.app_name));
                    isShow = false;
                } else if (!isShow) {
                    mCollapsingToolbarLayout.setTitle(" "); // Careful! There should be a space between double quote. Otherwise it won't work.
                    isShow = true;
                }
            }
        });*/
        if (gasTank != null) {
            String labelId = getString(R.string.label_id, gasTank.getTankId());
            mGasIdTextView.setText(labelId);
            String labelPercentage = gasTank.getTankPercentage() + "%";
            mGasLeftTextView.setText(labelPercentage);
        } else Log.d(TAG + ": GasTank", "null");

        getBackIcon();

        /*mAutoReorderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });*/

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getBackIcon() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @BindView(R.id.gas_id_text_view)
    TextView mGasIdTextView;
    @BindView(R.id.main_gas_left_text_view)
    TextView mGasLeftTextView;
    @BindView(R.id.auto_reorder_switch)
    Switch mAutoReorderSwitch;

/*
    @BindView(R.id.gas_toolbar_collapasable)
    Toolbar mGasToolbarCollapsable;
    @BindView(R.id.gas_app_bar_collapasable)
    AppBarLayout mAppBarLayoutCollapsable;
    @BindView(R.id.gas_collapse_layout_collapasable)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.gas_id_text_view_collapasable)
    TextView mGasIdTextViewCollapsable;
    @BindView(R.id.main_gas_left_text_view_collapasable)
    TextView mGasLeftTextViewCollapsable;
*/

}
