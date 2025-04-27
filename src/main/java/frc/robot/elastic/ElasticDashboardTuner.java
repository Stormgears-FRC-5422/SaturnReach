package frc.robot.elastic;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * Base class for publishing values to Elastic dashboard. Each tunable value is
 * published as a NetworkTableEntry that can be configured as a NumberSlider
 * widget in Elastic.
 */
public class ElasticDashboardTuner {

    protected final NetworkTable table;
    private final String name;

    public ElasticDashboardTuner(String tableName) {
        this.name = tableName;
        this.table = NetworkTableInstance.getDefault().getTable("Elastic/" + tableName);
    }

    public String getName() {
        return name;
    }

    public NetworkTable getTable() {
        return table;
    }
}
