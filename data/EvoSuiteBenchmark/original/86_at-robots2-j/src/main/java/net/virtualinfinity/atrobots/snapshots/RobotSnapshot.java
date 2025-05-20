package net.virtualinfinity.atrobots.snapshots;

import net.virtualinfinity.atrobots.measures.AbsoluteAngle;
import net.virtualinfinity.atrobots.measures.Temperature;

/**
 * @author Daniel Pitts
 */
public class RobotSnapshot extends ArenaObjectSnapshot {
    private Temperature temperature;
    private double armor;
    private boolean overburn;
    private boolean activeShield;
    private AbsoluteAngle heading;
    private AbsoluteAngle turretHeading;
    private String name;
    private int id;
    private String lastMessage;
    private int roundKills;
    private int totalKills;
    private int totalDeaths;
    private int totalWins;
    private int totalTies;

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public void setArmor(double armor) {
        this.armor = armor;
    }

    public void setOverburn(boolean overburn) {
        this.overburn = overburn;
    }

    public void setActiveShield(boolean activeShield) {
        this.activeShield = activeShield;
    }

    public void visit(SnapshotVisitor visitor) {
        visitor.acceptRobot(this);
    }

    public void setHeading(AbsoluteAngle heading) {
        this.heading = heading;
    }

    public void setTurretHeading(AbsoluteAngle turretHeading) {
        this.turretHeading = turretHeading;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public double getArmor() {
        return armor;
    }

    public boolean isOverburn() {
        return overburn;
    }

    public boolean isActiveShield() {
        return activeShield;
    }

    public AbsoluteAngle getHeading() {
        return heading;
    }

    public AbsoluteAngle getTurretHeading() {
        return turretHeading;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public int getRoundKills() {
        return roundKills;
    }

    public void setRoundKills(int roundKills) {
        this.roundKills = roundKills;
    }

    public int getTotalKills() {
        return totalKills;
    }

    public void setTotalKills(int totalKills) {
        this.totalKills = totalKills;
    }

    public int getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(int totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public int getTotalWins() {
        return totalWins;
    }

    public void setTotalWins(int totalWins) {
        this.totalWins = totalWins;
    }

    public int getTotalTies() {
        return totalTies;
    }

    public void setTotalTies(int totalTies) {
        this.totalTies = totalTies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RobotSnapshot that = (RobotSnapshot) o;

        if (activeShield != that.activeShield) return false;
        if (Double.compare(that.armor, armor) != 0) return false;
        if (id != that.id) return false;
        if (overburn != that.overburn) return false;
        if (roundKills != that.roundKills) return false;
        if (totalDeaths != that.totalDeaths) return false;
        if (totalWins != that.totalWins) return false;
        if (totalTies != that.totalTies) return false;
        if (totalKills != that.totalKills) return false;
        if (!heading.equals(that.heading)) return false;
        if (lastMessage != null ? !lastMessage.equals(that.lastMessage) : that.lastMessage != null) return false;
        if (!name.equals(that.name)) return false;
        if (!temperature.equals(that.temperature)) return false;
        if (!turretHeading.equals(that.turretHeading)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = temperature.hashCode();
        temp = armor != +0.0d ? Double.doubleToLongBits(armor) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (overburn ? 1 : 0);
        result = 31 * result + (activeShield ? 1 : 0);
        result = 31 * result + heading.hashCode();
        result = 31 * result + turretHeading.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + id;
        result = 31 * result + (lastMessage != null ? lastMessage.hashCode() : 0);
        result = 31 * result + roundKills;
        result = 31 * result + totalKills;
        result = 31 * result + totalWins;
        result = 31 * result + totalTies;
        result = 31 * result + totalDeaths;
        return result;
    }
}
