package com.dafttech.terra.game.world.items.persistence;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Set;

public class Prototype {
    public String className = "";
    public HashMap<Field, Object> values = new HashMap<Field, Object>();

    @Override
    public int hashCode() {
        return getHashBase().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Prototype) return hashCode() == ((Prototype) obj).hashCode();
        if (obj instanceof GameObject) return hashCode() == ((GameObject) obj).hashCode();

        return true;
    }

    ;

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

            Set<Field> fields = values.keySet();

            for (Field f : fields) {
                f.set(object, values.get(f));
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return object;
    }
}
