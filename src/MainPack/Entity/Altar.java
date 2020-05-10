package MainPack.Entity;

// описывает класс алтаря улучшения ("магазин")
// содержит поля стоимость и уровня улучшений, действия над улучшениями

public class Altar {
    private String username;
    // уровни улучшений
    private int costReduceLevel = 0;       //  Earth
    private int alchSpeedLevel = 0;        //  Energy
    private int alchGetEssenceLevel = 0;   //  Sun
    private int weaponSpeedLevel = 0;      //  Storm
    private int weaponDamageLevel = 0;     //  Void

    // на сколько уменьшать цену улучшений
    private int costReduce;
    // стоимость улучшений
    private int costReduceCost;
    private int alchSpeedCost;
    private int getAlchGetEssenceCost;
    private int weaponSpeedCost;
    private int weaponDamageCost;

    public Altar() {
    }

    public Altar(String Username, int CostReduceLevel, int AlchSpeedLevel, int AlchGetEssenceLevel, int WeaponSpeedLevel, int WeaponDamageLevel) {
        this.username = Username;
        this.costReduceLevel = CostReduceLevel;
        this.alchSpeedLevel = AlchSpeedLevel;
        this.alchGetEssenceLevel = AlchGetEssenceLevel;
        this.weaponSpeedLevel = WeaponSpeedLevel;
        this.weaponDamageLevel = WeaponDamageLevel;

        initialData();
    }

    // формула рассчета цены
    private int costFormula(int costlevel) {
        return costlevel*costlevel+7;
    }

    private void initialData() {
        // инициализация стоимости улучшений
        costReduceCost = costFormula(costReduceLevel);
        alchSpeedCost = costFormula(alchSpeedLevel);
        getAlchGetEssenceCost = costFormula(alchGetEssenceLevel);
        weaponSpeedCost = costFormula(weaponSpeedLevel);
        weaponDamageCost = costFormula(weaponDamageLevel);
        // инициализация значения снижения цены
        costReduce =  costReduceLevel*2;
    }

    // метод покупки улучшения на уменьшение стоимости улучшения
    public int buyCostReduceUpg(Stock pstock) {
        if( pstock.getEarthEssenceAmount() >= costReduceCost) {
            if( costReduceLevel < 50) {
                int realcost = costReduceCost - costReduce;
                if ( realcost < 0) realcost = 0;
                pstock.setEarthEssenceAmount(pstock.getEarthEssenceAmount() - realcost);
                costReduceLevel++;
                costReduceCost = costFormula(costReduceLevel);
                costReduce = costReduceLevel * 2;

                return 0;
            }
            else return 1;
        }
        else return 2;
    }

    // метод покупки улучшения скорости добычи
    public int buyAlchSpeedUpg(Stock pstock, Player pl) {
        if( pstock.getEnergyEssenceAmount() >= alchSpeedCost) {
            if( alchSpeedLevel < 20) {
                int realcost = alchSpeedCost - costReduce;
                if ( realcost < 0) realcost = 0;
                pstock.setEnergyEssenceAmount(pstock.getEnergyEssenceAmount() - realcost);
                alchSpeedLevel++;
                alchSpeedCost = costFormula(alchSpeedLevel);

                pl.setAlchSpeed(30 - alchSpeedLevel);

                return 0;
            }
            else return 1;
        }
        else return 2;
    }

    // метод покупки улучшения увеличения добычи
    public int buyAlchGetEssenceUpg(Stock pstock, Player pl) {
        if( pstock.getSunEssenceAmount() >= getAlchGetEssenceCost) {
            if( alchGetEssenceLevel < 500) {
                int realcost = getAlchGetEssenceCost - costReduce;
                if ( realcost < 0) realcost = 0;
                pstock.setSunEssenceAmount(pstock.getSunEssenceAmount() - realcost);
                alchGetEssenceLevel++;
                getAlchGetEssenceCost = costFormula(alchGetEssenceLevel);

                pl.setAlchProd(5 + alchGetEssenceLevel*3);

                return 0;
            }
            else return 1;
        }
        else return 2;
    }

    // метод покупки улучшения скорости атаки оружия
    public int buyWeaponSpeedUpg(Stock pstock, Player pl) {
        if( pstock.getStormEssenceAmount() >= weaponSpeedCost) {
            if( weaponSpeedLevel < 80) {
                int realcost = weaponSpeedCost - costReduce;
                if ( realcost < 0) realcost = 0;
                pstock.setStormEssenceAmount(pstock.getStormEssenceAmount() - realcost);
                weaponSpeedLevel++;
                weaponSpeedCost = costFormula(weaponSpeedLevel);

                pl.setWeaponSpeed(5-weaponSpeedLevel*0.05);

                return 0;
            }
            else return 1;
        }
        else return 2;
    }

    // метод покупки улучшения увеличения урона оружия
    public int buyWeaponDamageUpg(Stock pstock, Player pl) {
        if( pstock.getVoidEssenceAmount() >= weaponDamageCost) {
            if( weaponDamageLevel < 500) {
                int realcost = weaponDamageCost - costReduce;
                if ( realcost < 0) realcost = 0;
                pstock.setVoidEssenceAmount(pstock.getVoidEssenceAmount() - realcost);
                weaponDamageLevel++;
                weaponDamageCost = costFormula(weaponDamageLevel);

                pl.setWeaponDamage(5+(int)Math.pow(weaponDamageLevel, 2));

                return 0;
            }
            else return 1;
        }
        else return 2;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCostReduceLevel() {
        return costReduceLevel;
    }

    public void setCostReduceLevel(int costReduceLevel) {
        this.costReduceLevel = costReduceLevel;
    }

    public int getAlchSpeedLevel() {
        return alchSpeedLevel;
    }

    public void setAlchSpeedLevel(int alchSpeedLevel) {
        this.alchSpeedLevel = alchSpeedLevel;
    }

    public int getAlchGetEssenceLevel() {
        return alchGetEssenceLevel;
    }

    public void setAlchGetEssenceLevel(int alchGetEssenceLevel) {
        this.alchGetEssenceLevel = alchGetEssenceLevel;
    }

    public int getWeaponSpeedLevel() {
        return weaponSpeedLevel;
    }

    public void setWeaponSpeedLevel(int weaponSpeedLevel) {
        this.weaponSpeedLevel = weaponSpeedLevel;
    }

    public int getWeaponDamageLevel() {
        return weaponDamageLevel;
    }

    public void setWeaponDamageLevel(int weaponDamageLevel) {
        this.weaponDamageLevel = weaponDamageLevel;
    }

    public int getCostReduceCost() {
        return costReduceCost;
    }

    public int getAlchSpeedCost() {
        return alchSpeedCost;
    }

    public int getGetAlchGetEssenceCost() {
        return getAlchGetEssenceCost;
    }

    public int getWeaponSpeedCost() {
        return weaponSpeedCost;
    }

    public int getWeaponDamageCost() {
        return weaponDamageCost;
    }

}