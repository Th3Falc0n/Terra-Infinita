package com.dafttech.terra.game.world.items.persistence;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class GameObject {
    @Override
    public int hashCode() {
        return getHashBase().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Prototype) return hashCode() == ((Prototype) obj).hashCode();
        if (obj instanceof GameObject) return hashCode() == ((GameObject) obj).hashCode();

        return true;
    };

    public String getHashBase() {
        StringBuilder hashBuilder = new StringBuilder();

        hashBuilder.append(this.getClass().getCanonicalName());

        List<Field> fields = getAllDeclaredFields(this.getClass(), null);
        for (Field f : fields) {
            f.setAccessible(true);
            Persistent p = f.getAnnotation(Persistent.class);
            if (p == null) continue;

            try {
                hashBuilder.append(f.get(this).toString());
            } catch (IllegalArgumentException | IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return hashBuilder.toString();
    }

    private static List<Field> getAllDeclaredFields(Class<?> targetClass, List<Field> fields) {
        if (fields == null) fields = new ArrayList<Field>();
        for (Field field : targetClass.getDeclaredFields())
            if (!fields.contains(field)) fields.add(field);
        if (targetClass.getSuperclass() != null) getAllDeclaredFields(targetClass.getSuperclass(), fields);
        return fields;
    }

    public Prototype toPrototype() {
        Prototype proto = new Prototype();

        proto.className = this.getClass().getCanonicalName();

        Field[] fields = this.getClass().getFields();
        for (Field f : fields) {
            if (f.getAnnotation(Persistent.class) != null) {
                try {
                    proto.values.put(f.getName(), f.get(this));
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        return proto;
    }
}
