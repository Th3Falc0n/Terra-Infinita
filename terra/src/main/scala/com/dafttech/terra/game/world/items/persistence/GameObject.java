package com.dafttech.terra.game.world.items.persistence;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class GameObject {

    // ****FOLLOWING PERSISTENCE CODE**** May harm your brain

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

    public int getPrototypeHash() {
        return getHashBase().hashCode();
    }

    public boolean isSamePrototype(Object obj) {
        if (obj instanceof Prototype) return getPrototypeHash() == ((Prototype) obj).hashCode();
        if (obj instanceof GameObject) return getPrototypeHash() == ((GameObject) obj).getPrototypeHash();

        return false;
    }

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

    @Override
    protected Object clone() throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        return super.clone();
    }

    public String getName() {
        return this.getClass().getCanonicalName();
    }

    public Prototype toPrototype() {
        Prototype proto = new Prototype();

        proto.className = this.getClass().getCanonicalName();

        for (Field f : annotatedFields) {
            try {
                /*
                 * if (!(f.get(this) instanceof Serializable))
                 * System.out.println("WARNING! Field " + f.getName() + " in " +
                 * this.getClass().getCanonicalName() +
                 * " is not Serializable and cannot be saved!");
                 */
                if (f.isAnnotationPresent(Persistent.class)) proto.values.put(f, f.get(this));
            } catch (IllegalArgumentException | IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return proto;
    }
}
