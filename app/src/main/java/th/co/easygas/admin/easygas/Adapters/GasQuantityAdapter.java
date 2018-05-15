package th.co.easygas.admin.easygas.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import th.co.easygas.admin.easygas.Core.GasBarController;
import th.co.easygas.admin.easygas.GasTankActivity;
import th.co.easygas.admin.easygas.Model.GasTank;
import th.co.easygas.admin.easygas.R;

import static th.co.easygas.admin.easygas.Core.Utils.createLoadDialog;

public class GasQuantityAdapter extends RecyclerView.Adapter<GasQuantityAdapter.GenericHolder> {

    private ArrayList<GasTank> dataSet;
    private Context context;

    public GasQuantityAdapter(ArrayList<GasTank> dataSet, Context context) {
        this.dataSet = dataSet;
        this.context = context;
    }

    @Override
    public GenericHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_gas_quantity, parent, false);
        return new ViewHolder(v, context);
    }

    @Override
    public void onBindViewHolder(GenericHolder holder, int position) {
        GasTank gasTank = dataSet.get(position);
        holder.setViewData(gasTank);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public ArrayList getData() {
        return dataSet;
    }

    public abstract static class GenericHolder extends RecyclerView.ViewHolder {

        public GenericHolder(View itemView) {
            super(itemView);
        }

        abstract public void setViewData(GasTank gasTank);

    }

    public static class ViewHolder extends GenericHolder {

        //bind cardview's widgets
        @BindView(R.id.cardview_gas_quantity)
        CardView cardViewGasQuantity;
        @BindView(R.id.cardview_gas_bar)
        ProgressBar cardViewGasBar;
        @BindView(R.id.cardview_id_text_view)
        TextView cardViewIdTextView;
        @BindView(R.id.cardview_gas_percentage)
        TextView cardViewPercentageTextView;
        private Context context;

        public ViewHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            ButterKnife.bind(this, itemView);
        }

        private void openStoreActivity(GasTank gasTank) {
            Intent i = new Intent(context, GasTankActivity.class);
            i.putExtra(context.getString(R.string.model_name_gas_tank), gasTank);
            createLoadDialog(context, context.getString(R.string.loading));
            context.startActivity(i);
        }

        @Override
        public void setViewData(final GasTank gasTank) {
            String label = context.getString(R.string.label_id, gasTank.getTankId());
            cardViewIdTextView.setText(label);

            GasBarController controller = new GasBarController(cardViewGasBar, cardViewPercentageTextView, gasTank.getTankPercentage());

            cardViewGasQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        openStoreActivity(gasTank);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        }

    }

    public String printList() {
        String out = "";
        for (GasTank s : dataSet) out += s.getTankId() + ", ";
        return out;
    }

}
