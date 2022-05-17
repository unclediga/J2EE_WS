package ru.unclediga.book.wsuprun.ch01;

import javax.servlet.ServletContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Predictions {
    private int n = 32;
    private Prediction[] predictions = new Prediction[n];
    private ServletContext sctx;

    public Predictions() {

    }

    public void setPredictions(String ps) {

    }

    public String getPredictions() {
        return null;
    }

    private void populate() {
        InputStream rs = sctx.getClass().getResourceAsStream("/WEB-INF/db/predictions.db");
        BufferedReader br = new BufferedReader(new InputStreamReader(rs));
        String line;
        int i = 0;
        try {
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("!");
                Prediction prediction = new Prediction();
                prediction.setWho(parts[0]);
                prediction.setWhat(parts[1]);
                predictions[i++] = prediction;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String toXML() {
        return null;
    }

    public ServletContext getSctx() {
        return sctx;
    }

    public void setSctx(ServletContext sctx) {
        this.sctx = sctx;
    }
}

