package net.hikaruna.lunajava.support;

import net.hikaruna.lunajava.feature.AbstractCollider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hikaru on 2015/05/09.
 */
public class CollisionManager {
    private Map<Integer, List<AbstractCollider>> collisionGroups;
    private Integer group;

    public CollisionManager() {
        collisionGroups = new HashMap<>(5);
        useGroup(null);
    }

    public void setCollicion(Integer groupId, AbstractCollider rectCollider) {
        useGroup(groupId).add(rectCollider);
    }

    private List<AbstractCollider> useGroup(Integer groupId) {
        if (!collisionGroups.containsKey(groupId)) {
            setGroup(groupId);
        }
        return getGroup(groupId);
    }

    private List<AbstractCollider> getGroup(Integer groupId) {
        return collisionGroups.get(groupId);
    }

    public void setGroup(Integer groupId) {
        collisionGroups.put(groupId, new ArrayList<AbstractCollider>());
    }

    public Map<Integer, List<AbstractCollider>> getCollisionGroups() {
        return collisionGroups;
    }

    public void update() {
        for (Map.Entry<Integer, List<AbstractCollider>> g1 : collisionGroups.entrySet()) {
            if (g1.getValue().isEmpty()) {
                continue;
            }
            for (Map.Entry<Integer, List<AbstractCollider>> g2 : collisionGroups.entrySet()) {
                if (g2.getValue().isEmpty()) {
                    continue;
                }
                if (g1.getKey() != null && g1.getKey().equals(g2.getKey())) {
                    continue;
                }
                for (AbstractCollider c1 : g1.getValue()) {
                    if (!c1.getActive()) {
                        continue;
                    }
                    for (AbstractCollider c2 : g2.getValue()) {
                        if (!c2.getActive()) {
                            continue;
                        }
                        if (c1.equals(c2)) {
                            continue;
                        }
                        if (c1.check(c2)) {
                            c1.collision(c2);
                        }
                    }
                }
            }
        }
    }
}
