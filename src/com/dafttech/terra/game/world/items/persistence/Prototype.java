package com.dafttech.terra.game.world.items.persistence;

import java.lang.reflect.Field;
import java.util.HashMap;

public class Prototype {
    public String className = "";
    public HashMap<String, Object> values = new HashMap<String, Object>();

    public int getPrototypeHash() {
        return getHashBase().hashCode();
    }

    public boolean isSamePrototype(Object obj) {
        if (obj instanceof Prototype) return hashCode() == ((Prototype) obj).hashCode();
        if (obj instanceof GameObject) return hashCode() == ((GameObject) obj).hashCode();

        return true;
    };

    public String getHashBase() {
        StringBuilder hashBuilder = new StringBuilder();

        hashBuilder.append(className);

        for (Object f : values.values()) {
            hashBuilder.append(f.toString());
        }

        return hashBuilder.toString();
    }

    public GameObject toGameObject() {
        GameObject object = null;

        try {
            @SuppressWarnings("unchecked")
            Class<GameObject> cl = (Class<GameObject>) Class.forName(className);

            object = cl.newInstance();

            Field[] fields = cl.getFields();

            for (Field f : fields) {
                f.set(object, values.get(f.getName()));
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return object;
    }
}
