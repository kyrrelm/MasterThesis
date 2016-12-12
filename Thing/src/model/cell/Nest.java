package model.cell;

import java.util.HashMap;

/**
 * Created by Kyrre on 12.12.2016.
 */
public class Nest {
    private static Nest ourInstance = new Nest();
    private HashMap<Integer, Integer> recruitmentMap;

    public static Nest getInstance() {
        return ourInstance;
    }

    private Nest(){
        recruitmentMap = new HashMap<>();
    }

    public int checkRecruitment(int trailId){
        if (recruitmentMap.get(trailId) == null){
            return 0;
        }
        return recruitmentMap.get(trailId);
    }
    public int recruit(int trailId, int amount){
        Integer value = recruitmentMap.putIfAbsent(trailId, amount);
        if ( value != null){
            value += amount;
            recruitmentMap.put(trailId,value);
            return value;
        }
        return amount;
    }
    public int dismiss(int trailId, int amount){
        if (trailId == -1){
            return -1;
        }
        Integer value = recruitmentMap.get(trailId);
        recruitmentMap.put(trailId, Math.max(value-amount, 0));
        return recruitmentMap.get(trailId);
    }
}
