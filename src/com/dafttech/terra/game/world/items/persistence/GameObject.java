package com.dafttech.terra.game.world.items.persistence;

import java.lang.reflect.Field;

public abstract class GameObject {    
    @Override
    public int hashCode() {
        return getHashBase().hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Prototype) return hashCode() == ((Prototype)obj).hashCode();
        if(obj instanceof GameObject) return hashCode() == ((GameObject)obj).hashCode();
        
        return true;
    };
    
    public String getHashBase() {
        StringBuilder hashBuilder = new StringBuilder();
        
        hashBuilder.append(this.getClass().getCanonicalName());
        
        Field[] fields = this.getClass().getFields();
        for(Field f : fields) {
            Persistent p = f.getAnnotation(Persistent.class);
            if(p == null) continue;
            
            try {
                hashBuilder.append(f.get(this).toString());
            } catch (IllegalArgumentException | IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        return hashBuilder.toString();
    }
    
    public Prototype toPrototype() {
        Prototype proto = new Prototype();
        
        proto.className = this.getClass().getCanonicalName();
        
        Field[] fields = this.getClass().getFields();
        for(Field f : fields) {
            if(f.getAnnotation(Persistent.class) != null) {
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
