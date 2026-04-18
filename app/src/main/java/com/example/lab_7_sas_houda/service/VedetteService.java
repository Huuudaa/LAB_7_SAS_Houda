package com.example.lab_7_sas_houda.service;

import com.example.lab_7_sas_houda.beans.Vedette;
import com.example.lab_7_sas_houda.dao.IDao;
import java.util.ArrayList;
import java.util.List;

public class VedetteService implements IDao<Vedette> {

    private List<Vedette> catalogue;
    private static VedetteService instance;

    private VedetteService() {
        catalogue = new ArrayList<>();
        charger();
    }

    public static VedetteService getInstance() {
        if (instance == null) instance = new VedetteService();
        return instance;
    }

    private void charger() {
        catalogue.add(new Vedette("Emma Watson",
                "https://upload.wikimedia.org/wikipedia/commons/7/7f/Emma_Watson_2013.jpg", 4.5f));
        catalogue.add(new Vedette("Tom Cruise",
                "https://upload.wikimedia.org/wikipedia/commons/3/33/Tom_Cruise_by_Gage_Skidmore_2.jpg", 4.2f));
        catalogue.add(new Vedette("Scarlett Johansson",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/5/5d/Scarlett_Johansson_293_%28cropped2%29.jpg/500px-Scarlett_Johansson_293_%28cropped2%29.jpg", 4.7f));
        catalogue.add(new Vedette("Leonardo DiCaprio",
                "https://upload.wikimedia.org/wikipedia/commons/4/46/Leonardo_Dicaprio_Cannes_2019.jpg", 4.8f));
        catalogue.add(new Vedette("Angelina Jolie",
                "https://upload.wikimedia.org/wikipedia/commons/a/ad/Angelina_Jolie_2_June_2014_%28cropped%29.jpg", 4.6f));
    }

    @Override public boolean create(Vedette o) { return catalogue.add(o); }

    @Override
    public boolean update(Vedette o) {
        for (Vedette v : catalogue) {
            if (v.getId() == o.getId()) {
                v.setPrenom(o.getPrenom());
                v.setPhoto(o.getPhoto());
                v.setNote(o.getNote());
                return true;
            }
        }
        return false;
    }

    @Override public boolean delete(Vedette o) { return catalogue.remove(o); }

    @Override
    public Vedette findById(int id) {
        for (Vedette v : catalogue)
            if (v.getId() == id) return v;
        return null;
    }

    @Override public List<Vedette> findAll() { return catalogue; }
}