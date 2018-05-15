package th.co.easygas.admin.easygas.Core;

import android.widget.ProgressBar;
import android.widget.TextView;

public class GasBarController {

    private final ProgressBar progressBar;
    private final TextView percentageTextView;
    private int percent;

    public GasBarController(ProgressBar progressBar, TextView percentageTextView, int percent) {
        this.progressBar = progressBar;
        this.percentageTextView = percentageTextView;
        this.percent = percent;
        resetGauge();
    }

    public GasBarController(ProgressBar progressBar, TextView percentageTextView) {
        this(progressBar, percentageTextView, 0);
    }

    private void resetGauge() {
        progressBar.setProgress(percent);
        String s = percent + "%";
        percentageTextView.setText(s);
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public TextView getPercentageTextView() {
        return percentageTextView;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
        resetGauge();
    }

}
