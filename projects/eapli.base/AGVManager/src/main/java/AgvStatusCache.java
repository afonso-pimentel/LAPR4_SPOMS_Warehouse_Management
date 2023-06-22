package AGVManagerCaching;

import models.AgvDataResponse;

import java.util.*;

public class AgvStatusCache {
    public static class StatusCache {
        private static HashMap<Long, AgvDataResponse> agvCache = new HashMap<>();

        public static void updateAgvStatus(AgvDataResponse newAgvDataResponse){
            agvCache.put(newAgvDataResponse.id, newAgvDataResponse);
        }

        public static List<AgvDataResponse> getAllAgvStatus(){
            return new ArrayList<>(agvCache.values());
        }
    }
}
