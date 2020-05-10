package MainPack.Entity;

import java.util.Arrays;
import java.util.List;

public class Monster {
    private int stage;

    private int healthAmount = 0;
    private int maxHealth;
    private String monstername;
    private String skinname;

    private List<String> skins = Arrays.asList("reddemon.png", "bluedemon.png", "tentacledemon.png", "bigreddemon.png", "bigyellowdemon.png");

    private List<String> first_name = Arrays.asList("Matrix", "Beovuff","Kadd","Allon","Casparo","Daro","Serph","Setz","Yokkar","Jasket");
    private List<String> middle_name = Arrays.asList("Scary", "Lovely", "Perfectly", "Limited", "Dark",
            "Amorously", "Cleverly", "Puzzled", "Fast", "Suddenly");
    private List<String> last_name = Arrays.asList("Darkest", "Red", "Awesome", "Crazy",
            "Quick", "Apt", "Sensitive", "White", "Faceless", "Brainy");

    private List<List<String>> list_of_lists = Arrays.asList(first_name, middle_name, last_name);

    public Monster(Player p) {
        stage = p.getStage();
        spawnMonster(p);
    }

    private void spawnMonster(Player p) {
        stage = p.getStage();
        healthAmount = (int)Math.pow(stage, 2) + stage*5;
        maxHealth = healthAmount;
        monstername = customName();
        skinname = customSkin();
    }

    public boolean killMonster(Player p) {
        healthAmount = healthAmount - p.getWeaponDamage();
        return healthAmount <= 0;
    }

    public void dieMonster(Player p, Stock s) {
        dropMonster(s);
        p.setStage(p.getStage() + 1);
        spawnMonster(p);
    }

    private void dropMonster(Stock s) {
        int random_number = (int) (Math.random() * 5) * stage;

        switch (skinname) {
            case "reddemon.png":
                s.setEnergyEssenceAmount(s.getEnergyEssenceAmount() + random_number);
                break;
            case "bluedemon.png":
                s.setVoidEssenceAmount(s.getVoidEssenceAmount() + random_number);
                break;
            case "tentacledemon.png":
                s.setEarthEssenceAmount(s.getEarthEssenceAmount() + random_number);
                break;
            case "bigreddemon.png":
                s.setStormEssenceAmount(s.getStormEssenceAmount() + random_number);
                break;
            case "bigyellowdemon.png":
                s.setSunEssenceAmount(s.getSunEssenceAmount() + random_number);
                break;
        }
    }

    private String customName() {
        List<String> parts = Arrays.asList("", "", "");
        int random_number;

        for(int i = 0; i < 3; i++) {
            random_number = (int) (Math.random() * 9);
            parts.set(i, list_of_lists.get(i).get(random_number));
        }

        return parts.get(0)+ " " + parts.get(1) + " The " + parts.get(2);
    }

    private String customSkin() {
        int random_number;
        random_number = (int) (Math.random() * 5);

        return skins.get(random_number);
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public int getHealthAmount() {
        return healthAmount;
    }

    public void setHealthAmount(int healthAmount) {
        this.healthAmount = healthAmount;
    }

    public String getMonstername() {
        return monstername;
    }

    public void setMonstername(String monstername) {
        this.monstername = monstername;
    }

    public String getSkinname() {
        return skinname;
    }

    public void setSkinname(String skinname) {
        this.skinname = skinname;
    }

    public int getMaxHealth() {
        return maxHealth;
    }
}
