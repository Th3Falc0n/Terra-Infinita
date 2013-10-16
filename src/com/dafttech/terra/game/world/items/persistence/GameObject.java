package com.dafttech.terra.game.world.items.persistence;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.utils.Json.Serializable;

public abstract class GameObject {
    @Override
    public int hashCode() {
        return getHashBase().hashCode();
    }
    
    public List<Field> annotatedFields = new LinkedList<Field>();

    public GameObject() {
        List<Field> fields = getAllDeclaredFields(this.getClass(), null);
        for (Field f : fields) {
            f.setAccessible(true);
            Persistent p = f.getAnnotation(Persistent.class);
            if (p == null) continue;
            
            annotatedFields.add(f);
        }
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

        for (Field f : annotatedFields) {
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

        for (Field f : annotatedFields) {
            try {
                if (!(f.get(this) instanceof Serializable))
                    System.out.println("WARNING! Field " + f.getName() + " in " + this.getClass().getCanonicalName()
                            + " is not Serializable and cannot be saved!");
                proto.values.put(f.getName(), f.get(this));
            } catch (IllegalArgumentException | IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return proto;
    }
}
