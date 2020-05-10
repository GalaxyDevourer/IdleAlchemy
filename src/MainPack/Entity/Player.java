package MainPack.Entity;

import java.util.Arrays;
import java.util.List;

public class Player {
    private String username;
    // количество пройденых этапов
    private int stage = 1;

    // характеристики игрока
    private int alchSpeed = 30;
    private int alchProd = 10;
    private double weaponSpeed = 5;
    private int weaponDamage = 10;

    // прогресс добычи
    private double progress = 0;

    public Player() {
    }

    public Player(String Username, int Stage, int AlchSpeed, int AlchProd, int WeaponDamage, double WeaponSpeed) {
        this.username = Username;
        this.stage = Stage;
        this.alchSpeed = AlchSpeed;
        this.alchProd = AlchProd;
        this.weaponDamage = WeaponDamage;
        this.weaponSpeed = WeaponSpeed;
    }

    // метод увеличения прогресса добычи
    public void clickOnCauldron(Stock st) {
        progress = progress + 1;

        if ( progress == alchSpeed + 1) {
            randomCauldronDrop(st);
            progress = 0;
        }
    }

    private void randomCauldronDrop(Stock st) {
        List<Integer> multypliers = Arrays.asList(1, 1, 1, 1, 1);
        int random_number;
        for (int i = 0; i < 5; i++) {
            multypliers.set(i, (int) (Math.random() * 3) * alchProd);
        }

        st.setSunEssenceAmount(st.getSunEssenceAmount() + multypliers.get(0));
        st.setStormEssenceAmount(st.getStormEssenceAmount() + multypliers.get(1));
        st.setEarthEssenceAmount(st.getEarthEssenceAmount() + multypliers.get(2));
        st.setVoidEssenceAmount(st.getVoidEssenceAmount() + multypliers.get(3));
        st.setEnergyEssenceAmount(st.getEnergyEssenceAmount() + multypliers.get(4));

    }

    public double getProgress() {
        return progress;
    }

    public int getStage() {
        return stage;
    }

    public int getAlchSpeed() {
        return alchSpeed;
    }

    public int getAlchProd() {
        return alchProd;
    }

    public double getWeaponSpeed() {
        return weaponSpeed;
    }

    public int getWeaponDamage() {
        return weaponDamage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public void setAlchSpeed(int alchSpeed) {
        this.alchSpeed = alchSpeed;
    }

    public void setAlchProd(int alchProd) {
        this.alchProd = alchProd;
    }

    public void setWeaponSpeed(double weaponSpeed) {
        this.weaponSpeed = weaponSpeed;
    }

    public void setWeaponDamage(int weaponDamage) {
        this.weaponDamage = weaponDamage;
    }

}