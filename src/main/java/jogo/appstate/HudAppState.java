package jogo.appstate;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA; // Importante
import com.jme3.scene.Node;
public class HudAppState extends BaseAppState {
    private final Node guiNode;
    private final AssetManager assetManager;
    private BitmapText crosshair;
    
    // novo campo
    private BitmapText messageText;
    private float messageTimer = 0;
    public HudAppState(Node guiNode, AssetManager assetManager) {
        this.guiNode = guiNode;
        this.assetManager = assetManager;
    }
    @Override
    protected void initialize(Application app) {
        BitmapFont font = assetManager.loadFont("Interface/Fonts/Default.fnt");
        
        // Crosshair 
        crosshair = new BitmapText(font, false);
        crosshair.setText("+");
        crosshair.setSize(font.getCharSet().getRenderedSize() * 2f);
        guiNode.attachChild(crosshair);
        centerCrosshair();

        
        // Configurar texto de mensagem
        messageText = new BitmapText(font, false);
        messageText.setSize(font.getCharSet().getRenderedSize() * 1.5f);
        messageText.setColor(ColorRGBA.Yellow);
        messageText.setText(""); // Começa vazio
        messageText.setLocalTranslation(10, app.getCamera().getHeight() - 10, 0); // Canto superior esquerdo
        guiNode.attachChild(messageText);
        
        System.out.println("HudAppState initialized");
    }
    // Método para mostrar mensagem
    public void showMessage(String text, float duration) {
        messageText.setText(text);
        messageTimer = duration;
    }
    private void centerCrosshair() {
        SimpleApplication sapp = (SimpleApplication) getApplication();
        int w = sapp.getCamera().getWidth();
        int h = sapp.getCamera().getHeight();
        float x = (w - crosshair.getLineWidth()) / 2f;
        float y = (h + crosshair.getLineHeight()) / 2f;
        crosshair.setLocalTranslation(x, y, 0);
    }
    @Override
    public void update(float tpf) {
        centerCrosshair();
        //Gerir tempo da mensagem
        if (messageTimer > 0) {
            messageTimer -= tpf;
            if (messageTimer <= 0) {
                messageText.setText(""); // Limpar mensagem
            }
        }
    }
    @Override
    protected void cleanup(Application app) {
        if (crosshair != null) crosshair.removeFromParent();
        if (messageText != null) messageText.removeFromParent(); // Limpar
    }
    @Override
    protected void onEnable() { }
    @Override
    protected void onDisable() { }
}