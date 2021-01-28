package net.torghton.BlackJackLab;

interface SceneChanged {
    void run(Integer scene);
}

public class Scene {

    Integer scene;

    private SceneChanged onSceneChange;

    public Scene() {
        this(0, temp -> {});
    }

    public Scene(Integer scene) {
        this(scene, temp -> {});
    }

    public Scene(Integer scene, SceneChanged onSceneChange) {
        this.scene = scene;
        this.onSceneChange = onSceneChange;
    }

    public void setScene(Integer scene) {
        callSceneChange(this.scene, scene);
        this.scene = scene;
    }

    private void callSceneChange(Integer lastScene, Integer currentScene) {
        System.out.println("Last Scene: " + lastScene);
        System.out.println("Current Scene: " + currentScene);
        if(lastScene != currentScene) {
           onSceneChange.run(currentScene);
        }
    }

    public Integer getScene() {
        return scene;
    }

    public void onSceneChange(SceneChanged onSceneChange) {
        this.onSceneChange = onSceneChange;
    }
}
