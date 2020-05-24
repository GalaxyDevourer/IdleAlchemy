package MainPack.GUI;

import MainPack.Database.DAO.AltarDAO;
import MainPack.Database.DAO.PlayerDAO;
import MainPack.Database.DAO.StockDAO;
import MainPack.Entity.Altar;
import MainPack.Entity.Player;
import MainPack.Entity.Stock;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;

import java.util.*;

public class LeaderBoard {
    @FXML Label nickname_1;
    @FXML Label value_1;
    @FXML Label nickname_2;
    @FXML Label value_2;
    @FXML Label nickname_3;
    @FXML Label value_3;
    @FXML Label nickname_4;
    @FXML Label value_4;
    @FXML Label nickname_5;
    @FXML Label value_5;
    @FXML Label nickname_6;
    @FXML Label value_6;
    @FXML Label nickname_7;
    @FXML Label value_7;
    @FXML Label nickname_8;
    @FXML Label value_8;

    @FXML AnchorPane stage_common;
    @FXML AnchorPane stage_light;
    @FXML AnchorPane essences_common;
    @FXML AnchorPane essences_light;
    @FXML AnchorPane upgrades_common;
    @FXML AnchorPane upgrades_light;

    private List<Label> nick_list;
    private List<Label> value_list;

    private PlayerDAO playerDAO = new PlayerDAO();
    private StockDAO stockDAO = new StockDAO();
    private AltarDAO altarDAO = new AltarDAO();

    private List<Player> playerList = playerDAO.getAll();
    private List<Stock> stockList = stockDAO.getAll();
    private List<Altar> altarList = altarDAO.getAll();

    void initLists() {
        nick_list = Arrays.asList(nickname_1, nickname_2, nickname_3, nickname_4, nickname_5, nickname_6, nickname_7, nickname_8);
        value_list = Arrays.asList(value_1, value_2, value_3, value_4, value_5, value_6, value_7, value_8);
    }

    public LeaderBoard() throws Exception {
    }

    private void showLeaderStages () {
        int size = Math.min(playerList.size(), 8);
        List<Integer> values = new ArrayList<>();
        HashMap<Integer, String> nicks = new HashMap<>();

        playerList.forEach((x) -> {
            values.add(x.getStage());
            nicks.put(x.getStage(), x.getUsername());
        });
        Collections.sort(values);
        Collections.reverse(values);

        for(int i = 0; i < size; i++) {
            nick_list.get(i).setText(nicks.get(values.get(i)));
            value_list.get(i).setText(values.get(i).toString());
        }

    }

    private void showLeaderEssences () {
        int size = Math.min(stockList.size(), 8);
        List<Integer> values = new ArrayList<>();
        HashMap<Integer, String> nicks = new HashMap<>();

        stockList.forEach((x) -> {
            int value = x.getEarthEssenceAmount()+x.getVoidEssenceAmount()+x.getSunEssenceAmount()+x.getEnergyEssenceAmount()+x.getStormEssenceAmount();
            values.add(value);
            nicks.put(value, x.getUsername());

        });
        Collections.sort(values);
        Collections.reverse(values);

        for(int i = 0; i < size; i++) {
            nick_list.get(i).setText(nicks.get(values.get(i)));
            value_list.get(i).setText(values.get(i).toString());
        }
    }

    private void showLeaderUpgrades () {
        int size = Math.min(altarList.size(), 8);
        List<Integer> values = new ArrayList<>();
        HashMap<Integer, String> nicks = new HashMap<>();

        altarList.forEach((x) -> {
            int value = x.getCostReduceLevel()+x.getAlchSpeedLevel()+x.getAlchGetEssenceLevel()+x.getWeaponSpeedLevel()+x.getWeaponDamageLevel();
            values.add(value);
            nicks.put(value, x.getUsername());

        });
        Collections.sort(values);
        Collections.reverse(values);

        for(int i = 0; i < size; i++) {
            nick_list.get(i).setText(nicks.get(values.get(i)));
            value_list.get(i).setText(values.get(i).toString());
        }
    }

    private void inactiveAll () {
        stage_common.setVisible(true); essences_common.setVisible(true); upgrades_common.setVisible(true);
        stage_light.setVisible(false); essences_light.setVisible(false); upgrades_light.setVisible(false);
    }

    private boolean setActiveButton (AnchorPane common, AnchorPane light) {
        if (!light.isVisible()) {
            inactiveAll();

            common.setVisible(false);
            light.setVisible(true);

            return true;
        }
        return false;
    }

    @FXML
    private void onClickStages () {
        if (setActiveButton(stage_common, stage_light)) {
            showLeaderStages();
        }
    }

    @FXML
    private void onClickEssences () {
        if (setActiveButton(essences_common, essences_light)) {
            showLeaderEssences();
        }
    }

    @FXML
    private void onClickUpgrades () {
        if (setActiveButton(upgrades_common, upgrades_light)) {
            showLeaderUpgrades();
        }
    }

}
