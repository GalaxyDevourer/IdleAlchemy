package MainPack.Entity;

// описывает класс инвентаря игрока - количество его "валюты"

public class Stock{
    private String username;
    // количество эссенций у игрока
    private int sunEssenceAmount = 0;
    private int stormEssenceAmount = 0;
    private int earthEssenceAmount = 0;
    private int voidEssenceAmount = 0;
    private int energyEssenceAmount = 0;

    public Stock() {
    }

    public Stock (String Username, int SunEssenceAmount, int StormEssenceAmount, int EarthEssenceAmount, int VoidEssenceAmount, int EnergyEssenceAmount) {
        this.username = Username;
        this.sunEssenceAmount = SunEssenceAmount;
        this.stormEssenceAmount = StormEssenceAmount;
        this.earthEssenceAmount = EarthEssenceAmount;
        this.voidEssenceAmount = VoidEssenceAmount;
        this.energyEssenceAmount = EnergyEssenceAmount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getSunEssenceAmount() {
        return sunEssenceAmount;
    }

    public void setSunEssenceAmount(int sunEssenceAmount) {
        this.sunEssenceAmount = sunEssenceAmount;
    }

    public int getStormEssenceAmount() {
        return stormEssenceAmount;
    }

    public void setStormEssenceAmount(int stormEssenceAmount) {
        this.stormEssenceAmount = stormEssenceAmount;
    }

    public int getEarthEssenceAmount() {
        return earthEssenceAmount;
    }

    public void setEarthEssenceAmount(int earthEssenceAmount) {
        this.earthEssenceAmount = earthEssenceAmount;
    }

    public int getVoidEssenceAmount() {
        return voidEssenceAmount;
    }

    public void setVoidEssenceAmount(int voidEssenceAmount) {
        this.voidEssenceAmount = voidEssenceAmount;
    }

    public int getEnergyEssenceAmount() {
        return energyEssenceAmount;
    }

    public void setEnergyEssenceAmount(int energyEssenceAmount) {
        this.energyEssenceAmount = energyEssenceAmount;
    }
}
