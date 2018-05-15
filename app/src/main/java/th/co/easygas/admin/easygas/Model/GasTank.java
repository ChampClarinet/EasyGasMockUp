package th.co.easygas.admin.easygas.Model;

import java.io.Serializable;

public class GasTank implements Serializable{

    private final int tankId;
    private int tankPercentage;

    public GasTank(int tankId, int tankPercentage) {
        this.tankId = tankId;
        this.tankPercentage = tankPercentage;
    }

    public GasTank(int tankId) {
        this(tankId, 0);
    }

    public int getTankId() {
        return tankId;
    }

    public int getTankPercentage() {
        return tankPercentage;
    }

    public void setTankPercentage(int tankPercentage) {
        this.tankPercentage = tankPercentage;
    }

}
