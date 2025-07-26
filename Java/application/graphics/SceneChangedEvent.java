package application.graphics;

import javafx.event.Event;  
import javafx.event.EventType;  
import javafx.scene.Scene;  
import javafx.stage.Window;  
  
public class SceneChangedEvent extends Event {  
    public static final EventType<SceneChangedEvent> SCENE_CHANGED = new EventType<>(Event.ANY, "SCENE_CHANGED");  
    private transient Window window;  
    private transient Scene oldScene;  
    private transient Scene newScene;  
    
    public SceneChangedEvent(Window window, Scene oldScene, Scene newScene) {  
        super(window, window, SCENE_CHANGED);  
        this.window = window;  
        this.oldScene = oldScene;  
        this.newScene = newScene;  
    }  
    
    public Window getWindow() {  
        return window;  
    }  
    public Scene getOldScene() {  
        return oldScene;  
    }  
    public Scene getNewScene() {  
        return newScene;  
    }  
}  

