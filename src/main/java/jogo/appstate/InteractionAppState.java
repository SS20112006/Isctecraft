package jogo.appstate;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.collision.CollisionResults;
import jogo.engine.RenderIndex;
import jogo.gameobject.GameObject;
import jogo.gameobject.item.Item;
import jogo.voxel.VoxelWorld;
import jogo.engine.GameRegistry;

public class InteractionAppState extends BaseAppState {

    private final Node rootNode;
    private final Camera cam;
    private final InputAppState input;
    private final RenderIndex renderIndex;
    private final WorldAppState world;
    private final GameRegistry registry;
    private float reach = 5.5f;
    private final HudAppState hud; 

    public InteractionAppState(Node rootNode, Camera cam, InputAppState input, RenderIndex renderIndex, WorldAppState world, GameRegistry registry, HudAppState hud) {
        this.rootNode = rootNode;
        this.cam = cam;
        this.input = input;
        this.renderIndex = renderIndex;
        this.world = world;
        this.registry = registry;
        this.hud = hud;
    }

    @Override
    protected void initialize(Application app) { }

    @Override
    public void update(float tpf) {
        if (!input.isMouseCaptured()) return;
        if (!input.consumeInteractRequested()) return;
        Vector3f camPos = cam.getLocation();
        Vector3f camDir = cam.getDirection().normalize();
        GameObject closestObj = null;
        float closestDist = reach; // Alcance máximo (ex: 5.5f)
        float minDotProduct = 0.95f; // Precisão da mira (0.95 = cone estreito)
        // Percorrer todos os objetos do jogo (Item, Prop, Character)
        for (GameObject obj : registry.getAll()) {
            // 1. Calcular vetor do Jogador -> Objeto
            // Nota: obj.getPosition() devolve Vec3 (o nosso), converter para Vector3f (do jME) para contas
            float dx = obj.getPosition().x - camPos.x;
            float dy = obj.getPosition().y - camPos.y;
            float dz = obj.getPosition().z - camPos.z;
            
            // 2. Verificar Distância (rápido, evita raiz quadrada se usarmos distSq, mas aqui simplificamos)
            float dist = (float) Math.sqrt(dx*dx + dy*dy + dz*dz);
            
            if (dist <= closestDist) {
                // 3. Verificar Direção (Raycast)
                Vector3f toObj = new Vector3f(dx, dy, dz).normalizeLocal();
                float dot = camDir.dot(toObj);
                // Se estivermos a olhar para o objeto (dot > 0.95)
                if (dot > minDotProduct) {
                    closestDist = dist;
                    closestObj = obj;
                }
            }
        }
        // Se encontrámos um objeto válido
        if (closestObj != null) {
            closestObj.onInteract(); // Chama o método polimórfico
            System.out.println("Interagiu com: " + closestObj.getName());
            hud.showMessage("Interagiu com: " + closestObj.getName(), 3.0f); // Mostra mensagem
        } else {
            // Se não interagiu com objetos, tenta os voxels (código existente)
            checkVoxelInteraction(); 
        }
    }
    private void checkVoxelInteraction() {
         VoxelWorld vw = world != null ? world.getVoxelWorld() : null;
        if (vw != null) {
            vw.pickFirstSolid(cam, reach).ifPresent(hit -> {
                VoxelWorld.Vector3i cell = hit.cell;
                System.out.println("TODO: interact with voxel at " + cell.x + "," + cell.y + "," + cell.z);
            });
        }
    }

    private GameObject findRegistered(Spatial s) {
        Spatial cur = s;
        while (cur != null) {
            GameObject obj = renderIndex.lookup(cur);
            if (obj != null) return obj;
            cur = cur.getParent();
        }
        return null;
    }

    @Override
    protected void cleanup(Application app) { }

    @Override
    protected void onEnable() { }

    @Override
    protected void onDisable() { }
}
