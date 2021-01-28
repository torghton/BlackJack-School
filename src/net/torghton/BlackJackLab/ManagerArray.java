package net.torghton.BlackJackLab;

import net.torghton.BlackJackLab.AlexsGameEnhancers.*;

import java.util.ArrayList;

public class ManagerArray {

    ArrayList<ClickManager> clickManagers;
    ArrayList<DrawableManager> drawableManagers;
    ArrayList<KeyInteractor> keyInteractors;
    ArrayList<Updater> updaters;

    public ManagerArray() {
        clickManagers = new ArrayList<>();
        drawableManagers = new ArrayList<>();
        keyInteractors = new ArrayList<>();
        updaters = new ArrayList<>();
    }

    public void addManagers(ClickManager clickManager, DrawableManager drawableManager, KeyInteractor keyInteractor, Updater updater) {
        clickManagers.add(clickManager);
        drawableManagers.add(drawableManager);
        keyInteractors.add(keyInteractor);
        updaters.add(updater);
    }

    public ClickManager getClickManager(int index) {
        return clickManagers.get(index);
    }

    public DrawableManager getDrawableManager(int index) {
        return drawableManagers.get(index);
    }

    public KeyInteractor getKeyInteractor(int index) {
        return keyInteractors.get(index);
    }

    public Updater getUpdater(int index) {
        return updaters.get(index);
    }
}
